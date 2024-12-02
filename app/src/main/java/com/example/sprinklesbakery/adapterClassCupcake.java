package com.example.sprinklesbakery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;



public class adapterClassCupcake extends ArrayAdapter<modalCupcakes> {

    private Context context;
    private int resource;
    private ArrayList<modalCupcakes>cupcakesArrayList;
    adapterClassCupcake(Context context, int resource,ArrayList<modalCupcakes> cupcakesArrayList){
        super(context, resource, cupcakesArrayList);

        this.context=context;
        this.resource=resource;
        this.cupcakesArrayList=cupcakesArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater= LayoutInflater.from(context);
        View row=inflater.inflate(resource,parent,false);


        TextView cupcakeId = row.findViewById(R.id.cupcakeIdTextView);
        TextView cupcakeName = row.findViewById(R.id.cupcakeNameTextView);
        TextView cupcakePrice = row.findViewById(R.id.cupcakePriceTextView);
        TextView cupcakeDesc = row.findViewById(R.id.cupcakeDescriptionTextView);

        modalCupcakes modalCupcakes = cupcakesArrayList.get(position);


        cupcakeId.setText(String.valueOf(modalCupcakes.getCupcakeId()));
        cupcakeName.setText(modalCupcakes.getCupcakeName());
        cupcakePrice.setText("Rs."+String.valueOf(modalCupcakes.getCupcakePrice()));
        cupcakeDesc.setText(modalCupcakes.getCupcakeDescription());
        return row;

    }






}





