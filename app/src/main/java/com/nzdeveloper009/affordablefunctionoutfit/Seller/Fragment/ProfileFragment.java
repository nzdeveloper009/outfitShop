package com.nzdeveloper009.affordablefunctionoutfit.Seller.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.OrderTab.SellerOrderCategories;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.ProfileActivity;
import com.sdsmdg.tastytoast.TastyToast;

public class ProfileFragment extends Fragment {

    private RelativeLayout relProfileView;
    String username;
    CardView orderCv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle bundle = getArguments();
        if(bundle!=null)
            username = bundle.getString("str_username");


        relProfileView = view.findViewById(R.id.relProfileView);

        relProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("str_username",username);
                startActivity(intent);
            }
        });

        orderCv = view.findViewById(R.id.orderCv);
        orderCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SellerOrderCategories.class);
                intent.putExtra("str_username",username);
                startActivity(intent);
            }
        });

        return view;
    }
}