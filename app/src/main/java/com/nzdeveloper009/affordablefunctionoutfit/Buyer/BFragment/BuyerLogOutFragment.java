package com.nzdeveloper009.affordablefunctionoutfit.Buyer.BFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nzdeveloper009.affordablefunctionoutfit.Common.LoginActivity;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.sdsmdg.tastytoast.TastyToast;

public class BuyerLogOutFragment extends Fragment {

    Button mLogOutBtn;
    String logUserType="BUYER";
    SharedPreferences preferences;

    String username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buyer_log_out, container, false);

        Bundle bundle = getArguments();
        if(bundle!=null)
            username = bundle.getString("str_username");


        mLogOutBtn = view.findViewById(R.id.logout);

        preferences = getActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE);

        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                BuyerLogOutFragment.this.startActivity(intent);
                getActivity().finish();
            }
        });

        return  view;
    }
}