package com.victusvir.xtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FuelUpdate extends AppCompatActivity {

    EditText editTextBillNo;
    EditText editTextPrice;
    EditText editTextCost;
    Button saveBtn;

    private String vehicleId;
    DatabaseReference fuelDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_update);

        editTextBillNo = (EditText) findViewById(R.id.editTextBillNo);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        editTextCost = (EditText) findViewById(R.id.editTextCost);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        Intent intent = getIntent();
        vehicleId = intent.getStringExtra("vehicleId");

        fuelDatabase = FirebaseDatabase.getInstance().getReference("Fuel_Information").child(vehicleId);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFuelUpdate();
            }
        });


    }

    private void saveFuelUpdate()
    {
        String billNo = editTextBillNo.getText().toString().trim();
        String cost = editTextCost.getText().toString().trim();
        String price = editTextPrice.getText().toString().trim();

        if(!TextUtils.isEmpty(cost) || !TextUtils.isEmpty(price))
        {
            try {
                String id = fuelDatabase.push().getKey();
                FuelDetail fuelDetail = new FuelDetail(id,billNo,cost,price);
                fuelDatabase.child(id).setValue(fuelDetail);
                Toast.makeText(getApplicationContext(), "Fuel Details Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),UserDashboard.class));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Enter Fuel Details",Toast.LENGTH_SHORT).show();
        }

    }
}