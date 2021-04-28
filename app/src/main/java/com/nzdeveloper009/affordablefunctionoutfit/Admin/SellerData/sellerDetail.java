package com.nzdeveloper009.affordablefunctionoutfit.Admin.SellerData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses.AdapterSellerDetails;
import com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses.AdapterSpecialShop;
import com.nzdeveloper009.affordablefunctionoutfit.Buyer.RentProducts.RentSpecialProducts;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.ModelShop;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.UserHelperClass;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class sellerDetail extends AppCompatActivity {

    private RecyclerView sellerRV;
    private ArrayList<UserHelperClass> userHelperList;
    private AdapterSellerDetails adapterSellerDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_detail);

        sellerRV = findViewById(R.id.sellerRV);
        loadAllSeller();

    }

    private void loadAllSeller() {
        userHelperList = new ArrayList<>();
        //get all shops
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
        reference.child("SELLER").orderByChild("userType").equalTo("SELLER")
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
                        adapterSellerDetails = new AdapterSellerDetails(sellerDetail.this,userHelperList);
                        //set adapter to recyclerview
                        sellerRV.setAdapter(adapterSellerDetails);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        TastyToast.makeText(getApplicationContext(),""+error.getMessage(),TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }
                });
    }
}