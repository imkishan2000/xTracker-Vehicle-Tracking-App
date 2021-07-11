package com.victusvir.xtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterVehicle extends AppCompatActivity {

    private EditText registerEmail;
    private EditText registerPassword;
    private EditText ownerName;
    private EditText regNo;
    private EditText model;
    private EditText fuelType;
    private EditText seatingCap;
    private EditText color;
    private Button registerBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference vehicleDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);

        registerEmail = (EditText) findViewById(R.id.registerEmail);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        ownerName = (EditText) findViewById(R.id.ownerName);
        regNo = (EditText) findViewById(R.id.regNo);
        model = (EditText) findViewById(R.id.model);
        fuelType = (EditText) findViewById(R.id.fuelType);
        seatingCap = (EditText) findViewById(R.id.fuelType);
        color = (EditText) findViewById(R.id.color);
        registerBtn = (Button) findViewById(R.id.registerBtn);

        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser()
    {
        String email = registerEmail.getText().toString().toLowerCase().trim();
        String password = registerPassword.getText().toString().trim();
        String owner = ownerName.getText().toString().toUpperCase().trim();
        String registrationNo = regNo.getText().toString().toUpperCase().trim();
        String vehicleModel = model.getText().toString().toUpperCase().trim();
        String fuel = fuelType.getText().toString().trim();
        String seatingCapacity = seatingCap.getText().toString().trim();
        String vehicleColor = color.getText().toString().trim();

        //Validation
        //Email
        if(TextUtils.isEmpty(email))
        {
            registerEmail.setError("Email is required");
            registerEmail.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            registerEmail.setError("Please enter a valid email");
            registerEmail.requestFocus();
            return;
        }
        //Password
        if(TextUtils.isEmpty(password))
        {
            registerPassword.setError("Password is required");
            registerPassword.requestFocus();
            return;
        }else if (password.length()<6)
        {
            registerPassword.setError("Minimum length of password should be 6");
            registerPassword.requestFocus();
            return;
        }
        //Registration Number

        if(TextUtils.isEmpty(owner))
        {
            ownerName.setError("Owner Name is required");
            ownerName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(registrationNo))
        {
            regNo.setError("Registration number is required");
            regNo.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(vehicleModel))
        {
            model.setError("Model is required");
            model.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(fuel))
        {
            fuelType.setError("Fuel Type is required");
            fuelType.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(seatingCapacity))
        {
            seatingCap.setError("Seating Capacity is required");
            seatingCap.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(vehicleColor))
        {
            color.setError("Color is required");
            color.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful())
              {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Vehicle vehicle = new Vehicle(userId,email,owner,registrationNo,vehicleModel,fuel,seatingCapacity,vehicleColor);

                FirebaseDatabase.getInstance().getReference("vehicles")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(vehicle).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(RegisterVehicle.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),AdminDashBoard.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(RegisterVehicle.this,"Failed to register! Try again",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
              }
              else
              {
                  Toast.makeText(RegisterVehicle.this,"Failed to register! Try again",Toast.LENGTH_LONG).show();
              }
            }
        });




    }
}