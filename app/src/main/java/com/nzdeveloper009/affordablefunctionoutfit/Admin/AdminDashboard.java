package com.nzdeveloper009.affordablefunctionoutfit.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nzdeveloper009.affordablefunctionoutfit.Admin.BuyerData.buyerDetail;
import com.nzdeveloper009.affordablefunctionoutfit.Admin.SellerData.sellerDetail;
import com.nzdeveloper009.affordablefunctionoutfit.R;

public class AdminDashboard extends AppCompatActivity {

    CardView seller,buyer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        seller = findViewById(R.id.seller);
        buyer = findViewById(R.id.buyer);

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), sellerDetail.class));
            }
        });
        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), buyerDetail.class));
            }
        });
    }
}