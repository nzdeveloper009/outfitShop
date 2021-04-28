package com.nzdeveloper009.affordablefunctionoutfit.Buyer.RentProducts.Shops;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses.AdapterMenProducts;
import com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses.AdapterSpecialProducts;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.ModelProduct;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class BuyerSpecialShopDetails extends AppCompatActivity {

    //declare ui views
    private ImageView shopIv,callBtn;
    private TextView shopNameTv,phoneTv,emailTv;
    private ImageButton filterProductBtn;
    private EditText searchProductEt;
    private RecyclerView productsRv;
    private ArrayList<ModelProduct> productList;
    private AdapterSpecialProducts adapterSpecialProducts;

    String sellerUsername,sellerEmail,sellerPhone;
    String searchProduct;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_speical_shop_details);

        Intent i = getIntent();
        if(i != null)
        {
            sellerEmail = i.getStringExtra("email");
            sellerUsername = i.getStringExtra("name");
            sellerPhone = i.getStringExtra("phone");

            //init ui views
            shopIv = findViewById(R.id.shopIv);
            shopNameTv = findViewById(R.id.shopNameTv);
            phoneTv = findViewById(R.id.phoneTv);
            emailTv = findViewById(R.id.emailTv);
            productsRv = findViewById(R.id.productsRv);
            callBtn = findViewById(R.id.callBtn);

            filterProductBtn = findViewById(R.id.filterProductBtn);
            searchProductEt = findViewById(R.id.searchProductEt);

            shopNameTv.setText(sellerUsername);
            phoneTv.setText(sellerPhone);
            emailTv.setText(sellerEmail);


            callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference = FirebaseDatabase.getInstance().getReference("Credentials");
                    reference.child("SELLER").child(sellerUsername).addListenerForSingleValueEvent(listener);
                }
            });

            loadShopProducts();

            filterProductBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchProduct = searchProductEt.getText().toString().toLowerCase();
                    if(searchProduct.equals("all"))
                    {
                        loadShopProducts();
                    }
                    else{
                        loadFilteredProducts(searchProduct);
                    }
                }
            });
        }
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists())
            {
                String db_phoneNo = snapshot.child("phoneNo").getValue(String.class);
                String sellerPhoneNoUri = "tel:" + db_phoneNo;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(sellerPhoneNoUri));
                startActivity(intent);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            TastyToast.makeText(getApplicationContext(),""+error.getMessage(),TastyToast.LENGTH_SHORT,TastyToast.ERROR);
        }
    };

    private void loadFilteredProducts(String searchProduct) {
        reference = FirebaseDatabase.getInstance().getReference("Products");
        productList = new ArrayList<>();
        reference.child(sellerUsername).child("Men Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset list
                        productList.clear();

                        for(DataSnapshot ds: snapshot.getChildren())
                        {

                            String productColor = ""+ds.child("productColor").getValue(String.class);
                            //if color matches product color then add in list
                            if(searchProduct.equals(productColor))
                            {
                                ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                                productList.add(modelProduct);
                            }

                        }
                        //setup adapter
                        adapterSpecialProducts = new AdapterSpecialProducts(BuyerSpecialShopDetails.this,productList);
                        //set adapter
                        productsRv.setAdapter(adapterSpecialProducts);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        TastyToast.makeText(getApplicationContext(),""+error.getMessage(),TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }
                });
    }

    private void loadShopProducts() {
        reference = FirebaseDatabase.getInstance().getReference("Products");
        productList = new ArrayList<>();
        reference.child(sellerUsername).child("Special Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset list
                        for(DataSnapshot ds: snapshot.getChildren())
                        {
                            ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                            productList.add(modelProduct);
                        }
                        //setup adapter
                        adapterSpecialProducts = new AdapterSpecialProducts(BuyerSpecialShopDetails.this,productList);
                        //set adapter
                        productsRv.setAdapter(adapterSpecialProducts);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        TastyToast.makeText(getApplicationContext(),""+error.getMessage(),TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }
                });
    }
}