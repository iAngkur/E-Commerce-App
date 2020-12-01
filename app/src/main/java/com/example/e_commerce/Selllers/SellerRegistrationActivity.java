package com.example.e_commerce.Selllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.e_commerce.Buyers.MainActivity;
import com.example.e_commerce.R;

public class SellerRegistrationActivity extends AppCompatActivity {

    Button sellerGotoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_seller_registration);

        sellerGotoLogin = findViewById(R.id.seller_already_have_account_btn);


        sellerGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}