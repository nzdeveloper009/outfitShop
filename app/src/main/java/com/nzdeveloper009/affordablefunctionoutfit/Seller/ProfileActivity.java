package com.nzdeveloper009.affordablefunctionoutfit.Seller;


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
import com.sdsmdg.tastytoast.TastyToast;

public class ProfileActivity extends AppCompatActivity {

    EditText up_fullName,up_username,up_phoneNo,up_email,up_password,up_ConfirmPassword,up_nicNo;
    String str_fullName,username,str_phoneNo,str_email,str_password,str_nicNo,str_confirm_pass,userType="SELLER";
    TextView seller_update;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        up_fullName = findViewById(R.id.up_fullName);
        up_username = findViewById(R.id.up_username);
        up_phoneNo = findViewById(R.id.up_phoneNo);
        up_email = findViewById(R.id.up_email);
        up_password = findViewById(R.id.up_password);
        up_ConfirmPassword = findViewById(R.id.up_ConfirmPassword);
        up_nicNo = findViewById(R.id.up_nicNo);

        seller_update = findViewById(R.id.seller_update);

        Intent intent = getIntent();
        if(intent!=null)
            username = intent.getStringExtra("str_username");



        ref = FirebaseDatabase.getInstance().getReference("Credentials");
        ref.child("SELLER").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    str_fullName = snapshot.child("fullName").getValue(String.class);
                    str_phoneNo = snapshot.child("phoneNo").getValue(String.class);
                    str_email = snapshot.child("email").getValue(String.class);
                    str_nicNo = snapshot.child("nicNo").getValue(String.class);
                    str_password = snapshot.child("password").getValue(String.class);

                    up_fullName.setText(str_fullName);
                    up_username.setText(username);
                    up_phoneNo.setText(str_phoneNo);
                    up_email.setText(str_email);
                    up_password.setText(str_password);
                    up_ConfirmPassword.setText(str_password);
                    up_nicNo.setText(str_nicNo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        seller_update.setOnClickListener(new View.OnClickListener() {
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
        str_fullName = up_fullName.getText().toString().trim();
        username = up_username.getText().toString().trim().toLowerCase();
        str_phoneNo = up_phoneNo.getText().toString().trim();
        str_email = up_email.getText().toString().trim();
        str_password = up_password.getText().toString().trim();
        str_confirm_pass = up_ConfirmPassword.getText().toString().trim();
        str_nicNo = up_nicNo.getText().toString().trim();


        if (TextUtils.isEmpty(str_fullName)) {
            up_fullName.setError("Enter Full Name");
            up_fullName.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(username)) {
            up_username.setError("Enter username");
            up_username.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_phoneNo)) {
            up_phoneNo.setError("Enter Phone No.");
            up_phoneNo.requestFocus();
            return true;
        }
        if (TextUtils.isEmpty(str_email)) {
            up_email.setError("Enter Email");
            up_email.requestFocus();
            return true;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
            up_email.setError("Please enter valid email.");
            up_email.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_password)) {
            up_password.setError("Enter Password");
            up_password.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_confirm_pass)) {
            up_ConfirmPassword.setError("Enter Confirm Password");
            up_ConfirmPassword.requestFocus();
            return true;
        }

        if (!str_password.equals(str_confirm_pass)) {
            up_ConfirmPassword.setError("Password Not Matched");
            up_ConfirmPassword.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_nicNo)) {
            up_nicNo.setError("Please Enter NIC 00000-0000000-0");
            up_nicNo.requestFocus();
            return true;
        }

        return false;
    }


}