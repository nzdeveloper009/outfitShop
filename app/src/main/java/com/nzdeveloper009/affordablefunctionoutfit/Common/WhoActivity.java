package com.nzdeveloper009.affordablefunctionoutfit.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nzdeveloper009.affordablefunctionoutfit.Admin.AdminLogin;
import com.nzdeveloper009.affordablefunctionoutfit.R;

public class WhoActivity extends AppCompatActivity {

    TextView adminBtn,otherBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who);

        adminBtn = findViewById(R.id.adminBtn);
        otherBtn = findViewById(R.id.otherBtn);

        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
                finish();
            }
        });
        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }
}