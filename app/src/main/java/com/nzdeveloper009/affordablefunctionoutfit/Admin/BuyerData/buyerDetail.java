package com.nzdeveloper009.affordablefunctionoutfit.Admin.BuyerData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses.AdapterBuyerDetails;
import com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses.AdapterSellerDetails;
import com.nzdeveloper009.affordablefunctionoutfit.Admin.SellerData.sellerDetail;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.UserHelperClass;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class buyerDetail extends AppCompatActivity {


    private RecyclerView buyerRV;
    private ArrayList<UserHelperClass> userHelperList;
    private AdapterBuyerDetails adapterBuyerDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_detail);


        buyerRV = findViewById(R.id.buyerRV);
        loadAllBuyer();

    }

    private void loadAllBuyer() {
        userHelperList = new ArrayList<>();
        //get all shops
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
        reference.child("BUYER").orderByChild("userType").equalTo("BUYER")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //clear list before adding
                        userHelperList.clear();
                        for(DataSnapshot ds: snapshot.getChildren())
                        {
                            UserHelperClass userHelperClass = ds.getValue(UserHelperClass.class);
                            userHelperList.add(userHelperClass);
                        }
                        //setup adapter
                        adapterBuyerDetails = new AdapterBuyerDetails(buyerDetail.this,userHelperList);
                        //set adapter to recyclerview
                        buyerRV.setAdapter(adapterBuyerDetails);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        TastyToast.makeText(getApplicationContext(),""+error.getMessage(),TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }
                });
    }
}