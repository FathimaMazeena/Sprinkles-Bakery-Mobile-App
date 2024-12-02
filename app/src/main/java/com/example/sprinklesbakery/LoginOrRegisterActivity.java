package com.example.sprinklesbakery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginOrRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        TextView logintextview=findViewById(R.id.logintextview);
        TextView newAccount=findViewById(R.id.createAnAccountTextView);

        logintextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //logintextview.setTextColor(R.color.darkAdditional);
                Intent intent=new Intent(LoginOrRegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //newAccount.setTextColor(R.color.darkAdditional);
                Intent intent=new Intent(LoginOrRegisterActivity.this,CreateNewAccountActivity.class);
                startActivity(intent);
            }
        });

    }
}