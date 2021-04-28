package com.nzdeveloper009.affordablefunctionoutfit.Admin.SellerData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nzdeveloper009.affordablefunctionoutfit.R;

public class exploreSeller extends AppCompatActivity {

    String fulName,uname,phoneNo,email,nicNo;
    TextView fullNameTv,usernameTV,phoneNoTv,emailTv,nicNoTv;
    CardView orderCv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_seller);

        fulName = getIntent().getStringExtra("fulName");
        uname = getIntent().getStringExtra("uname");
        phoneNo = getIntent().getStringExtra("phoneNo");
        email = getIntent().getStringExtra("email");
        nicNo = getIntent().getStringExtra("nicNo");

        //init ui
        fullNameTv = findViewById(R.id.fullNameTv);
        usernameTV = findViewById(R.id.usernameTV);
        phoneNoTv = findViewById(R.id.phoneNoTv);
        emailTv = findViewById(R.id.emailTv);
        nicNoTv = findViewById(R.id.nicNoTv);

        orderCv = findViewById(R.id.orderCv);


        //set Data
        fullNameTv.setText(fulName);
        usernameTV.setText(uname);
        phoneNoTv.setText(phoneNo);
        emailTv.setText(email);
        nicNoTv.setText(nicNo);

        orderCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SellerOrderList.class);
                i.putExtra("username",uname);
                startActivity(i);
            }
        });

    }
}