package com.example.sprinklesbakery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class UserCupcakes extends AppCompatActivity {

    int categoryId;
    //Context context;
    private DBHelper dbHelper;
    ArrayList<modalCupcakes> cupcakeArrayList;
    public void displayCupcakes(int categoryId) {
        //context = this;
        //DBHelper helper = new DBHelper(this);

        ListView adminCupcakeListview = findViewById(R.id.userCupcakeListview);

        //cupcakeArrayList= new ArrayList<>();
        cupcakeArrayList = dbHelper.readCupcakes(categoryId);

        adapterClassCupcake adapter = new adapterClassCupcake(this, R.layout.cupcake_list_item, cupcakeArrayList);
        adminCupcakeListview.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cupcakes);



        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        String selectedCategory = intent.getStringExtra("selected_category");
        categoryId =  dbHelper.getCategoryIdByName(selectedCategory);

        displayCupcakes(categoryId);


        ListView userCupcakeListview=findViewById(R.id.userCupcakeListview);

        userCupcakeListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                modalCupcakes selectedCupcake = cupcakeArrayList.get(position);

                Intent intent = new Intent(UserCupcakes.this, UserCupcakeDetails.class);
                intent.putExtra("cupcake_name", selectedCupcake.getCupcakeName());
                intent.putExtra("cupcake_price",selectedCupcake.getCupcakePrice() );
                intent.putExtra("cupcake_description", selectedCupcake.getCupcakeDescription());


                startActivity(intent);
                finish();
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.user_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_cupcake);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.navigation_cupcake) {
                    startActivity(new Intent(UserCupcakes.this, UserCategory.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_cart) {

                    Intent intent = new Intent(UserCupcakes.this, UserCart.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if(item.getItemId() == R.id.navigation_account){
                    Intent intent = new Intent(UserCupcakes.this, Account.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }



                return false;
            }
        });

    }
}