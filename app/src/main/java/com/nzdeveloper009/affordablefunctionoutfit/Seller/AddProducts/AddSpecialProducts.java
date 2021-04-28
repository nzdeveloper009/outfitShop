package com.nzdeveloper009.affordablefunctionoutfit.Seller.AddProducts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses.AdapterProductSeller;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.ModelProduct;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.AddProducts.ProductDetails.GetSpecialProductDetail;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class AddSpecialProducts extends AppCompatActivity {

    Button addProducts;

    private ImageButton filterProductBtn;
    private EditText searchProductEt;
    private RecyclerView productsRv;
    private ArrayList<ModelProduct> productList;
    private AdapterProductSeller adapterProductSeller;

    String sellerUsername;
    String searchProduct;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_special_products);

        Intent intent = getIntent();
        if(intent!=null)
        {
            sellerUsername = intent.getStringExtra("str_username");
            addProducts = findViewById(R.id.addProducts);

            //init ui views
            productsRv = findViewById(R.id.productsRv);
            filterProductBtn = findViewById(R.id.filterProductBtn);
            searchProductEt = findViewById(R.id.searchProductEt);

            addProducts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), GetSpecialProductDetail.class);
                    intent.putExtra("str_username",sellerUsername);
                    startActivity(intent);
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

    private void loadFilteredProducts(String searchProduct) {
        reference = FirebaseDatabase.getInstance().getReference("Products");
        productList = new ArrayList<>();
        reference.child(sellerUsername).child("Special Products")
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
                        adapterProductSeller = new AdapterProductSeller(AddSpecialProducts.this,productList);
                        //set adapter
                        productsRv.setAdapter(adapterProductSeller);
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
                        productList.clear();
                        //before getting reset list
                        for(DataSnapshot ds: snapshot.getChildren())
                        {
                            ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                            productList.add(modelProduct);
                        }
                        //setup adapter
                        adapterProductSeller = new AdapterProductSeller(AddSpecialProducts.this,productList);
                        //set adapter
                        productsRv.setAdapter(adapterProductSeller);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        TastyToast.makeText(getApplicationContext(),""+error.getMessage(),TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }
                });
    }
}