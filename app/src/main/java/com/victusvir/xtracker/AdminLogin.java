package com.victusvir.xtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
    EditText emailText;
    EditText passwordText;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        emailText = (EditText)findViewById(R.id.emailText);
        passwordText = (EditText)findViewById(R.id.passwordText);
        submitBtn = (Button)findViewById(R.id.submitBtn);

        login();
    }
    private void login()
    {

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                if(TextUtils.equals(email,"admin") && TextUtils.equals(password,"admin"))
                {
                    Intent intent = new Intent(getApplicationContext(),AdminDashBoard.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Incorrect Cerdentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}