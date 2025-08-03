package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class deletePage extends AppCompatActivity {
EditText id;
Button bt;
    Button btn;

    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_page);
        bt=(Button) findViewById(R.id.search);
        id=findViewById(R.id.stdid);
        btn=findViewById(R.id.button2);

        databaseReference = FirebaseDatabase.getInstance().getReference("Documents");
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(deletePage.this,MenuPage.class);
        startActivity(i);
        finish();
    }
});
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bid = id.getText().toString().trim();
                if (!bid.isEmpty()) {
                    databaseReference.child(id.getText().toString()).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                databaseReference.child(bid).removeValue()
                                        .addOnSuccessListener(unused -> Toast.makeText(deletePage.this, "Item deleted successfully!", Toast.LENGTH_SHORT).show()

                                        )
                                        .addOnFailureListener(e -> Toast.makeText(deletePage.this, "Failed to delete item.", Toast.LENGTH_SHORT).show());

                            } else {
                                Toast.makeText(deletePage.this, "No Record Found!", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {
                            Toast.makeText(deletePage.this, "Error checking ID: ", Toast.LENGTH_SHORT).show();

                        }
                    });

                } else {
                    Toast.makeText(deletePage.this, "Please enter an ID.", Toast.LENGTH_SHORT).show();
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