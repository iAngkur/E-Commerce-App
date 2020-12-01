package com.example.e_commerce.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_commerce.Prevalent.Prevalent;
import com.example.e_commerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    // Variables
    private EditText nameEditText, phoneEditText, addressEditText, cityEditText;
    private Button confirmOrderBtn;

    private String overAllTotalPrice = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        overAllTotalPrice = getIntent().getStringExtra("totalPrice");


        confirmOrderBtn = findViewById(R.id.confirm_order_btn);
        nameEditText = findViewById(R.id.shipping_name);
        phoneEditText = findViewById(R.id.shipping_phone_number);
        addressEditText = findViewById(R.id.shipping_address);
        cityEditText = findViewById(R.id.shipping_city);

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });
    }

    private void Check() {
        if (TextUtils.isEmpty(nameEditText.getText().toString())) {
            nameEditText.setError("Please Provide your Name");
            nameEditText.requestFocus();
        } else if (TextUtils.isEmpty(phoneEditText.getText().toString())) {
            phoneEditText.setError("Please Provide your Phone Number");
            phoneEditText.requestFocus();
        } else if (TextUtils.isEmpty(addressEditText.getText().toString())) {
            addressEditText.setError("Please Provide your Home Address");
            addressEditText.requestFocus();
        } else if (TextUtils.isEmpty(cityEditText.getText().toString())) {
            cityEditText.setError("Please Provide your City Name");
            cityEditText.requestFocus();
        } else {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        final HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalPrice", overAllTotalPrice);
        ordersMap.put("name", nameEditText.getText().toString());
        ordersMap.put("phone", phoneEditText.getText().toString());
        ordersMap.put("address", addressEditText.getText().toString());
        ordersMap.put("city", cityEditText.getText().toString());
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("state", "not shipped");

        orderRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ConfirmFinalOrderActivity.this, "Order Confirmed Successfully", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}