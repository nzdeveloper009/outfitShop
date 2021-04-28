package com.nzdeveloper009.affordablefunctionoutfit.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nzdeveloper009.affordablefunctionoutfit.Buyer.BuyerDashBoardActivity;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.SellerDashBoardActivity;
import com.sdsmdg.tastytoast.TastyToast;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    TextView idgoToSignUpPage, idForgotPassword, logInButton, logTypeBuyer, logTypeSeller;
    String logUserType = "", str_username, str_password;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");

    CheckBox mRemember;
    SharedPreferences sharedPreferences;
    boolean isRemembered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        logTypeBuyer = findViewById(R.id.Login_typeBuyer);
        logTypeSeller = findViewById(R.id.Login_typeSeller);


        logInButton = findViewById(R.id.idLogIn);
        idgoToSignUpPage = findViewById(R.id.idNotRegister);
        idForgotPassword = findViewById(R.id.idforgotPassword);

        if(isNetworkOnline(getApplicationContext()))
        {

            if (isRemembered) {
                Intent i = getIntent();
                String userType = i.getStringExtra("logUserType");
                if (userType != null) {
                    logUserType = i.getStringExtra("logUserType");
                }
            }

            edtUsername = findViewById(R.id.emailLogIn);
            edtPassword = findViewById(R.id.PasswordLogIn);


            mRemember = findViewById(R.id.checkbox);


            sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
            isRemembered = sharedPreferences.getBoolean("CHECKBOX", false); // default value of checkbox is false, when value is true we move another activity

            if (isRemembered) {
                Intent intent;
                if (logUserType.equals("BUYER")) {
                    intent = new Intent(getApplicationContext(), BuyerDashBoardActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), SellerDashBoardActivity.class);
                }
                intent.putExtra("str_username", str_username);
                startActivity(intent);
                finish();
            }
            logTypeBuyer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validation()) {

                    } else {
                        logUserType = "BUYER";
                        logTypeBuyer.setBackgroundResource(R.drawable.select_btn_buyer);
                        logInButton.setBackgroundResource(R.drawable.selected_btn_buyer_sign);
                        logTypeSeller.setBackgroundResource(R.drawable.round_btn_seller);
                    }
                }
            });

            logTypeSeller.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validation()) {

                    } else {
                        logUserType = "SELLER";
                        logTypeBuyer.setBackgroundResource(R.drawable.round_btn_buyer);
                        logInButton.setBackgroundResource(R.drawable.select_btn_seller);
                        logTypeSeller.setBackgroundResource(R.drawable.selected_btn_seller_sign);

                    }

                }
            });


            idgoToSignUpPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
                    finish();
                }
            });

            idForgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), ForgetActivity.class));
                }
            });
        }
        else{
            TastyToast.makeText(getApplicationContext(),"Before use please connect to internet, and restart your app",TastyToast.LENGTH_LONG,TastyToast.WARNING);
            logTypeBuyer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TastyToast.makeText(getApplicationContext(),"Please connect to internet, and restart your app",TastyToast.LENGTH_LONG,TastyToast.WARNING);
                }
            });

            logTypeSeller.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TastyToast.makeText(getApplicationContext(),"Please connect to internet, and restart your app",TastyToast.LENGTH_LONG,TastyToast.WARNING);
                }
            });


            idgoToSignUpPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TastyToast.makeText(getApplicationContext(),"Please connect to internet, and restart your app",TastyToast.LENGTH_LONG,TastyToast.WARNING);
                }
            });

            idForgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TastyToast.makeText(getApplicationContext(),"Please connect to internet, and restart your app",TastyToast.LENGTH_LONG,TastyToast.WARNING);
                }
            });
            logInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TastyToast.makeText(getApplicationContext(),"Please connect to internet, and restart your app",TastyToast.LENGTH_LONG,TastyToast.WARNING);
                }
            });
        }
    }

    private boolean validation() {
        str_username = edtUsername.getText().toString().trim().toLowerCase();
        str_password = edtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(str_username)) {
            edtUsername.setError("Enter username");
            edtUsername.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(str_password)) {
            edtPassword.setError("Enter Password");
            edtPassword.requestFocus();
            return true;
        }
        return false;

    }

    public void logInFunction(View view) {

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
                String db_password = snapshot.child("password").getValue(String.class);
                if (str_password.equals(db_password)) {
                    boolean checked = mRemember.isChecked();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("str_username", str_username);
                    editor.putString("str_password", str_password);
                    editor.putString("logUserType", logUserType);
                    editor.putBoolean("CHECKBOX", checked);
                    editor.apply();

                    // bnd kr k jb open krte hai to seller he open hoti hai q k logUserType me vallue koi rehti nhi to condition false ho jati to else ki body chal prti
                    Intent intent;
                    if (logUserType.equals("BUYER")) {
                        intent = new Intent(getApplicationContext(), BuyerDashBoardActivity.class);
                    } else {
                        intent = new Intent(getApplicationContext(), SellerDashBoardActivity.class);
                    }
                    intent.putExtra("str_username", str_username);
                    TastyToast.makeText(getApplicationContext(), "Login Successfully", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    startActivity(intent);
                    finish();
                }
            } else {
                TastyToast.makeText(getApplicationContext(), "Record Not Find", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            TastyToast.makeText(getApplicationContext(), error.toString(), TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
        }
    };

    public static boolean isNetworkOnline(Context con)
    {
        boolean status = false;
        try
        {
            ConnectivityManager cm = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);

            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);

                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    status = true;
                } else {
                    status = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return status;
    }

}