package com.nzdeveloper009.affordablefunctionoutfit.Admin.BuyerData;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.nzdeveloper009.affordablefunctionoutfit.R;

public class exploreBuyer extends AppCompatActivity {

    String fulName,uname,phoneNo,email,nicNo;
    TextView fullNameTv,usernameTV,phoneNoTv,emailTv,nicNoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_buyer);

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

        //set Data
        fullNameTv.setText(fulName);
        usernameTV.setText(uname);
        phoneNoTv.setText(phoneNo);
        emailTv.setText(email);
        nicNoTv.setText(nicNo);
    }
}