package com.nzdeveloper009.affordablefunctionoutfit.Buyer.BFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nzdeveloper009.affordablefunctionoutfit.Buyer.RentProducts.RentMenProducts;
import com.nzdeveloper009.affordablefunctionoutfit.Buyer.RentProducts.RentSpecialProducts;
import com.nzdeveloper009.affordablefunctionoutfit.Buyer.RentProducts.RentWomenProducts;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.sdsmdg.tastytoast.TastyToast;

public class BuyerProductCategoriesFragment extends Fragment {

    RelativeLayout menCard,womenCard,specialCard;
    String username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buyer_product_categories, container, false);

        Bundle bundle = getArguments();
        if(bundle!=null)
            username = bundle.getString("str_username");

        menCard = view.findViewById(R.id.menCard);
        womenCard = view.findViewById(R.id.womenCard);
        specialCard = view.findViewById(R.id.specialCard);

        menCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RentMenProducts.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        womenCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RentWomenProducts.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        specialCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RentSpecialProducts.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        return  view;
    }
}