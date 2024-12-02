package com.example.sprinklesbakery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class AdminCupcakes extends AppCompatActivity {

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
        setContentView(R.layout.activity_admin_cupcakes);


        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        String selectedCategory = intent.getStringExtra("selected_category");
        categoryId =  dbHelper.getCategoryIdByName(selectedCategory);


        Button fixedButton=findViewById(R.id.fixedButton);
        Button add=findViewById(R.id.addCupcakeButton);
        Button view=findViewById(R.id.viewCupcakeButton);

        //DBHelper dbHelper = new DBHelper(getBaseContext());



        fixedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CardView cardView1 = findViewById(R.id.cardView1);
                CardView cardView2 = findViewById(R.id.cardView2);
                cardView1.setVisibility(View.GONE);
                cardView2.setVisibility(View.VISIBLE);

            }

        });




        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){



                EditText newCupcakeName=findViewById(R.id.newCupcakeEditText);
                EditText cupcakePrice=findViewById(R.id.newCupcakePriceEditText);
                EditText cupcakeDesc=findViewById(R.id.newCupcakeDescEditText);

                String newCupcake=newCupcakeName.getText().toString();
                String priceCupcakeString=cupcakePrice.getText().toString();
                String descCupcake=cupcakeDesc.getText().toString();



                if (newCupcake.isEmpty()||priceCupcakeString.isEmpty()||descCupcake.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please fill all required fields!", Toast.LENGTH_SHORT);
                    toast.show();
                }


                else{

                    double priceCupcake = Double.parseDouble(priceCupcakeString);
                    //DBHelper dbHelper = new DBHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DBHelper.COLUMN_CUPCAKE_NAME,newCupcake);
                    values.put(DBHelper.COLUMN_CUPCAKE_DESCRIPTION, descCupcake);
                    values.put(DBHelper.COLUMN_CUPCAKE_PRICE, priceCupcake);
                    values.put (DBHelper.COLUMN_CATEGORY_ID, categoryId);

                    db.insert(DBHelper.TABLE_CUPCAKES, null, values);
                    dbHelper.close();
                    Toast.makeText(AdminCupcakes.this, "New Cupcake has been added!", Toast.LENGTH_SHORT).show();



                }

            }
        });



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardView cardView1 = findViewById(R.id.cardView1);
                CardView cardView2 = findViewById(R.id.cardView2);

                displayCupcakes(categoryId);

                cardView1.setVisibility(View.VISIBLE);
                cardView2.setVisibility(View.GONE);
            }
        });

        displayCupcakes(categoryId);

        BottomNavigationView bottomNavigationView = findViewById(R.id.adminNavigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_cupcake);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.navigation_cupcake) {
                    startActivity(new Intent(AdminCupcakes.this, AdminCategory.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if(item.getItemId() == R.id.navigation_account){
                    Intent intent = new Intent(AdminCupcakes.this, AdminAccount.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }



                return false;
            }
        });

    }
}

/*public class AdminCupcakes extends AppCompatActivity {

    int categoryId;
    Context context;

    public void displayCupcakes(int categoryId) {
        context = this;
        DBHelper helper = new DBHelper(context);

        ListView adminCupcakeListview = findViewById(R.id.adminCupcakeListview);

        ArrayList<modalCupcakes> cupcakeArrayList = new ArrayList<>();
        cupcakeArrayList = helper.readCupcakes(categoryId);

        adapterClassCupcake adapter = new adapterClassCupcake(context, R.layout.cupcake_list_item, cupcakeArrayList);
        adminCupcakeListview.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cupcakes);

        //displayCupcakes(categoryId);

        Intent intent = getIntent();
        String selectedCategory = intent.getStringExtra("selected_category");

        Button fixedButton = findViewById(R.id.fixedButton);
        Button add = findViewById(R.id.addCupcakeButton);
        Button view = findViewById(R.id.viewCupcakeButton);

        DBHelper dbHelper = new DBHelper(getBaseContext());



        fixedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CardView cardView1 = findViewById(R.id.cardView1);
                CardView cardView2 = findViewById(R.id.cardView2);
                cardView1.setVisibility(View.GONE);
                cardView2.setVisibility(View.VISIBLE);

            }

        });




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                categoryId = dbHelper.getCategoryIdByName(selectedCategory);

                EditText newCupcakeName = findViewById(R.id.newCupcakeEditText);
                EditText cupcakePrice = findViewById(R.id.newCupcakePriceEditText);
                EditText cupcakeDesc = findViewById(R.id.newCupcakeDescEditText);

                String newCupcake = newCupcakeName.getText().toString();
                String priceCupcake = cupcakePrice.getText().toString();
                String descCupcake = cupcakeDesc.getText().toString();

                if (newCupcake.isEmpty() || priceCupcake.isEmpty() || descCupcake.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please fill all required fields!", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    DBHelper dbHelper = new DBHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DBHelper.COLUMN_CUPCAKE_NAME, newCupcake);
                    values.put(DBHelper.COLUMN_CUPCAKE_DESCRIPTION, descCupcake);
                    values.put(DBHelper.COLUMN_CUPCAKE_PRICE, priceCupcake);
                    values.put(DBHelper.COLUMN_CATEGORY_ID, categoryId);

                    db.insert(DBHelper.TABLE_CUPCAKES, null, values);
                    dbHelper.close();
                    Toast.makeText(AdminCupcakes.this, "New Cupcake has been added!", Toast.LENGTH_SHORT).show();


                }

            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardView cardView1 = findViewById(R.id.cardView1);
                CardView cardView2 = findViewById(R.id.cardView2);

                displayCupcakes(categoryId);

                cardView1.setVisibility(View.VISIBLE);
                cardView2.setVisibility(View.GONE);
            }
        });

    }
}*/