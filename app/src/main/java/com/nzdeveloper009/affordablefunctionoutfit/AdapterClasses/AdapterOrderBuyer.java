package com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.ModelOrderBuyer;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;


public class AdapterOrderBuyer extends RecyclerView.Adapter<AdapterOrderBuyer.HolderOrderBuyer> {

    private Context context;
    private ArrayList<ModelOrderBuyer> orderBuyerList;

    public AdapterOrderBuyer(Context context, ArrayList<ModelOrderBuyer> orderBuyerList) {
        this.context = context;
        this.orderBuyerList = orderBuyerList;
    }

    @NonNull
    @Override
    public HolderOrderBuyer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_order_buyer,parent,false);

        return new HolderOrderBuyer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderBuyer holder, int position) {
        //get data
        ModelOrderBuyer modelOrderBuyer = orderBuyerList.get(position);
        final String orderId = modelOrderBuyer.getOrderId();
        final String orderBy = modelOrderBuyer.getOrderBy();
        final String orderCost = modelOrderBuyer.getOrderCost();
        final String orderStatus = modelOrderBuyer.getOrderStatus();
        final String orderTime = modelOrderBuyer.getOrderTime();
        final String orderTo = modelOrderBuyer.getOrderTo();
        final String orderImgUri = modelOrderBuyer.getOrderImgUri();

        //get shop info
        loadShopInfo(modelOrderBuyer,holder);

        String cost = "Amount: " + orderCost + " pkr";
        //set Data

        try{
            Picasso.with(context).load(orderImgUri).placeholder(R.drawable.ic_baseline_shopping_cart_24).into(holder.productIv);
        }
        catch (Exception e){
            holder.productIv.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
        }
        holder.amountTv.setText(cost);
        holder.statusTv.setText(orderStatus);
        String oID = "OrderID: " + orderId;
        holder.orderIdTv.setText(oID);
        //change order status text color
        if (orderStatus.equals("In Progress")) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.black));
        } else if (orderStatus.equals("Completed")) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.green));
        } else if (orderStatus.equals("Cancelled")) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.red));
        }

        //convert timestamp to proper format
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(orderTime));
        String formatedDate = DateFormat.format("dd/MM/yyy",calendar).toString(); //e.g. 16/06/2021

        holder.dateTv.setText(formatedDate);
    }

    private void loadShopInfo(ModelOrderBuyer modelOrderBuyer, HolderOrderBuyer holder) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Credentials");
        ref.child("SELLER").child(modelOrderBuyer.getOrderTo())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String shopName = ""+snapshot.child("username").getValue();
                        holder.shopNameTv.setText(shopName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return orderBuyerList.size();
    }

    //view Holder class
    class HolderOrderBuyer extends RecyclerView.ViewHolder{

        //views of layout
        private TextView orderIdTv,dateTv,shopNameTv,amountTv,statusTv;
        private ImageView productIv;
        public HolderOrderBuyer(@NonNull View itemView) {
            super(itemView);

            //init views of layout
            orderIdTv = itemView.findViewById(R.id.orderIdTv);
            dateTv = itemView.findViewById(R.id.dateTv);
            shopNameTv = itemView.findViewById(R.id.shopNameTv);
            amountTv = itemView.findViewById(R.id.amountTv);
            statusTv = itemView.findViewById(R.id.statusTv);
            productIv = itemView.findViewById(R.id.productIv);
        }
    }
}
