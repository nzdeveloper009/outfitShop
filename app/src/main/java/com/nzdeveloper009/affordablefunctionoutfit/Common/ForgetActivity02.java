package com.nzdeveloper009.affordablefunctionoutfit.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.UserHelperClass;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.sdsmdg.tastytoast.TastyToast;

public class ForgetActivity02 extends AppCompatActivity {


    EditText password;
    TextView changePassword,db_prePassword;
    String str_email,str_fullName,str_nicNo,str_password,str_phoneNo,logUserType,str_username,new_password;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget02);


        Intent intent = getIntent();
        str_email = intent.getStringExtra("db_email");
        str_fullName = intent.getStringExtra("db_fullName");
        str_nicNo = intent.getStringExtra("db_nicNo");
        str_password = intent.getStringExtra("db_password");
        str_phoneNo = intent.getStringExtra("db_phoneNo");
        logUserType = intent.getStringExtra("logUserType");
        str_username = intent.getStringExtra("db_username");



        password = findViewById(R.id.password);
        changePassword = findViewById(R.id.changePassword);
        db_prePassword = findViewById(R.id.db_pre_password);

        db_prePassword.setText(str_password);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_password = String.valueOf(password.getText());
                updatePasswordInDatabase();
            }
        });


    }

    private void updatePasswordInDatabase() {
        if(validation())
        {
            UserHelperClass helperClass = new UserHelperClass(str_fullName,str_username,str_phoneNo,str_email,new_password,str_nicNo,logUserType);
            reference.child(logUserType).child(str_username).setValue(helperClass)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            TastyToast.makeText(getApplicationContext(),""+e.getMessage(),TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            TastyToast.makeText(getApplicationContext(),"Password Changed Successfully",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }
                    });
        }
    }

    private boolean validation() {
        if(TextUtils.isEmpty(new_password))
        {
            password.setError("Enter Password For Update");
            password.requestFocus();
            return false;
        }
        return true;
    }

}