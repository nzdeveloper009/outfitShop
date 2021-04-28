package com.nzdeveloper009.affordablefunctionoutfit.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nzdeveloper009.affordablefunctionoutfit.Common.LoginActivity;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.UserHelperClass;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.ProfileActivity;
import com.sdsmdg.tastytoast.TastyToast;

public class BuyerProfileActivity extends AppCompatActivity {

    EditText buy_fullName,buy_username,buy_phoneNo,buy_email,buy_password,buy_ConfirmPassword,buy_nicNo;
    String str_fullName,username,str_phoneNo,str_email,str_password,str_nicNo,str_confirm_pass,userType="BUYER";
    TextView tv_Update;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_profile);

        Intent intent = getIntent();
        if(intent!=null)
            username = intent.getStringExtra("str_username");

        buy_fullName = findViewById(R.id.up_fullName);
        buy_username = findViewById(R.id.buyer_username);
        buy_phoneNo = findViewById(R.id.buyer_phoneNo);
        buy_email = findViewById(R.id.buyer_email);
        buy_password = findViewById(R.id.buyer_password);
        buy_ConfirmPassword = findViewById(R.id.buyer_ConfirmPassword);
        buy_nicNo = findViewById(R.id.buyer_nicNo);


        tv_Update = findViewById(R.id.tv_Update);


        ref = FirebaseDatabase.getInstance().getReference("Credentials");
        ref.child(userType).child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    str_fullName = snapshot.child("fullName").getValue(String.class);
                    str_phoneNo = snapshot.child("phoneNo").getValue(String.class);
                    str_email = snapshot.child("email").getValue(String.class);
                    str_nicNo = snapshot.child("nicNo").getValue(String.class);
                    str_password = snapshot.child("password").getValue(String.class);

                    buy_fullName.setText(str_fullName);
                    buy_username.setText(username);
                    buy_phoneNo.setText(str_phoneNo);
                    buy_email.setText(str_email);
                    buy_password.setText(str_password);
                    buy_ConfirmPassword.setText(str_password);
                    buy_nicNo.setText(str_nicNo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        tv_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validation()){
                    UserHelperClass helperClass = new UserHelperClass(str_fullName,username,str_phoneNo,str_email,str_password,str_nicNo,userType);
                    ref.child(userType).child(username).setValue(helperClass)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    TastyToast.makeText(getApplicationContext(),e.toString(),TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    TastyToast.makeText(getApplicationContext(),"Updation Successfully",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();
                                }
                            });
                }
            }
        });

    }

    private boolean validation() {
        str_fullName = buy_fullName.getText().toString().trim();
        username = buy_username.getText().toString().trim().toLowerCase();
        str_phoneNo = buy_phoneNo.getText().toString().trim();
        str_email = buy_email.getText().toString().trim();
        str_password = buy_password.getText().toString().trim();
        str_confirm_pass = buy_ConfirmPassword.getText().toString().trim();
        str_nicNo = buy_nicNo.getText().toString().trim();


        if (TextUtils.isEmpty(str_fullName)) {
            buy_fullName.setError("Enter Full Name");
            buy_fullName.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(username)) {
            buy_username.setError("Enter username");
            buy_username.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_phoneNo)) {
            buy_phoneNo.setError("Enter Phone No.");
            buy_phoneNo.requestFocus();
            return true;
        }
        if (TextUtils.isEmpty(str_email)) {
            buy_email.setError("Enter Email");
            buy_email.requestFocus();
            return true;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
            buy_email.setError("Please enter valid email.");
            buy_email.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_password)) {
            buy_password.setError("Enter Password");
            buy_password.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_confirm_pass)) {
            buy_ConfirmPassword.setError("Enter Confirm Password");
            buy_ConfirmPassword.requestFocus();
            return true;
        }

        if (!str_password.equals(str_confirm_pass)) {
            buy_ConfirmPassword.setError("Password Not Matched");
            buy_ConfirmPassword.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_nicNo)) {
            buy_nicNo.setError("Please Enter NIC 00000-0000000-0");
            buy_nicNo.requestFocus();
            return true;
        }

        return false;
    }
}