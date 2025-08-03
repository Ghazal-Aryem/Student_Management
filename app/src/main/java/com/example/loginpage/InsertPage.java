package com.example.loginpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InsertPage extends AppCompatActivity {
Button btn;
EditText name;
    EditText sid;
EditText fname;
EditText Degree;
EditText rNo;
Button btn2;
    FirebaseFirestore db;
    ProgressDialog pd;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert_page);
        databaseReference = FirebaseDatabase.getInstance().getReference("Documents");
       sid=(EditText) findViewById(R.id.stdid);
        name=(EditText) findViewById(R.id.name);
        fname=(EditText) findViewById(R.id.email);
        Degree=(EditText) findViewById(R.id.pass);
        rNo=(EditText) findViewById(R.id.rno);
        btn=(Button) findViewById(R.id.inbtn);
        btn2=(Button) findViewById(R.id.button2);
        pd=new ProgressDialog(this);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertPage.this, MenuPage.class);
                startActivity(intent);
                finish();
            }
        });
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        insert();
    }
});

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void insert(){
        pd.setTitle("Inserting Data");

        pd.show();
        pd.setMessage("Inserting");
        String id= sid.getText().toString();
        String n=name.getText().toString();
        String fn=fname.getText().toString();
        String d=Degree.getText().toString();
        String rn=rNo.getText().toString();
        Map<String,Object> doc=new HashMap<>();
        doc.put("ID",id);
        doc.put("Name",n);
        doc.put("FatherName",fn);
        doc.put("Degree",d);
        doc.put("RollNo",rn);
        databaseReference.child(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    Toast.makeText(this, "Student ID already exists. Please use a unique ID.", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child(id).setValue(doc)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(this, "Student added successfully!", Toast.LENGTH_SHORT).show();
                                name.setText(""); // Clear input fields
                                fname.setText("");
                                rNo.setText("");
                                sid.setText("");
                                Degree.setText("");

                            })
                            .addOnFailureListener(e -> Toast.makeText(this, "Failed to add student.", Toast.LENGTH_SHORT).show());
                }
            } else {
                Toast.makeText(this, "Error checking Student ID: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("CreateActivity", "Error checking Student ID", task.getException());
            }
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i= new Intent(InsertPage.this,MenuPage.class);
        startActivity(i);
        finish();
    }
}