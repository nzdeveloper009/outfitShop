package com.nzdeveloper009.affordablefunctionoutfit.Buyer.RentProducts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses.AdapterMenShop;
import com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses.AdapterWomenShop;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.ModelShop;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class RentWomenProducts extends AppCompatActivity {

    private RecyclerView shopsRv;
    private ArrayList<ModelShop> shopList;
    private AdapterWomenShop adapterWomenShop;

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_women_products);

        Intent intent = getIntent();
        if(intent != null)
        {
            username = intent.getStringExtra("username");

            shopsRv = findViewById(R.id.shopsRv);
            loadAllShops();
        }
    }

    private void loadAllShops() {
        shopList = new ArrayList<>();
        //get all shops
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
        reference.child("SELLER").orderByChild("userType").equalTo("SELLER")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //clear list before adding
                        shopList.clear();
                        for(DataSnapshot ds: snapshot.getChildren())
                        {
                            ModelShop modelShop = ds.getValue(ModelShop.class);
                            shopList.add(modelShop);
                        }
                        //setup adapter
                        adapterWomenShop = new AdapterWomenShop(RentWomenProducts.this,shopList);
                        //set adapter to recyclerview
                        shopsRv.setAdapter(adapterWomenShop);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        TastyToast.makeText(getApplicationContext(),""+error.getMessage(),TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }
                });
    }
}