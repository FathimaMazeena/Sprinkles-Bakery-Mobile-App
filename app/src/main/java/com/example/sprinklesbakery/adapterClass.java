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

public class adapterClass extends ArrayAdapter<modalCategories> {

private Context context;
private int resource;
private ArrayList<modalCategories> categoriesArrayList;

adapterClass(Context context, int resource,ArrayList<modalCategories> categoriesArrayList){
    super(context, resource, categoriesArrayList);

    this.context=context;
    this.resource=resource;
    this.categoriesArrayList=categoriesArrayList;
}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater= LayoutInflater.from(context);
        View row=inflater.inflate(resource,parent,false);

        TextView categoryName=row.findViewById(R.id.categoryName);
        TextView categoryDesc=row.findViewById(R.id.categoryDesc);



        modalCategories modal=categoriesArrayList.get(position);


        categoryName.setText(modal.getCategoryName());
        categoryDesc.setText(modal.getCategoryDesc());

        return row;

    }
}
