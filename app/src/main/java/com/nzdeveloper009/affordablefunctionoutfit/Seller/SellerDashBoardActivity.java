package com.nzdeveloper009.affordablefunctionoutfit.Seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.nzdeveloper009.affordablefunctionoutfit.Buyer.BFragment.BuyerProfileFragment;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.Fragment.SellerProductCategoriesFragment;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.Fragment.LogOutFragment;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.Fragment.PaymentFragment;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.Fragment.ProfileFragment;

public class    SellerDashBoardActivity extends AppCompatActivity {

    MeowBottomNavigation navigation;

    private final static int PROFILE_ID = 1;
    private final static int ADD_PRODUCT_ID = 2;
    private final static int PAYMENT_ID = 3;
    private final static int LOGOUT_ID = 4;

    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dash_board);

        Intent getIntent = getIntent();
        username = getIntent.getStringExtra("str_username");

        navigation = findViewById(R.id.bottom_navigation);

        navigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_profile));
        navigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_about));
        navigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_payment_3));
        navigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_logout));


        //getSupportFragmentManager().beginTransaction().replace(R.id.root_container,new ProfileFragment()).commit();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment();

        Bundle bundle = new Bundle();
        bundle.putString("str_username",username);
        profileFragment.setArguments(bundle);
        transaction.replace(R.id.root_container,profileFragment);
        transaction.commit();

        navigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //These listener are required here
            }
        });

        navigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //These listener are required here
            }
        });

        navigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Bundle bundle = new Bundle();
                bundle.putString("str_username",username);
                Fragment fragmentSelected = null;
                switch (item.getId())
                {
                    case PROFILE_ID:
                        fragmentSelected = new ProfileFragment();
                        break;
                    case ADD_PRODUCT_ID:
                        fragmentSelected = new SellerProductCategoriesFragment();
                        break;
                    case PAYMENT_ID:
                        fragmentSelected = new PaymentFragment();
                        break;
                    case LOGOUT_ID:
                        fragmentSelected = new LogOutFragment();
                        break;
                }
                fragmentSelected.setArguments(bundle);
                assert fragmentSelected != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.root_container,fragmentSelected).commit();
            }
        });
    }

}