package com.victusvir.xtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDashboard extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewNo;
    private DatabaseReference mRef;
    private DatabaseReference rRef;
    private ImageView imageViewFuelUpdate;
    private ImageView imageViewServiceUpdate;
    private TextView textViewLogout;

    Bundle firstData;
    String firstMessage;

    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 10000;
    private final long MIN_DIST = 5;
    private LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        textViewName = (TextView) findViewById(R.id.textView2);
        textViewNo = (TextView) findViewById(R.id.textView3);
        textViewLogout = (TextView) findViewById(R.id.textViewLogout);
        imageViewFuelUpdate = (ImageView) findViewById(R.id.imageViewFuelUpdate);
        imageViewServiceUpdate = (ImageView) findViewById(R.id.imageViewServiceUpdate);

        firstData = getIntent().getExtras();

        if(firstData==null)
        {
            return;
        }

        firstMessage = firstData.getString("firstmessage");
        mRef = FirebaseDatabase.getInstance().getReference("vehicles");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    Vehicle vehicle = snapshot.child(firstMessage).getValue(Vehicle.class);


                    textViewName.setText("Name : "+vehicle.getModel());
                    textViewNo.setText("Registration No : "+vehicle.getRegNo());

                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();

            }
        });

        imageViewFuelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FuelUpdate.class);
                intent.putExtra("vehicleId",firstMessage);
                startActivity(intent);
            }
        });

        imageViewServiceUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ServiceUpdate.class);
                intent.putExtra("vehicleId",firstMessage);
                startActivity(intent);
            }
        });

        textViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                locationManager.removeUpdates(locationListener);
                Intent intent = new Intent(UserDashboard.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},PackageManager.PERMISSION_GRANTED);


    }

    @Override
    protected void onStart() {
        super.onStart();

        rRef = FirebaseDatabase.getInstance().getReference("locations").child(firstMessage);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                try {
                    latLng = new LatLng(location.getLatitude(),location.getLongitude());
                    String id = rRef.push().getKey();
                    Cordinate cordinate = new Cordinate(id,location.getLatitude(),location.getLongitude());
                    rRef.child(id).setValue(cordinate);
                    Toast.makeText(getApplicationContext(),"Cordinate saved",Toast.LENGTH_SHORT).show();


                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME,MIN_DIST,locationListener);

        }catch (SecurityException e)
        {
            e.printStackTrace();
        }

    }
}