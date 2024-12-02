package com.example.sprinklesbakery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class AdminCategory extends AppCompatActivity {

    private ListView listview;
    Context context;
    private DBHelper helper;
    private ArrayList<modalCategories> categoryArrayList;

    public void displayCategories() {
        context = this;
        helper = new DBHelper(context);

        ListView adminCategoryListview = findViewById(R.id.userCupcakeListview);

        //categoryArrayList = new ArrayList<>();
        categoryArrayList = helper.readCategories();

        adapterClass adapter = new adapterClass(context, R.layout.activity_category_list_item, categoryArrayList);
        adminCategoryListview.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);


        Button fixedButton=findViewById(R.id.fixedButton);
        Button add=findViewById(R.id.addCategoryButton);
        Button view=findViewById(R.id.viewCategoryButton);




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


                EditText newCategoryName=findViewById(R.id.newCategoryEditText);
                EditText catDesc=findViewById(R.id.newCategoryDescEditText);

                String newCategory=newCategoryName.getText().toString();
                String catDescription=catDesc.getText().toString();

                 if (newCategory.isEmpty()||catDescription.isEmpty()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Please fill all required fields!", Toast.LENGTH_SHORT);
                        toast.show();
                }


                else{
                    DBHelper dbHelper = new DBHelper(getBaseContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DBHelper.COLUMN_CATEGORY_NAME,newCategory);
                    values.put(DBHelper.COLUMN_CATEGORY_DESCRIPTION, catDescription);


                    db.insert(DBHelper.TABLE_CATEGORY, null, values);
                    dbHelper.close();
                    Toast.makeText(AdminCategory.this, "New Category has been added!", Toast.LENGTH_SHORT).show();

                }

            }
        });


        displayCategories();

        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                CardView cardView1 = findViewById(R.id.cardView1);
                CardView cardView2 = findViewById(R.id.cardView2);

                displayCategories();

                cardView1.setVisibility(View.VISIBLE);
                cardView2.setVisibility(View.GONE);
            }
        });

        ListView adminCategoryListview = findViewById(R.id.userCupcakeListview);


        adminCategoryListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                int categoryId = categoryArrayList.get(position).getCategoryId();

                modalCategories selectedCategoryObject = (modalCategories) parent.getItemAtPosition(position);
                String selectedCategory = selectedCategoryObject.getCategoryName();



                Intent intent = new Intent(AdminCategory.this, AdminCupcakes.class);
                intent.putExtra("selected_category", selectedCategory);
                //intent.putExtra("category_id", categoryId); // Pass the categoryId
                startActivity(intent);
                finish();

                //AdminCupcakes adminCupcakes=new AdminCupcakes();
                //adminCupcakes.displayCupcakes(categoryId);


            }
        });


        /*adminCategoryListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                int categoryId = categoryArrayList.get(position).getCategoryId();
                AdminCupcakes adminCupcakes=new AdminCupcakes();
                adminCupcakes.displayCupcakes(categoryId);

                modalCategories selectedCategoryObject = (modalCategories) parent.getItemAtPosition(position);
                String selectedCategory = selectedCategoryObject.getCategoryName();

                Intent intent = new Intent(AdminCategory.this, AdminCupcakes.class);
                intent.putExtra("selected_category", selectedCategory);
                startActivity(intent);
                finish();
            }
        });*/

        BottomNavigationView bottomNavigationView = findViewById(R.id.adminNavigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_cupcake);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.navigation_cupcake) {
                    startActivity(new Intent(AdminCategory.this, AdminCategory.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if(item.getItemId() == R.id.navigation_account){
                    Intent intent = new Intent(AdminCategory.this, AdminAccount.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }



                return false;
            }
        });

    }





}

