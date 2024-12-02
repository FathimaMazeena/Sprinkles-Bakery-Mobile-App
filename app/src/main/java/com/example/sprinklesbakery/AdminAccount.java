package com.example.sprinklesbakery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account);

        Button signout=findViewById(R.id.signoutButton);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminAccount.this, LoginOrRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.adminNavigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_cupcake);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.navigation_cupcake) {
                    startActivity(new Intent(AdminAccount.this, AdminCategory.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if(item.getItemId() == R.id.navigation_account){
                    Intent intent = new Intent(AdminAccount.this, AdminAccount.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }



                return false;
            }
        });
    }
}