package com.example.sprinklesbakery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class UserCategory extends AppCompatActivity {

    ArrayList<modalCategories> categoryArrayList;
    private List<modalCartItem> cartItems = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category);

        String userName=getIntent().getStringExtra("CUSTOMER_NAME");
        TextView welcomeTextView=findViewById(R.id.welcomeTextView);
        welcomeTextView.setText("Welcome, "+userName+" !");


       /* BottomNavigationView bottomNavigationView = findViewById(R.id.user_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_cupcake);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.navigation_cupcake:
                        startActivity(new Intent(getApplicationContext(),UserCategory.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_cart:
                        startActivity(new Intent(getApplicationContext(),UserCart.class));
                        overridePendingTransition(0,0);
                        return true;
                    //case R.id.navigation_order:
                    //startActivity(new Intent(getApplicationContext(),About.class));
                    //overridePendingTransition(0,0);
                    // return true;
                    //case R.id.navigation_account:
                    //startActivity(new Intent(getApplicationContext(),About.class));
                    //overridePendingTransition(0,0);
                    // return true;
                }
                return false;
            }
        });

*/


        BottomNavigationView bottomNavigationView = findViewById(R.id.user_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_cupcake);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.navigation_cupcake) {
                    startActivity(new Intent(getApplicationContext(), UserCategory.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_cart) {

                    Intent intent = new Intent(UserCategory.this, UserCart.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;

                    /*modalCartItem cartItem=getIntent().getParcelableExtra("cart_item");

                    //ListView cartListView = findViewById(R.id.userCartListView);
                    //adapterClassCart cartAdapter = new adapterClassCart(this, cartItems);
                    //cartListView.setAdapter(cartAdapter);

                    UserCart usercart= new UserCart();
                    List<modalCartItem> cartItems = usercart.getCartItems();
                    if (cartItem != null) {
                        cartItems.add(cartItem);
                    }


                    usercart.saveCartItems(cartItems);

                    ListView cartListView = findViewById(R.id.userCartListView);
                    adapterClassCart cartAdapter = new adapterClassCart(UserCategory.this, cartItems);
                    cartListView.setAdapter(cartAdapter);


                    //startActivity(new Intent(getApplicationContext(), UserCart.class));
                    //Intent intent = new Intent(UserCategory.this, UserCart.class);
                    //intent.putParcelableArrayListExtra("cart_items", (ArrayList<modalCartItem>) cartItems);
                    overridePendingTransition(0, 0);
                    return true;*/
                }

                else if(item.getItemId() == R.id.navigation_account){
                    Intent intent = new Intent(UserCategory.this, Account.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });




        Context context = this;
        DBHelper helper = new DBHelper(context);

        ListView userCategoryListview = findViewById(R.id.userCategoryListview);


        categoryArrayList = helper.readCategories();

        adapterClass adapter = new adapterClass(context, R.layout.activity_category_list_item, categoryArrayList);
        userCategoryListview.setAdapter(adapter);



        userCategoryListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                int categoryId = categoryArrayList.get(position).getCategoryId();

                modalCategories selectedCategoryObject = (modalCategories) parent.getItemAtPosition(position);
                String selectedCategory = selectedCategoryObject.getCategoryName();



                Intent intent = new Intent(UserCategory.this, UserCupcakes.class);
                intent.putExtra("selected_category", selectedCategory);
                //intent.putExtra("category_id", categoryId); // Pass the categoryId
                startActivity(intent);
                finish();

                //AdminCupcakes adminCupcakes=new AdminCupcakes();
                //adminCupcakes.displayCupcakes(categoryId);



            }
        });


    }




}