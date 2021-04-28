package com.nzdeveloper009.affordablefunctionoutfit.Buyer.BFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nzdeveloper009.affordablefunctionoutfit.Buyer.BuyerProfileActivity;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.ProfileActivity;
import com.sdsmdg.tastytoast.TastyToast;

public class BuyerProfileFragment extends Fragment {

    RelativeLayout buyerProfileView;
    String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buyer_profile, container, false);

        Bundle bundle = getArguments();
        if(bundle!=null)
            username = bundle.getString("str_username");


        buyerProfileView = view.findViewById(R.id.relProfileView);

        buyerProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BuyerProfileActivity.class);
                intent.putExtra("str_username",username);
                startActivity(intent);
            }
        });

        return view;
    }
}