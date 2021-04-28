package com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nzdeveloper009.affordablefunctionoutfit.Buyer.RentProducts.Shops.BuyerOrderSpecialProducts;
import com.nzdeveloper009.affordablefunctionoutfit.Buyer.RentProducts.Shops.BuyerOrderWomenProducts;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.ModelProduct;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterWomenProducts extends RecyclerView.Adapter<AdapterWomenProducts.HolderWomenProduct> {


    private Context context;
    public ArrayList<ModelProduct> productList;

    public AdapterWomenProducts(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public HolderWomenProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_buyer,parent,false);

        return new HolderWomenProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderWomenProduct holder, int position) {
//get data
        ModelProduct modelProduct = productList.get(position);
        final String pBrand = modelProduct.getBrandName();
        final String pColor = modelProduct.getProductColor();
        final String pSPrice = modelProduct.getSecurityPrice();
        final String pAPrice = modelProduct.getOriginalPrice();
        final String pAddress = modelProduct.getProductAddress();
        final String pIcon = modelProduct.getImgUri();
        final String uname = modelProduct.getUsername();
        final String pDesc = modelProduct.getProductDescription();

        //set data
        holder.productBrand.setText(pBrand);
        holder.actualPrice.setText(pAPrice);
        holder.securityPrice.setText(pSPrice);
        holder.productAddress.setText(pAddress);
        try{
            Picasso.with(context).load(pIcon).placeholder(R.drawable.ic_add_product).into(holder.productIcon);
        }
        catch (Exception e){
            holder.productIcon.setImageResource(R.drawable.ic_add_product);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle item clicks, show item details.
                Intent intent = new Intent(context, BuyerOrderWomenProducts.class);
                intent.putExtra("pBrand",pBrand);
                intent.putExtra("pIcon",pIcon);
                intent.putExtra("pAPrice",pAPrice);
                intent.putExtra("pSPrice",pSPrice);
                intent.putExtra("pAddress",pAddress);
                intent.putExtra("uname",uname);
                intent.putExtra("pColor",pColor);
                intent.putExtra("pDesc",pDesc);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HolderWomenProduct extends RecyclerView.ViewHolder
    {
        /*holds views of recyclerview*/
        private ImageView productIcon;
        private TextView productBrand,actualPrice,securityPrice,productAddress;
        public HolderWomenProduct(@NonNull View itemView) {
            super(itemView);

            productIcon = itemView.findViewById(R.id.productIcon);
            productBrand = itemView.findViewById(R.id.productBrand);
            productAddress = itemView.findViewById(R.id.productAddress);
            actualPrice = itemView.findViewById(R.id.actualPrice);
            securityPrice = itemView.findViewById(R.id.securityPrice);

        }
    }
}
