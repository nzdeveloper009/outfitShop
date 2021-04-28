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

import com.nzdeveloper009.affordablefunctionoutfit.Admin.BuyerData.exploreBuyer;
import com.nzdeveloper009.affordablefunctionoutfit.Admin.SellerData.exploreSeller;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.UserHelperClass;
import com.nzdeveloper009.affordablefunctionoutfit.R;

import java.util.ArrayList;

public class AdapterBuyerDetails extends RecyclerView.Adapter<AdapterBuyerDetails.HolderBuyerDetails> {

    private Context context;
    public ArrayList<UserHelperClass> userHelperClassList;

    public AdapterBuyerDetails(Context context, ArrayList<UserHelperClass> userHelperClassList) {
        this.context = context;
        this.userHelperClassList = userHelperClassList;
    }

    @NonNull
    @Override
    public HolderBuyerDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_shop.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_seller_details,parent,false);


        return new HolderBuyerDetails(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderBuyerDetails holder, int position) {

        UserHelperClass userHelperClass = userHelperClassList.get(position);
        final String fulName = userHelperClass.getFullName();
        final String uname = userHelperClass.getUsername();
        final String phoneNo = userHelperClass.getPhoneNo();
        final String email = userHelperClass.getEmail();
        final String nicNo = userHelperClass.getNicNo();

        //set data
        holder.username.setText(uname);
        holder.email.setText(email);
        holder.phone.setText(phoneNo);

        //handle click listener, show shop details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, exploreBuyer.class);
                intent.putExtra("fulName",fulName);
                intent.putExtra("uname",uname);
                intent.putExtra("phoneNo",phoneNo);
                intent.putExtra("email",email);
                intent.putExtra("nicNo",nicNo);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userHelperClassList.size();
    }


    class HolderBuyerDetails extends RecyclerView.ViewHolder
    {
        private TextView username,email,phone;

        public HolderBuyerDetails(@NonNull View itemView) {
            super(itemView);

            //init ui views
            username = itemView.findViewById(R.id.seller_username);
            email = itemView.findViewById(R.id.seller_email);
            phone = itemView.findViewById(R.id.seller_phoneNo);
        }
    }
}
