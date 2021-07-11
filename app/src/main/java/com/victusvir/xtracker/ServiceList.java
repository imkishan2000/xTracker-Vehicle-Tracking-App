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

public class ServiceList extends ArrayAdapter<ServiceDetail> {
    private Activity context;
    private List<ServiceDetail> servicelist;

    public ServiceList(Activity context,List<ServiceDetail> servicelist)
    {
        super(context,R.layout.list_layout,servicelist);

        this.context = context;
        this.servicelist = servicelist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewStatus = (TextView) listViewItem.findViewById(R.id.textViewModelName);
        TextView textViewAmount = (TextView) listViewItem.findViewById(R.id.textViewRegNo);


        try {
            ServiceDetail serviceDetail = servicelist.get(position);
            textViewStatus.setText(serviceDetail.getStatus());
            textViewAmount.setText("Rs"+serviceDetail.getAmount());
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return listViewItem;
    }
}
