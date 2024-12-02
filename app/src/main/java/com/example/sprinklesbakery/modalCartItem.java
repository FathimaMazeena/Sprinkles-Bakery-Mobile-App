package com.example.sprinklesbakery;

import android.os.Parcel;
import android.os.Parcelable;

public class modalCartItem implements Parcelable {

    private String cupcakeName;
    private double cupcakePrice;
    private int quantity;

    public modalCartItem(String name, double price, int qty) {
        this.cupcakeName = name;
        this.cupcakePrice = price;
        this.quantity = qty;
    }

    public String getCupcakeName() {
        return cupcakeName;
    }

    public double getCupcakePrice() {
        return cupcakePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    // Parcelable implementation
    protected modalCartItem(Parcel in) {
        cupcakeName = in.readString();
        cupcakePrice = in.readDouble();
        quantity = in.readInt();
    }

    public static final Creator<modalCartItem> CREATOR = new Creator<modalCartItem>() {
        @Override
        public modalCartItem createFromParcel(Parcel in) {
            return new modalCartItem(in);
        }

        @Override
        public modalCartItem[] newArray(int size) {
            return new modalCartItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cupcakeName);
        dest.writeDouble(cupcakePrice);
        dest.writeInt(quantity);
    }
}
