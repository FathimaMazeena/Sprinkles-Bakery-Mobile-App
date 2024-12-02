package com.example.sprinklesbakery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_orders);

        BottomNavigationView bottomNavigationView = findViewById(R.id.user_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_cupcake);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.navigation_cupcake) {
                    startActivity(new Intent(UserOrders.this, UserCategory.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_cart) {

                    Intent intent = new Intent(UserOrders.this, UserCart.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if(item.getItemId() == R.id.navigation_account){
                    Intent intent = new Intent(UserOrders.this, Account.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }



                return false;
            }
        });

    }

}