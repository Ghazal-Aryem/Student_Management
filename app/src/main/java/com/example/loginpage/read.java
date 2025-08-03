package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class read extends AppCompatActivity {
    private Button readButton;
    private Button back;
    private TextView displayItems;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        readButton = findViewById(R.id.readButton);
        back = findViewById(R.id.back);
        displayItems = findViewById(R.id.displayItems);
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(read.this,MenuPage.class);
        startActivity(i);
        finish();
    }
});
        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Documents");

        readButton.setOnClickListener(v -> fetchItems());
    }

    private void fetchItems() {
        displayItems.setVisibility(View.VISIBLE);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    StringBuilder itemsList = new StringBuilder();
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        String id = itemSnapshot.child("ID").getValue(String.class);
                        String name = itemSnapshot.child("Name").getValue(String.class); // Get item name
                        String fn=itemSnapshot.child("FatherName").getValue(String.class);
                        String degree=itemSnapshot.child("Degree").getValue(String.class);
                        String roll=itemSnapshot.child("RollNo").getValue(String.class);
                        if (id != null && name != null) {
                            itemsList.append("\n\nStudent ID: ").append(id).append("\nStudent Name: ").append(name).append("\nFather Name: ").append(fn).append("\nDegree: ").append(degree).append("\nRoll No: ").append(roll).append("\n\n");
                           // itemsList.append("Student ID: ").append(id).append("\nStudent Name: ").append(name).append("\nRoll No: ").append(roll).append("Degree: ").append(degree);
                            Log.d("ReadActivity", "Fetched Item - ID: " + id + ", Name: " + name);
                        }
                    }

                    if (itemsList.length() > 0) {
                        displayItems.setText(itemsList.toString());
                    } else {
                        displayItems.setText("No items found.");
                    }
                } else {
                    displayItems.setText("No data found in the database.");
                    Log.d("ReadActivity", "No snapshot exists in the database.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(read.this, "Failed to fetch items: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ReadActivity", "Database error: " + error.getMessage());
            }
        });
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent i= new Intent(read.this,MenuPage.class);
        startActivity(i);
        finish();
    }
}