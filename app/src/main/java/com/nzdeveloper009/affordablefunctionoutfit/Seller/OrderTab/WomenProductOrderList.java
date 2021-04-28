package com.nzdeveloper009.affordablefunctionoutfit.Seller.OrderTab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses.AdapterOrderSeller;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.ModelOrderBuyer;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class WomenProductOrderList extends AppCompatActivity {

    EditText filterOrdersTv;
    ImageButton filterOrderBtn;
    RecyclerView ordersRv;

    String sellerUsername;

    private ArrayList<ModelOrderBuyer> orderShopArrayList;
    private AdapterOrderSeller adapterOrderSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women_product_order_list);
        sellerUsername = getIntent().getStringExtra("username");

        filterOrdersTv = findViewById(R.id.filterOrdersTv);
        filterOrderBtn = findViewById(R.id.filterOrderBtn);
        ordersRv = findViewById(R.id.ordersRv);

        loadAllOrders();

        filterOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //options to display in dialog
                String[] options = {"All", "In Progress", "Completed", "Cancelled"};
                //dialog
                //dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(WomenProductOrderList.this);
                builder.setTitle("Filter Orders:")
                        .setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //handle item clicks
                                if (which == 0) {
                                    //All clicked
                                    String str = "Showing All Orders";
                                    filterOrdersTv.setText(str);
                                    adapterOrderSeller.getFilter().filter("");// show all order
                                } else {
                                    String optionClicked = options[which];
                                    String clicked = "Showing " + optionClicked + " Orders";
                                    filterOrdersTv.setText(clicked); //Showing Completed Orders
                                    adapterOrderSeller.getFilter().filter(optionClicked);
                                }
                            }
                        })
                        .show();
            }
        });


    }

    private void loadAllOrders() {
        //init array list
        orderShopArrayList = new ArrayList<>();

        //load orders of shop
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
        reference.child("SELLER").child(sellerUsername).child("Orders").child("Women Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Clear list before addinf new data in it
                        orderShopArrayList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ModelOrderBuyer modelOrderBuyer = ds.getValue(ModelOrderBuyer.class);
                            //add to list
                            orderShopArrayList.add(modelOrderBuyer);
                        }
                        //setup adapter
                        adapterOrderSeller = new AdapterOrderSeller(getApplicationContext(), orderShopArrayList);
                        //set adapter to recyclerview
                        ordersRv.setAdapter(adapterOrderSeller);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        TastyToast.makeText(getApplicationContext(), "" + error.getMessage(), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }
                });
    }
}