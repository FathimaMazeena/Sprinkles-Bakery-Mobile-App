package com.example.sprinklesbakery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class adapterClassCart extends ArrayAdapter<modalCartItem> {

    private Context context;
    private List<modalCartItem> cartItems;

    public adapterClassCart( Context context,  List<modalCartItem> cartItems) {
        super(context, 0, cartItems);
        this.context = context;
        this.cartItems = cartItems;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cart_list_item, parent, false);
        }

        modalCartItem cartItem = cartItems.get(position);

        double totalAmountValue = (cartItem.getQuantity()) * (cartItem.getCupcakePrice());

        TextView itemName = view.findViewById(R.id.cupcakeNameCartTextView);
        TextView itemPrice = view.findViewById(R.id.cupcakePriceCartTextView);
        TextView itemQuantity = view.findViewById(R.id.quantityTextView);
        TextView itemTotalAmount=view.findViewById(R.id.totalAmountTextView);



        itemName.setText(cartItem.getCupcakeName());
        itemPrice.setText("Rs."+String.valueOf(cartItem.getCupcakePrice()));
        itemQuantity.setText("Quantity-"+String.valueOf(cartItem.getQuantity()));
        itemTotalAmount.setText("Rs."+(String.valueOf(totalAmountValue)));

        return view;
    }
}
