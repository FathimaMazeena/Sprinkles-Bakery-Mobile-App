package com.example.sprinklesbakery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNewAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);


        Button continueButton = findViewById(R.id.continueButton);
        Button signupButton=findViewById(R.id.signupButton);
        CardView cardView1 = findViewById(R.id.cardView1);
        CardView cardView2 = findViewById(R.id.cardView2);

        EditText name=findViewById(R.id.nameEditText);
        EditText email=findViewById(R.id.emailEditText);
        EditText password=findViewById(R.id.passwordEditText);
        EditText conPassword=findViewById(R.id.conPasswordEditText);

        EditText contact=findViewById(R.id.contactEditText);
        EditText address=findViewById(R.id.addressEditText);

        continueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String Name=name.getText().toString();
                String Email=email.getText().toString();
                String Password=password.getText().toString();
                String ConfirmPassword=conPassword.getText().toString();


                if (Name.isEmpty() || Email.isEmpty() || Password.isEmpty() || ConfirmPassword.isEmpty() ){
                    Toast toast= Toast.makeText(getApplicationContext(),"Please fill all required fields!",Toast.LENGTH_SHORT);
                    toast.show();
                }

             else if(!Password.equals(ConfirmPassword)){
                    Toast toast= Toast.makeText(getApplicationContext(),"Password and Confirm Password don't match!",Toast.LENGTH_SHORT);
                    toast.show();
             }

             else {
                 cardView1.setVisibility(View.GONE);
                 cardView2.setVisibility(View.VISIBLE);}

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                String Name=name.getText().toString();
                String Email=email.getText().toString();
                String Password=password.getText().toString();
                String ConfirmPassword=conPassword.getText().toString();
                String Contact=contact.getText().toString();
                String Address=address.getText().toString();

                if(Contact.isEmpty()|| Address.isEmpty()){
                    Toast toast= Toast.makeText(getApplicationContext(),"Please fill all required fields!",Toast.LENGTH_SHORT);
                    toast.show();
                }

                else{
                    DBHelper dbHelper = new DBHelper(getBaseContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DBHelper.COLUMN_CUSTOMER_NAME, Name);
                    values.put(DBHelper.COLUMN_CUSTOMER_EMAIL, Email);
                    values.put(DBHelper.COLUMN_CUSTOMER_PASSWORD, Password);
                    values.put(DBHelper.COLUMN_CUSTOMER_CONTACT, Contact);
                    values.put(DBHelper.COLUMN_CUSTOMER_ADDRESS, Address);

                    db.insert(DBHelper.TABLE_CUSTOMERS, null, values);
                    dbHelper.close();
                    Toast.makeText(CreateNewAccountActivity.this, "You have been registered!.", Toast.LENGTH_SHORT).show();

                    SQLiteDatabase dbr = dbHelper.getReadableDatabase();

                    String[] projection = {
                            DBHelper.COLUMN_CUSTOMER_ID,
                            DBHelper.COLUMN_CUSTOMER_NAME,
                            DBHelper.COLUMN_CUSTOMER_EMAIL,
                            DBHelper.COLUMN_CUSTOMER_PASSWORD,
                            DBHelper.COLUMN_CUSTOMER_CONTACT,
                            DBHelper.COLUMN_CUSTOMER_ADDRESS


                    };

                    Cursor cursor = dbr.query(
                            DBHelper.TABLE_CUSTOMERS, // The table name
                            projection,             // The columns to retrieve
                            null,                   // The selection criteria (null means all rows)
                            null,                   // Selection arguments (none in this case)
                            null,                   // Group by
                            null,                   // Having
                            null                    // Order by
                    );

                    while (cursor.moveToNext()) {
                        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CUSTOMER_ID));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CUSTOMER_NAME));
                        String email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CUSTOMER_EMAIL));
                        String password = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CUSTOMER_PASSWORD));
                        String contact = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CUSTOMER_CONTACT));
                        String address = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CUSTOMER_ADDRESS));

                        // Log the data
                        Log.d("Customer Data", "ID: " + id + ", Name: " + name + ", email: " + email+" Password :"+password+" Contact :"+contact+" Address :"+ address );
                    }

                    cursor.close();
                    dbHelper.close();



                }
            }
        });


    }


}