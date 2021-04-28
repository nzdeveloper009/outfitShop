package com.nzdeveloper009.affordablefunctionoutfit.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.UserHelperClass;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.sdsmdg.tastytoast.TastyToast;

public class RegistrationActivity extends AppCompatActivity {

    // Variables
    EditText reg_fullName, reg_username, reg_phoneNo, reg_email, reg_password, reg_ConfirmPassword, reg_nicNo;
    String userType="", str_fullName, str_phoneNo, str_ConfirmPassword, str_nicNo, str_username, str_email, str_password;
    TextView idAllreadyRegister, reg_btn_typeSeller, reg_btn_typeBuyer, reg_btn_idSignUp;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Hooks
        reg_fullName = findViewById(R.id.fullName);
        reg_username = findViewById(R.id.username);
        reg_phoneNo = findViewById(R.id.phoneNo);
        reg_email = findViewById(R.id.email);
        reg_password = findViewById(R.id.password);
        reg_ConfirmPassword = findViewById(R.id.ConfirmPassword);
        reg_nicNo = findViewById(R.id.nicNo);

        reg_btn_typeBuyer = findViewById(R.id.typeBuyer);
        reg_btn_typeSeller = findViewById(R.id.typeSeller);
        reg_btn_idSignUp = findViewById(R.id.idSignUp);

        idAllreadyRegister = findViewById(R.id.idAllreadyRegister);

        reg_btn_typeBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {

                } else {
                    userType = "BUYER";
                    reg_btn_typeBuyer.setBackgroundResource(R.drawable.select_btn_buyer);
                    reg_btn_idSignUp.setBackgroundResource(R.drawable.selected_btn_buyer_sign);
                    reg_btn_typeSeller.setBackgroundResource(R.drawable.round_btn_seller);
                }

            }
        });

        reg_btn_typeSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {

                } else {
                    userType = "SELLER";
                    reg_btn_typeBuyer.setBackgroundResource(R.drawable.round_btn_buyer);
                    reg_btn_typeSeller.setBackgroundResource(R.drawable.select_btn_seller);
                    reg_btn_idSignUp.setBackgroundResource(R.drawable.selected_btn_seller_sign);
                }

            }
        });


        idAllreadyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        reg_btn_idSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userType.equals(""))
                {
                    TastyToast.makeText(getApplicationContext(),"Select Your Type",TastyToast.LENGTH_SHORT,TastyToast.WARNING).show();
                }
                else{
                    UserHelperClass helperClass = new UserHelperClass(str_fullName,str_username,str_phoneNo,str_email,str_password,str_nicNo,userType);
                    reference.child(userType).child(str_username).setValue(helperClass)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            TastyToast.makeText(getApplicationContext(),"Not Registered Successfully",TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            TastyToast.makeText(getApplicationContext(),"Registered Successfully",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }
                    });

                }

            }
        });

    }

    private boolean validation() {
        str_fullName = reg_fullName.getText().toString().trim();
        str_username = reg_username.getText().toString().trim().toLowerCase();
        str_phoneNo = reg_phoneNo.getText().toString().trim();
        str_email = reg_email.getText().toString().trim();
        str_password = reg_password.getText().toString().trim();
        str_ConfirmPassword = reg_ConfirmPassword.getText().toString().trim();
        str_nicNo = reg_nicNo.getText().toString().trim();

        if (TextUtils.isEmpty(str_fullName)) {
            reg_fullName.setError("Enter Full Name");
            reg_fullName.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_username)) {
            reg_username.setError("Enter username");
            reg_username.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_phoneNo)) {
            reg_phoneNo.setError("Enter Phone No.");
            reg_phoneNo.requestFocus();
            return true;
        }
        if (TextUtils.isEmpty(str_email)) {
            reg_email.setError("Enter Email");
            reg_email.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_email)) {
            reg_email.setError("Enter Email");
            reg_email.requestFocus();
            return true;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
            reg_email.setError("Please enter valid email.");
            reg_email.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_password)) {
            reg_password.setError("Enter Password");
            reg_password.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_ConfirmPassword)) {
            reg_ConfirmPassword.setError("Enter Confirm Password");
            reg_ConfirmPassword.requestFocus();
            return true;
        }

        if (!str_password.equals(str_ConfirmPassword)) {
            reg_ConfirmPassword.setError("Password Not Matched");
            reg_ConfirmPassword.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_nicNo)) {
            reg_nicNo.setError("Please Enter NIC 00000-0000000-0");
            reg_nicNo.requestFocus();
            return true;
        }

        return false;
    }

}