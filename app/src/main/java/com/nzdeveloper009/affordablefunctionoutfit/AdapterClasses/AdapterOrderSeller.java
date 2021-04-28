package com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nzdeveloper009.affordablefunctionoutfit.FilterOrderShop;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.ModelOrderBuyer;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.OrderTab.OrderDetailsSeller;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class AdapterOrderSeller extends RecyclerView.Adapter<AdapterOrderSeller.HolderOrderSeller> implements Filterable {

    private Context context;
    public ArrayList<ModelOrderBuyer> orderBuyerList,filterList;

    private FilterOrderShop filter;


    public AdapterOrderSeller(Context context, ArrayList<ModelOrderBuyer> orderBuyerList) {
        this.context = context;
        this.orderBuyerList = orderBuyerList;
        this.filterList = orderBuyerList;
    }

    @NonNull
    @Override
    public HolderOrderSeller onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_order_seller, parent, false);

        return new HolderOrderSeller(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderSeller holder, int position) {
        //get data at position
        ModelOrderBuyer modelOrderBuyer = orderBuyerList.get(position);
        final String orderId = modelOrderBuyer.getOrderId();
        final String orderBy = modelOrderBuyer.getOrderBy();
        final String orderCost = modelOrderBuyer.getOrderCost();
        final String orderStatus = modelOrderBuyer.getOrderStatus();
        final String orderTime = modelOrderBuyer.getOrderTime();
        final String orderTo = modelOrderBuyer.getOrderTo();
        final String orderImgUri = modelOrderBuyer.getOrderImgUri();
        final String orderPhoneNo = modelOrderBuyer.getOrderPhoneNo();
        final String brandName = modelOrderBuyer.getOrderBrandName();
        final String orderAddress = modelOrderBuyer.getOrderAddress();
        final String orderNic = modelOrderBuyer.getOrderNIC();
        final String cat = modelOrderBuyer.getCat();

        loadUserInfo(modelOrderBuyer,holder);

        //set Data
        String cost = "Amount: " + orderCost + " pkr";
        try {
            Picasso.with(context).load(orderImgUri).placeholder(R.drawable.ic_baseline_shopping_cart_24).into(holder.productIv);
        } catch (Exception e) {
            holder.productIv.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
        }
        holder.amountTv.setText(cost);
        holder.statusTv.setText(orderStatus);
        String oID = "OrderID: " + orderId;
        holder.orderIdTv.setText(oID);
        //change order status text color
        switch (orderStatus) {
            case "In Progress":
                holder.statusTv.setTextColor(context.getResources().getColor(R.color.black));
                break;
            case "Completed":
                holder.statusTv.setTextColor(context.getResources().getColor(R.color.yellow));
                break;
            case "Cancelled":
                holder.statusTv.setTextColor(context.getResources().getColor(R.color.red));
                break;
        }

        //convert timestamp to proper format
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(orderTime));
        String formatedDate = DateFormat.format("dd/MM/yyy", calendar).toString(); //e.g. 16/06/2021

        holder.dateTv.setText(formatedDate);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open order details
                Intent intent = new Intent(context, OrderDetailsSeller.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("orderId",orderId); // to load order info
                intent.putExtra("orderBy",orderBy); // to load info of the use who place order
                intent.putExtra("orderTo",orderTo);
                intent.putExtra("orderCost",orderCost);
                intent.putExtra("orderTime",orderTime);
                intent.putExtra("orderStatus",orderStatus);
                intent.putExtra("orderImgUri",orderImgUri);
                intent.putExtra("orderPhoneNo",orderPhoneNo);
                intent.putExtra("brandName",brandName);
                intent.putExtra("orderAddress",orderAddress);
                intent.putExtra("orderNic",orderNic);
                intent.putExtra("cat",cat);
                context.startActivity(intent);

            }
        });
    }

    private void loadUserInfo(ModelOrderBuyer modelOrderBuyer, HolderOrderSeller holder) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Credentials");
        ref.child("BUYER").child(modelOrderBuyer.getOrderBy())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            String buyerName = ""+snapshot.child("username").getValue();
                            holder.buyerNameTv.setText(buyerName);
                        }
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

    @Override
    public Filter getFilter() {
        if(filter == null)
        {
            //init filter
            filter = new FilterOrderShop(this,filterList);
        }
        return filter;
    }

    class HolderOrderSeller extends RecyclerView.ViewHolder {
        //views of layout
        private TextView orderIdTv, dateTv, buyerNameTv, amountTv, statusTv;
        private ImageView productIv;

        public HolderOrderSeller(@NonNull View itemView) {
            super(itemView);

            //init views of layout
            orderIdTv = itemView.findViewById(R.id.orderIdTv);
            dateTv = itemView.findViewById(R.id.dateTv);
            buyerNameTv = itemView.findViewById(R.id.buyerNameTv);
            amountTv = itemView.findViewById(R.id.amountTv);
            statusTv = itemView.findViewById(R.id.statusTv);
            productIv = itemView.findViewById(R.id.productIv);
        }
    }
}
