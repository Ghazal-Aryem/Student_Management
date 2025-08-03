package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
FirebaseAuth auth;
    Button btn;
    TextView email;
    TextView password;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        email=(EditText) findViewById(R.id.stdid);
        password=(EditText) findViewById(R.id.pass);
        btn = (Button) findViewById(R.id.inbtn);
       auth=FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Please Enter Username and Password", Toast.LENGTH_SHORT).show();
                }
                else{
                auth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {// Write a message to the database
                                    Toast.makeText(MainActivity.this, "Logged In Successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, MenuPage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Incorrect Password or Username: ", Toast.LENGTH_LONG).show();
                                }
                            }
                        });}
            }});
      //  Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void signUp(View view){
        Intent intent = new Intent(MainActivity.this, signup.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       Intent i= new Intent(MainActivity.this,MainPage.class);
       startActivity(i);
       finish();
    }
    public void forgotPassword(View v){
        Toast.makeText(this,"Comming Soon! ",Toast.LENGTH_SHORT).show();
    }

}