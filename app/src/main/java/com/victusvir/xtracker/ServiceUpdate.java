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

public class ServiceUpdate extends AppCompatActivity {

    private EditText serviceBillNo;
    private EditText serviceStatus;
    private EditText serviceAmount;
    private Button serviceSavedBtn;

    private String vehicleId;
    DatabaseReference fuelDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_update);

        serviceBillNo = (EditText) findViewById(R.id.serviceBillNo);
        serviceStatus = (EditText) findViewById(R.id.serviceStatus);
        serviceAmount = (EditText) findViewById(R.id.serviceAmount);
        serviceSavedBtn = (Button)findViewById(R.id.serviceSavedBtn);

        Intent intent = getIntent();
        vehicleId = intent.getStringExtra("vehicleId");

        fuelDatabase = FirebaseDatabase.getInstance().getReference("Service_Information").child(vehicleId);

        serviceSavedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveServiceUpdate();
            }
        });


    }

    private void saveServiceUpdate()
    {
        String billNo = serviceBillNo.getText().toString().trim();
        String status = serviceStatus.getText().toString().trim();
        String amount = serviceAmount.getText().toString().trim();

        if(!TextUtils.isEmpty(status) || !TextUtils.isEmpty(amount))
        {
            try {
                String id = fuelDatabase.push().getKey();
                ServiceDetail serviceDetail = new ServiceDetail(id,billNo,status,amount);
                fuelDatabase.child(id).setValue(serviceDetail);
                Toast.makeText(getApplicationContext(), "Service Details Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),UserDashboard.class));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            if(!TextUtils.isEmpty(status))
            {
                serviceStatus.setError("status should not be empty");
                serviceStatus.requestFocus();
                return;
            }
            else if(!TextUtils.isEmpty(amount))
            {
                serviceAmount.setError("Amount should not be empty");
                serviceAmount.requestFocus();
                return;
            }
            //Toast.makeText(getApplicationContext(),"Enter Fuel Details",Toast.LENGTH_SHORT).show();
        }

    }
}