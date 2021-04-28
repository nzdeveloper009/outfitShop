package com.nzdeveloper009.affordablefunctionoutfit.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.nzdeveloper009.affordablefunctionoutfit.R;

public class AdminLogin extends AppCompatActivity {

    EditText PasswordLogIn;
    String strPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        PasswordLogIn = findViewById(R.id.PasswordLogIn);
    }

    public void logInFunction(View view) {

        if(validation())
        {
            startActivity(new Intent(getApplicationContext(),AdminDashboard.class));
        }

    }

    private boolean validation() {
        strPassword = PasswordLogIn.getText().toString();
        if(TextUtils.isEmpty(strPassword))
        {
            PasswordLogIn.setError("Please enter password!!!");
            PasswordLogIn.requestFocus();
            return false;
        }
        else if(strPassword.equals("admin"))
        {
            return true;
        }
        else{
            PasswordLogIn.setError("Please enter Valid Password!!!");
            PasswordLogIn.requestFocus();
            return false;
        }
    }
}