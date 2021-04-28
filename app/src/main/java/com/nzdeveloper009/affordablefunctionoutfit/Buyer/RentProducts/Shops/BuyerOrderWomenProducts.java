package com.nzdeveloper009.affordablefunctionoutfit.Buyer.RentProducts.Shops;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;

public class BuyerOrderWomenProducts extends AppCompatActivity {



    String str_brandName, str_productAddress, str_productDescription, sellerName;
    String pSPrice, pAprice, pIcon, finalPrice;
    EditText buyerName, buyerAddress, buyerPhoneNo,buyerNIC;
    TextView originalPrice, secuityPrice, totalPrice, orderBtn, brandName, productAddress, productDescription;
    ImageView productIv;

    int tPrice, price, sPrice;

    //variable for store buyer data
    String username,address,phoneNo,nic;
    boolean check = false;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_order_women_products);

        Intent i = getIntent();
        if (i != null) {
            str_brandName = i.getStringExtra("pBrand");
            str_productAddress = i.getStringExtra("pAddress");
            str_productDescription = i.getStringExtra("pDesc");
            pSPrice = i.getStringExtra("pSPrice");
            pAprice = i.getStringExtra("pAPrice");
            pIcon = i.getStringExtra("pIcon");
            sellerName = i.getStringExtra("uname");

            //init Ui Views
            brandName = findViewById(R.id.brandName);
            productAddress = findViewById(R.id.productAddress);
            productDescription = findViewById(R.id.productDescription);
            productIv = findViewById(R.id.productIv);


            buyerName = findViewById(R.id.buyerName);
            buyerAddress = findViewById(R.id.buyerAddress);
            buyerPhoneNo = findViewById(R.id.buyerPhoneNo);
            buyerNIC = findViewById(R.id.buyerNIC);

            originalPrice = findViewById(R.id.originalPrice);
            secuityPrice = findViewById(R.id.secuityPrice);
            totalPrice = findViewById(R.id.totalPrice);
            orderBtn = findViewById(R.id.orderBtn);

            //set tv
            brandName.setText(str_brandName);
            productAddress.setText(str_productAddress);
            productDescription.setText(str_productDescription);
            try{
                Picasso.with(getApplicationContext()).load(pIcon).placeholder(R.drawable.ic_baseline_shopping_cart_24).into(productIv);
            }
            catch (Exception e){
                productIv.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
            }

            // convert and calculate total price
            price = Integer.parseInt(pAprice);
            sPrice = Integer.parseInt(pSPrice);
            tPrice = price + sPrice;

            pAprice = pAprice + " pkr";
            pSPrice = pAprice + " pkr";
            originalPrice.setText(pAprice);
            secuityPrice.setText(pSPrice);

            // default total price
            finalPrice = String.valueOf(tPrice);
            finalPrice = finalPrice + "pkr";
            finalPrice = String.valueOf(tPrice);

            totalPrice.setText(finalPrice);

            //setup progress dialog
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("PLEASE WAIT...");
            progressDialog.setCanceledOnTouchOutside(false);

            orderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(validation())
                    {
                        submitOrder();
                    }
                }
            });
        }
    }

    private void submitOrder() {
        progressDialog.setMessage("Placing Order....");
        progressDialog.show();

        //For order id and order time
        String timestamp = ""+System.currentTimeMillis();

        String cost = totalPrice.getText().toString().trim().replace("pkr",""); // remove pkr if contains

        String cat = "Women Products";
        //setup order data
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("orderId",""+timestamp);
        hashMap.put("orderTime",""+timestamp);
        hashMap.put("orderStatus","In Progress");// In Progress/Completed/Cancelled
        hashMap.put("orderCost",""+cost);
        hashMap.put("orderBy",""+username);
        hashMap.put("orderTo",""+sellerName);
        hashMap.put("orderImgUri",""+pIcon);
        hashMap.put("orderBrandName",""+str_brandName);
        hashMap.put("orderPhoneNo",""+phoneNo);
        hashMap.put("orderNIC",""+nic);
        hashMap.put("orderAddress",""+address);
        hashMap.put("cat",""+cat);

        //add to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Credentials").child("SELLER").child(sellerName).child("Orders");
        ref.child("Women Products").child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //order info added now add order items
                        progressDialog.dismiss();
                        TastyToast.makeText(getApplicationContext(),"Order Placed Successfully...",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed placing order
                        progressDialog.dismiss();
                        TastyToast.makeText(getApplicationContext(),""+e.getMessage(),TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
                    }
                });
    }

    private boolean validation() {
        username = buyerName.getText().toString().toLowerCase().trim();
        address = productAddress.getText().toString();
        phoneNo = buyerPhoneNo.getText().toString();
        nic = buyerNIC.getText().toString();

        if(username.isEmpty())
        {
            buyerName.setError("Please fill field");
            buyerName.requestFocus();
            return false;
        }
        if(!username.isEmpty())
        {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Credentials");
            reference.child("BUYER").orderByChild(username).equalTo(username)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                Toast.makeText(getApplicationContext(),"username find", Toast.LENGTH_SHORT).show();
                                check = false;
                            }
                            else{
                                check = true;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            check = true;
                        }
                    });

            if(check)
            {
                buyerName.setError("Invalid Username! Please type valid username");
                buyerName.requestFocus();
                return false;
            }

        }
        if(address.isEmpty())
        {
            productAddress.setError("Please fill field");
            productAddress.requestFocus();
            return false;
        }
        if(phoneNo.isEmpty())
        {
            buyerPhoneNo.setError("Please fill field");
            buyerPhoneNo.requestFocus();
            return false;
        }
        if(nic.isEmpty())
        {
            buyerNIC.setError("Please fill field");
            buyerNIC.requestFocus();
            return false;
        }

        return true;
    }
}