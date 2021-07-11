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

public class FuelList extends ArrayAdapter<FuelDetail> {
    private Activity context;
    private List<FuelDetail> fuellist;

    public FuelList(Activity context,List<FuelDetail> fuellist)
    {
        super(context,R.layout.list_layout,fuellist);

        this.context = context;
        this.fuellist = fuellist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewAmount = (TextView) listViewItem.findViewById(R.id.textViewModelName);
        TextView textViewCapacity = (TextView) listViewItem.findViewById(R.id.textViewRegNo);


        try {
            FuelDetail fuelDetail = fuellist.get(position);
            double liter = Double.parseDouble(fuelDetail.getPrice()) / Double.parseDouble(fuelDetail.getCost());
            textViewAmount.setText("Rs"+fuelDetail.getPrice());
            textViewCapacity.setText("liters - "+Double.toString(liter));
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return listViewItem;
    }
}

