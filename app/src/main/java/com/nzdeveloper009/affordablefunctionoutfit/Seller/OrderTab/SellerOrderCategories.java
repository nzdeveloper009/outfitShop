package com.nzdeveloper009.affordablefunctionoutfit.Seller.OrderTab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nzdeveloper009.affordablefunctionoutfit.R;

public class SellerOrderCategories extends AppCompatActivity {

    CardView menProductOrders,womenProductOrders,specialProductOrders;
    String sellerUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_order_categories);

        sellerUsername = getIntent().getStringExtra("str_username");
        menProductOrders = findViewById(R.id.menProductOrders);
        womenProductOrders = findViewById(R.id.womenProductOrders);
        specialProductOrders = findViewById(R.id.specialProductOrders);

        menProductOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MenProductOrderList.class);
                i.putExtra("username",sellerUsername);
                startActivity(i);
            }
        });

        womenProductOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),WomenProductOrderList.class);
                i.putExtra("username",sellerUsername);
                startActivity(i);
            }
        });

        specialProductOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SpecialProductOrderList.class);
                i.putExtra("username",sellerUsername);
                startActivity(i);
            }
        });

    }
}