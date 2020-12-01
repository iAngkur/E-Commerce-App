package com.example.e_commerce.Selllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.e_commerce.R;

public class SellerLoginActivity extends AppCompatActivity {

    Button sellerGotoRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_seller_login);

        sellerGotoRegister = findViewById(R.id.seller_already_have_account_btn);


        sellerGotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerLoginActivity.this, SellerRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}