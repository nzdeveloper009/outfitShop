package com.nzdeveloper009.affordablefunctionoutfit.Seller.AddProducts.ProductDetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nzdeveloper009.affordablefunctionoutfit.Common.LoginActivity;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.AddProductHelper;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.AddProducts.AddMenProducts;
import com.nzdeveloper009.affordablefunctionoutfit.util.Helper;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Objects;


public class GetMenProductDetail extends AppCompatActivity {


    // variables
    EditText edt_dress_name, edt_desc, edt_product_color, edt_actual_price, edt_brand_name, edt_security_price;

    Button post;
    // get username from previous activity
    String username;



    private ProgressDialog progressDialog;


    String timestamp = ""+System.currentTimeMillis();

    String productCategory = "Men Products";
    //////////////
    private ImageView imageView;
    private static final int pick = 2;
    private StorageReference storageReference;
    private DatabaseReference reference;
    Uri resultUri;
    String imageRename;

    String newUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_men_product_detail);


        Intent intent = getIntent();
        if (intent != null)
            username = intent.getStringExtra("str_username");



        imageRename = username + "_" + timestamp;




        //init ui views
        edt_dress_name = findViewById(R.id.edt_dress_name);
        edt_desc = findViewById(R.id.edt_desc);
        edt_product_color = findViewById(R.id.edt_product_color);
        edt_actual_price = findViewById(R.id.edt_actual_price);
        edt_security_price = findViewById(R.id.edt_security_price);
        edt_brand_name = findViewById(R.id.edt_brand_name);
        imageView = findViewById(R.id.imageView);
        post = findViewById(R.id.post);

        //setup progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("PLEASE WAIT...");
        progressDialog.setCanceledOnTouchOutside(false);




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show dialog to pick image
                // showImagePickkDialog();
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(gallery,pick);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Flow:
                    // 1) input data
                    // 2) validate data
                    // 3) Add Data to db
                inputData();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pick && resultCode==RESULT_OK && data!=null){
            Uri image = data.getData();
            /*Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);*/

            CropImage.activity(image)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                storageReference = FirebaseStorage.getInstance().getReference().child("Product Images");
                storageReference.child(imageRename).putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                newUri = uri.toString();
                                Picasso.with(getApplicationContext())
                                        .load(newUri)
                                        .into(imageView);
                            }
                        });
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private String productAddress,productDescription,productColor,originalPrice,SecurityPrice,brandName;
    private void inputData() {
        // 1) input data
        productAddress = edt_dress_name.getText().toString().trim();
        productDescription = edt_desc.getText().toString().trim();
        productColor = edt_product_color.getText().toString().trim();
        originalPrice = edt_actual_price.getText().toString().trim();
        SecurityPrice = edt_security_price.getText().toString().trim();
        brandName = edt_brand_name.getText().toString().trim();

        // 2) Validate data
        if(TextUtils.isEmpty(productAddress))
        {
            Toast.makeText(this, "Address is required...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(productDescription))
        {
            Toast.makeText(this, "Description is required...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(productColor))
        {
            Toast.makeText(this, "Product color is required...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(originalPrice))
        {
            Toast.makeText(this, "Actual price is required...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(SecurityPrice))
        {
            Toast.makeText(this, "Security price is required...", Toast.LENGTH_SHORT).show();
            return;
        }

        if(newUri.isEmpty())
        {
            Toast.makeText(this, "Image is required...", Toast.LENGTH_SHORT).show();
            return;
        }

        addProduct();
    }

    private void addProduct() {
        // 3) Add Data to db
        progressDialog.setMessage("Adding Product....");
        progressDialog.show();

        HashMap<String , Object> data = new HashMap<>();
        data.put("productAddress",""+productAddress);
        data.put("productDescription",""+productDescription);
        data.put("productColor",""+productColor);
        data.put("originalPrice",""+originalPrice);
        data.put("SecurityPrice",""+SecurityPrice);
        data.put("brandName",""+brandName);
        data.put("username",""+username);
        data.put("imgUri",""+newUri);

        reference = FirebaseDatabase.getInstance().getReference("Products");
        reference.child(username).child(productCategory).child(timestamp).setValue(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        TastyToast.makeText(getApplicationContext(),"Product Added...",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        TastyToast.makeText(getApplicationContext(),""+e.getMessage(),TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
                    }
                });
        }

    private void clearData() {
        edt_dress_name.setText("");
        edt_desc.setText("");
        edt_product_color.setText("");
        edt_actual_price.setText("");
        edt_security_price.setText("");
        edt_brand_name.setText("");
    }
}