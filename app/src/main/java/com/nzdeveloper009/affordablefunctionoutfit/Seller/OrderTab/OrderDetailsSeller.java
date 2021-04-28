package com.nzdeveloper009.affordablefunctionoutfit.Seller.OrderTab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;

public class OrderDetailsSeller extends AppCompatActivity {

    String orderId, orderBy, orderTo, orderCost, orderTime, orderStatus, orderImgUri, orderPhoneNo, brandName;
    String orderAddress, orderNic,cat;

    ImageButton editBtn, callBtn;
    ImageView productIconIv;
    TextView orderIdTv, orderByTv, orderBrandTv, dateTv, orderStatusTv, orderNICTv, phoneTv, addressTv, amountTv;


    String selectedOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_seller);

        //get data from intent
        Intent i = getIntent();
        if (i != null) {

            orderId = i.getStringExtra("orderId");
            orderBy = i.getStringExtra("orderBy");
            orderTo = i.getStringExtra("orderTo");
            orderCost = i.getStringExtra("orderCost");
            orderTime = i.getStringExtra("orderTime");
            orderStatus = i.getStringExtra("orderStatus");
            orderImgUri = i.getStringExtra("orderImgUri");
            orderPhoneNo = i.getStringExtra("orderPhoneNo");
            brandName = i.getStringExtra("brandName");
            orderAddress = i.getStringExtra("orderAddress");
            orderNic = i.getStringExtra("orderNic");
            cat = i.getStringExtra("cat");


            //init ui
            editBtn = findViewById(R.id.editBtn);
            callBtn = findViewById(R.id.callBtn);

            productIconIv = findViewById(R.id.productIconIv);

            orderIdTv = findViewById(R.id.orderIdTv);
            orderByTv = findViewById(R.id.orderByTv);
            orderBrandTv = findViewById(R.id.orderBrandTv);
            dateTv = findViewById(R.id.dateTv);
            orderStatusTv = findViewById(R.id.orderStatusTv);
            orderNICTv = findViewById(R.id.orderNICTv);
            phoneTv = findViewById(R.id.phoneTv);
            addressTv = findViewById(R.id.addressTv);
            amountTv = findViewById(R.id.amountTv);

            //set data

            try {
                Picasso.with(getApplicationContext()).load(orderImgUri).placeholder(R.drawable.ic_baseline_shopping_cart_24).into(productIconIv);
            } catch (Exception e) {
                productIconIv.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
            }

            orderIdTv.setText(orderId);
            orderByTv.setText(orderBy);
            orderBrandTv.setText(brandName);
            //convert timestamp to proper format
            dateTv.setText(TimestampToDate(orderTime));

            orderStatusTv.setText(orderStatus);
            //change order status text color
            switch (orderStatus) {
                case "In Progress":
                    orderStatusTv.setTextColor(getResources().getColor(R.color.black));
                    break;
                case "Completed":
                    orderStatusTv.setTextColor(getResources().getColor(R.color.green));
                    break;
                case "Cancelled":
                    orderStatusTv.setTextColor(getResources().getColor(R.color.red));
                    break;
            }
            orderNICTv.setText(orderNic);
            phoneTv.setText(orderPhoneNo);
            addressTv.setText(orderAddress);
            String cost = orderCost + " pkr";
            amountTv.setText(cost);

            callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    String buyerPhoneNoUri = "tel:" + orderPhoneNo;
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(buyerPhoneNoUri));
                    startActivity(intent);
                }
            });

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editOrderStatusDialog();
                }
            });

        }

    }

    private String TimestampToDate(String orderTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(orderTime));
        String formatedDate = DateFormat.format("dd/MM/yyy", calendar).toString(); //e.g. 16/06/2021
        return formatedDate;
    }

    private void editOrderStatusDialog() {
        //option to display in dialog
        String[] options = {"In Progress", "Completed", "Cancelled"};
        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Order Status")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //handle item clicks
                        selectedOptions = options[which];
                        orderStatusTv.setText(selectedOptions);
                        editOrderStatus(selectedOptions);
                    }
                })
                .show();
    }

    private void editOrderStatus(String selectedOptions) {
        //setup data to put in firebase db
        String timestamp = ""+System.currentTimeMillis();

        if(selectedOptions.equals("In Progress") || selectedOptions.equals("Cancelled"))
        {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("orderStatus", "" + selectedOptions);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Credentials");
            ref.child("SELLER").child(orderTo).child("Orders").child(cat).child(orderId)
                    .updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //status updated
                            TastyToast.makeText(getApplicationContext(), "Order is now " + selectedOptions, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                            ;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //failed updating status, show reason
                            TastyToast.makeText(getApplicationContext(), "" + e.getMessage(), TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
                        }
                    });
        }
        if(selectedOptions.equals("Completed"))
        {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("orderStatus", "" + selectedOptions);
            hashMap.put("deliveryDate",""+timestamp);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Credentials");
            ref.child("SELLER").child(orderTo).child("Orders").child(cat).child(orderId)
                    .updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //status updated
                            TastyToast.makeText(getApplicationContext(), "Order is now " + selectedOptions, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                            ;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //failed updating status, show reason
                            TastyToast.makeText(getApplicationContext(), "" + e.getMessage(), TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
                        }
                    });
        }
    }


}