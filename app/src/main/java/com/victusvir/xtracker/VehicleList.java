package com.victusvir.xtracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class VehicleList extends ArrayAdapter<Vehicle> {
    private Activity context;
    private List<Vehicle> vehicleList;

    public VehicleList(Activity context,List<Vehicle> vehicleList)
    {
        super(context,R.layout.list_layout,vehicleList);

        this.context=context;
        this.vehicleList=vehicleList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);
        TextView textViewModelName = (TextView) listViewItem.findViewById(R.id.textViewModelName);
        TextView textViewRegNo = (TextView) listViewItem.findViewById(R.id.textViewRegNo);

        Vehicle vehicle = vehicleList.get(position);

        textViewModelName.setText(vehicle.getModel());
        textViewRegNo.setText(vehicle.getRegNo());

        return listViewItem;

    }
}
