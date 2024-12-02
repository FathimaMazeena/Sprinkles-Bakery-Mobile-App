package com.example.sprinklesbakery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;


public class LoginActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this);

        EditText email=findViewById(R.id.emailEditText2);
        EditText password=findViewById(R.id.passwordEditText2);
        Button loginButton=findViewById(R.id.addToCartButton);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();

                String adminEmail = "admin@gmail.com";
                String adminPassword = "admin123";

                String[] customerName = new String[1];

                if(Email.isEmpty()||Password.isEmpty()){
                    Toast toast= Toast.makeText(getApplicationContext(),"Please fill all required fields!",Toast.LENGTH_SHORT);
                    toast.show();
                }

                else if (dbHelper.login(Email, Password, customerName)) {


                    SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();



                    Intent intent = new Intent(LoginActivity.this, UserCategory.class);
                    intent.putExtra("CUSTOMER_NAME", customerName[0]);
                    startActivity(intent);
                    finish();

                } else if (Email.equals(adminEmail) && Password.equals(adminPassword)) {

                    SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, AdminCategory.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}