package com.example.loginpage;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class updatePage extends AppCompatActivity {
Button btn;
EditText id;
    Button back;
    EditText name;
    EditText degree;
    EditText fname;
    EditText rno;
    Button upd;

    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_page);
        btn=(Button) findViewById(R.id.search);
        upd=(Button) findViewById(R.id.inbtn);

        back=(Button)findViewById(R.id.button2);
        name=(EditText) findViewById(R.id.name);
        fname=(EditText) findViewById(R.id.email);
        degree=(EditText) findViewById(R.id.pass);
        rno=(EditText) findViewById(R.id.rno);




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(updatePage.this,MenuPage.class);
                startActivity(i);
                finish();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Documents");
id=findViewById(R.id.stdid);

        btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String bid = id.getText().toString().trim();
        if (!bid.isEmpty()) {
            databaseReference.child(id.getText().toString()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        Toast.makeText(updatePage.this, "Record Found!", Toast.LENGTH_SHORT).show();
                        DataSnapshot snapshot = task.getResult();

                        // Assuming the fields in the snapshot are named "name", "age", and "email"
                        String n = snapshot.child("Name").getValue(String.class);
                        String f = snapshot.child("FatherName").getValue(String.class);
                        String d = snapshot.child("Degree").getValue(String.class);
                        String r= snapshot.child("RollNo").getValue(String.class);
                        name.setText(n);
                        fname.setText(f);
                        degree.setText(d);
                        rno.setText(r);
                    } else {
                        Toast.makeText(updatePage.this, "No Record Found!", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(updatePage.this, "Error checking ID: ", Toast.LENGTH_SHORT).show();

                }
            });

        } else {
            Toast.makeText(updatePage.this, "Please enter an ID.", Toast.LENGTH_SHORT).show();
        }}
});
        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("") || fname.getText().toString().equals("") || degree.getText().toString().equals("") || rno.getText().toString().equals("") || id.getText().toString().equals("") )
                {
                    Toast.makeText(updatePage.this, "Please Fill the Fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    Map<String, Object> updatedItem = new HashMap<>();
                    updatedItem.put("ID", id.getText().toString());
                    updatedItem.put("Name", name.getText().toString());
                    updatedItem.put("Degree", degree.getText().toString());
                    updatedItem.put("FatherName", fname.getText().toString());
                    updatedItem.put("RollNo", rno.getText().toString());

                    databaseReference.child(id.getText().toString()).updateChildren(updatedItem)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(updatePage.this, "Item updated successfully!", Toast.LENGTH_SHORT).show();
                                name.setText(""); // Clear input fields
                                fname.setText("");
                                id.setText("");
                                degree.setText("");
                                rno.setText("");
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(updatePage.this, "Failed to update item.", Toast.LENGTH_SHORT).show();
                                Log.e("UpdateActivity", "Error updating item", e);
                            });
                }

            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}