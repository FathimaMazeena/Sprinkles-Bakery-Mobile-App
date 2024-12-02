package com.example.sprinklesbakery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserCart extends AppCompatActivity {


    public void saveCartItems(List<modalCartItem> cartItems) {
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        editor.putString("cartItems", json);
        editor.apply();
    }

    public List<modalCartItem> getCartItems() {
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("cartItems", "");

        if (json.isEmpty()) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<List<modalCartItem>>(){}.getType();
        return gson.fromJson(json, type);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);

        //List<modalCartItem> cartItems = getIntent().getParcelableArrayListExtra("cart_items");

        //ListView cartListView = findViewById(R.id.userCartListView);
        //adapterClassCart cartAdapter = new adapterClassCart(this, cartItems);
        //cartListView.setAdapter(cartAdapter);
        //List<modalCartItem> cartItems = getIntent().getParcelableArrayListExtra("cart_items");
        //List<modalCartItem> cartItems = getCartItems();

        modalCartItem cartItem=getIntent().getParcelableExtra("cart_item");

        //ListView cartListView = findViewById(R.id.userCartListView);
        //adapterClassCart cartAdapter = new adapterClassCart(this, cartItems);
        //cartListView.setAdapter(cartAdapter);

        List<modalCartItem> cartItems = getCartItems();
        if (cartItem != null) {
            cartItems.add(cartItem);
            saveCartItems(cartItems);
        }


        ListView cartListView = findViewById(R.id.userCartListView);
        adapterClassCart cartAdapter = new adapterClassCart(this, cartItems);
        cartListView.setAdapter(cartAdapter);


        Button placeorder=findViewById(R.id.placeOrderButton);
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    DBHelper dbHelper = new DBHelper(getBaseContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();

                    TextView welcomeTextView=findViewById(R.id.welcomeTextView);
                    String customerName= welcomeTextView.getText().toString();

                    int customerId= dbHelper.getCustomerIdByName(customerName);


                    values.put(DBHelper.COLUMN_CUSTOMER_ID,customerId);
                    long orderId = db.insert(DBHelper.TABLE_ORDERS, null, values);


                for (int i = 0; i < cartListView.getCount(); i++) {
                    view = cartListView.getChildAt(i);

                    TextView cupcakeNameTextView = view.findViewById(R.id.cupcakeNameCartTextView);
                    TextView quantityTextView = view.findViewById(R.id.quantityTextView);
                    TextView totalAmountTextView = view.findViewById(R.id.totalAmountTextView);
                    TextView priceTextView = view.findViewById(R.id.cupcakePriceCartTextView);


                    String cupcakeName = cupcakeNameTextView.getText().toString();
                    int quantity = Integer.parseInt(quantityTextView.getText().toString());
                    double totalAmount = Double.parseDouble(totalAmountTextView.getText().toString());
                    double price = Double.parseDouble(priceTextView.getText().toString());

                    int cupcakeId= dbHelper.getCupcakeIdByName(cupcakeName);

                    ContentValues itemValues = new ContentValues();
                    itemValues.put(DBHelper.COLUMN_ORDER_ID, orderId);
                    itemValues.put(DBHelper.COLUMN_CUPCAKE_ID,cupcakeId);
                    itemValues.put(DBHelper.COLUMN_QUANTITY,quantity);
                    itemValues.put(DBHelper.COLUMN_TOTAL_AMOUNT,totalAmount);
                    db.insert(DBHelper.TABLE_ORDER_ITEMS, null, itemValues);


                }

                    dbHelper.close();
                    Toast.makeText(UserCart.this, "Your Order has been Confirmed!", Toast.LENGTH_SHORT).show();
            }

        });







        BottomNavigationView bottomNavigationView = findViewById(R.id.user_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_cupcake);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.navigation_cupcake) {
                    startActivity(new Intent(UserCart.this, UserCategory.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_cart) {

                    Intent intent = new Intent(UserCart.this, UserCart.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if(item.getItemId() == R.id.navigation_account){
                    Intent intent = new Intent(UserCart.this, Account.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }



                return false;
            }
        });




    }
}