package com.nzdeveloper009.affordablefunctionoutfit.Seller.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.sdsmdg.tastytoast.TastyToast;

public class PaymentFragment extends Fragment {

    String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        Bundle bundle = getArguments();
        if(bundle!=null)
            username = bundle.getString("str_username");


        return view;
    }
}