package com.nzdeveloper009.affordablefunctionoutfit.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.sdsmdg.tastytoast.TastyToast;

public class ForgetActivity extends AppCompatActivity {

    EditText username;
    TextView next, userTypeBuyer, userTypeSeller,goToLogin;
    String logUserType = "", str_username;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        username = findViewById(R.id.username);

        userTypeBuyer = findViewById(R.id.Login_typeBuyer);
        userTypeSeller = findViewById(R.id.Login_typeSeller);
        next = findViewById(R.id.checkbtn);
        goToLogin = findViewById(R.id.idNotRegister);

        userTypeBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validation()) {
                    logUserType = "BUYER";
                    userTypeBuyer.setBackgroundResource(R.drawable.select_btn_buyer);
                    next.setBackgroundResource(R.drawable.selected_btn_buyer_sign);
                    userTypeSeller.setBackgroundResource(R.drawable.round_btn_seller);
                }
            }
        });

        userTypeSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validation()) {
                    logUserType = "SELLER";
                    userTypeBuyer.setBackgroundResource(R.drawable.round_btn_buyer);
                    next.setBackgroundResource(R.drawable.select_btn_seller);
                    userTypeSeller.setBackgroundResource(R.drawable.selected_btn_seller_sign);
                }

            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),LoginActivity.class));
               finish();

            }
        });
    }

    private boolean validation() {
        str_username = username.getText().toString().trim().toLowerCase();
        if (TextUtils.isEmpty(str_username)) {
            username.setError("Enter username");
            username.requestFocus();
            return true;
        }
        return false;

    }

    public void Next(View view) {
        if (!logUserType.equals("")) {

            reference.child(logUserType).child(str_username)
                    .addListenerForSingleValueEvent(listener);

        } else {
            TastyToast.makeText(getApplicationContext(), "Select Your Type", TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
        }
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {

                String db_email = snapshot.child("email").getValue(String.class);
                String db_fullName = snapshot.child("fullName").getValue(String.class);
                String db_nicNo = snapshot.child("nicNo").getValue(String.class);
                String db_password = snapshot.child("password").getValue(String.class);
                String db_phoneNo = snapshot.child("phoneNo").getValue(String.class);
                String db_username = snapshot.child("username").getValue(String.class);


                Intent intent = new Intent(getApplicationContext(),ForgetActivity02.class);
                intent.putExtra("db_email",db_email);
                intent.putExtra("db_fullName",db_fullName);
                intent.putExtra("db_nicNo",db_nicNo);
                intent.putExtra("db_password",db_password);
                intent.putExtra("db_phoneNo",db_phoneNo);
                intent.putExtra("db_username",db_username);
                intent.putExtra("logUserType",logUserType);
                startActivity(intent);
                finish();

            } else {
                    TastyToast.makeText(getApplicationContext(), "Record Not Find", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
                }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}