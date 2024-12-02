package com.example.sprinklesbakery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class UserCupcakeDetails extends AppCompatActivity {

    private List<modalCartItem> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cupcake_details);


        String cupcakeName = getIntent().getStringExtra("cupcake_name");
        double cupcakePrice = getIntent().getDoubleExtra("cupcake_price", 0.0);
        String cupcakeDesc=getIntent().getStringExtra("cupcake_description");

        TextView nameTextView = findViewById(R.id.cupcakeNameTextView);
        TextView priceTextView = findViewById(R.id.cupcakePriceTextView);
        TextView descTextView = findViewById(R.id.cupcakeDescription);


        nameTextView.setText(cupcakeName);
        priceTextView.setText("Rs."+String.valueOf(cupcakePrice));
        descTextView.setText(cupcakeDesc);


        Button addToCartButton = findViewById(R.id.addToCartButton);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText quantityEditText = findViewById(R.id.quantityEditText);


                String quantityString = quantityEditText.getText().toString();


                if (quantityString.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter the quantity!", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                    int quantity = Integer.parseInt(quantityString);
                    //modalCartItem cartItem = new modalCartItem(cupcakeName, cupcakePrice, quantity);
                    //cartItems.add(cartItem);
                    //Intent intent = new Intent(UserCupcakeDetails.this, UserCart.class);
                    //intent.putParcelableArrayListExtra("cart_items", (ArrayList<modalCartItem>) cartItems);
                    //startActivity(intent);


                    // Assuming cartItems is a List<modalCartItem>
                    modalCartItem cartItem = new modalCartItem(cupcakeName, cupcakePrice, quantity);
                    //cartItems.add(cartItem);

                    Intent intent = new Intent(UserCupcakeDetails.this, UserCart.class);
                    intent.putExtra("cart_item",cartItem);
                    startActivity(intent);

                    Toast.makeText(UserCupcakeDetails.this, "Item has been added to your cart!", Toast.LENGTH_SHORT).show();

                   /*TextView name=findViewById(R.id.cupcakeNameCartTextView);
                    TextView price=findViewById(R.id.cupcakePriceCartTextView);
                    TextView itemQuantity=findViewById(R.id.quantityTextView);
                    TextView totalAmount=findViewById(R.id.totalAmountTextView);

                    int qty = Integer.parseInt(quantity);
                    double totalAmountValue = cupcakePrice * qty;

                    //ListView cartListView = findViewById(R.id. userCartListView);
                    name.setText(cupcakeName);
                    price.setText(String.valueOf(cupcakePrice));
                    itemQuantity.setText(quantity);
                    totalAmount.setText((String.valueOf(totalAmountValue)));


                    Intent intent=new Intent(UserCupcakeDetails.this,UserCart.class);
                    startActivity(intent);

                    Toast.makeText(UserCupcakeDetails.this, "Item has been added to your cart!", Toast.LENGTH_SHORT).show();
*/
                }
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.user_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_cupcake);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.navigation_cupcake) {
                    startActivity(new Intent(UserCupcakeDetails.this, UserCategory.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_cart) {

                    Intent intent = new Intent(UserCupcakeDetails.this, UserCart.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }

                else if(item.getItemId() == R.id.navigation_account){
                    Intent intent = new Intent(UserCupcakeDetails.this, Account.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }

                return false;
            }
        });




    }
}