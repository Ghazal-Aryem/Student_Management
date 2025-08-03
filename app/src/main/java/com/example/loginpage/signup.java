package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {
Button btn;
EditText mail;
EditText pas;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        btn=(Button) findViewById(R.id.inbtn);
        mail=(EditText) findViewById(R.id.email);
        pas=(EditText) findViewById(R.id.pass);
        auth=FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e=mail.getText().toString().trim();
                String p=pas.getText().toString().trim();
                if(e.equals("") || p.equals("")){
                    Toast.makeText(signup.this,"Fill the Required Fields: ",Toast.LENGTH_SHORT).show();
                }
                else{
                    registerUser(e,p);

                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void signIn(View view){
        Intent intent = new Intent(signup.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent i= new Intent(signup.this,MainActivity.class);
        startActivity(i);
        finish();
    }
    public  void registerUser(String e,String p){
        auth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(signup.this,"Account Created Successfully: ",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signup.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(signup.this,"An Error Has Been Occured: "+mail.getText().toString()+pas.getText().toString(),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}