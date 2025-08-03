package com.example.loginpage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainPage extends AppCompatActivity {
Button btn1;
Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page);
        btn1 =(Button) findViewById(R.id.btn1);
        btn2 =(Button) findViewById(R.id.btn2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainPage.this,MainActivity.class);
                startActivity(i);
             //   Toast.makeText(MainPage.this, "Intent done!", Toast.LENGTH_SHORT).show();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainPage.this,signup.class);
                startActivity(i);
                finish();

            }
        });
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    public void facebook(View v){
        Uri uri =Uri.parse("https://www.facebook.com");
        Intent i=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }
    public void instagram(View v){
        Uri uri =Uri.parse("https://www.instagram.com");
        Intent i=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }
    public void twitter(View v){
        Uri uri =Uri.parse("https://x.com/?lang=en");
        Intent i=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }
}