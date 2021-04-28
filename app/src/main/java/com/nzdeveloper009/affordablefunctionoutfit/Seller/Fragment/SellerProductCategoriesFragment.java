
package com.nzdeveloper009.affordablefunctionoutfit.Seller.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.AddProducts.AddSpecialProducts;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.AddProducts.AddMenProducts;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.AddProducts.AddWomenProducts;
import com.sdsmdg.tastytoast.TastyToast;

public class SellerProductCategoriesFragment extends Fragment {

    RelativeLayout menCard,womenCard,specialCard;
    String username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seller_product_categories, container, false);

        Bundle bundle = getArguments();
        if(bundle!=null)
            username = bundle.getString("str_username");


        menCard = view.findViewById(R.id.menCard);
        womenCard = view.findViewById(R.id.womenCard);
        specialCard = view.findViewById(R.id.specialCard);

        menCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddMenProducts.class);
                intent.putExtra("str_username",username);
                startActivity(intent);
            }
        });
        womenCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddWomenProducts.class);
                intent.putExtra("str_username",username);
                startActivity(intent);
            }
        });
        specialCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddSpecialProducts.class);
                intent.putExtra("str_username",username);
                startActivity(intent);
            }
        });

        return view;
    }
}