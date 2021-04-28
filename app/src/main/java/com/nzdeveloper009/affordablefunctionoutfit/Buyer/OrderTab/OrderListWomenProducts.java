package com.nzdeveloper009.affordablefunctionoutfit.Buyer.OrderTab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses.AdapterOrderBuyer;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.ModelOrderBuyer;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class OrderListWomenProducts extends AppCompatActivity {

    private RecyclerView ordersRv;
    private ArrayList<ModelOrderBuyer> ordersList;
    private AdapterOrderBuyer adapterOrderBuyer;
    String buyerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list_women_products);
        Intent i = getIntent();
        if (i != null) {
            buyerName = i.getStringExtra("username");
            ordersRv = findViewById(R.id.ordersRv);
            loadOrders();
        }
    }

    private void loadOrders() {
        //init order list
        ordersList = new ArrayList<>();

        //get all orders
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
        reference.child("SELLER")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //clear list before adding
                        ordersList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String uid = "" + ds.getRef().getKey();

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Credentials").child("SELLER").child(uid).child("Orders").child("Women Products");
                            ref.orderByChild("orderBy").equalTo(buyerName)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                for (DataSnapshot ds : snapshot.getChildren()) {
                                                    ModelOrderBuyer modelOrderBuyer = ds.getValue(ModelOrderBuyer.class);

                                                    //add to list
                                                    ordersList.add(modelOrderBuyer);
                                                }
                                                //setup adapter
                                                adapterOrderBuyer = new AdapterOrderBuyer(OrderListWomenProducts.this, ordersList);
                                                //set to recyclerview
                                                ordersRv.setAdapter(adapterOrderBuyer);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            TastyToast.makeText(getApplicationContext(), "" + error.getMessage(), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                                        }
                                    });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        TastyToast.makeText(getApplicationContext(), "" + error.getMessage(), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }
                });
    }
}