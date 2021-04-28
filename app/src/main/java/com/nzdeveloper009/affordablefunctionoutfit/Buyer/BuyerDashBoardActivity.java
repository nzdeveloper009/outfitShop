package com.nzdeveloper009.affordablefunctionoutfit.Buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.nzdeveloper009.affordablefunctionoutfit.Buyer.BFragment.BuyerLogOutFragment;
import com.nzdeveloper009.affordablefunctionoutfit.Buyer.BFragment.BuyerOrderStatusFragment;
import com.nzdeveloper009.affordablefunctionoutfit.Buyer.BFragment.BuyerProductCategoriesFragment;
import com.nzdeveloper009.affordablefunctionoutfit.Buyer.BFragment.BuyerProfileFragment;
import com.nzdeveloper009.affordablefunctionoutfit.R;
import com.nzdeveloper009.affordablefunctionoutfit.Seller.Fragment.ProfileFragment;

public class BuyerDashBoardActivity extends AppCompatActivity {


    MeowBottomNavigation btm_navigation;

    public String username;

    private final static int PROFILE_ID = 1;
    private final static int BUYER_PRODUCT_CATEGORIES_ID = 2;
    private final static int STATUS_ID = 3;
    private final static int LOGOUT_ID = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_dash_board);

        Intent getIntent = getIntent();
        username = getIntent.getStringExtra("str_username");


        btm_navigation = findViewById(R.id.botm_navigation);

        btm_navigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_profile));
        btm_navigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_about));
        btm_navigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_payment_3));
        btm_navigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_logout));



        //getSupportFragmentManager().beginTransaction().replace(R.id.buyer_root_container,new BuyerProfileFragment()).commit();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        BuyerProfileFragment buyerProfileFragment = new BuyerProfileFragment();

        Bundle bundle = new Bundle();
        bundle.putString("str_username",username);
        buyerProfileFragment.setArguments(bundle);
        transaction.replace(R.id.buyer_root_container,buyerProfileFragment);
        transaction.commit();

        btm_navigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //These listener are required here
            }
        });

        btm_navigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //These listener are required here
            }
        });

        btm_navigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {

            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Bundle bundle = new Bundle();
                bundle.putString("str_username",username);
                Fragment fragmentSelected = null;
                switch (item.getId())
                {
                    case PROFILE_ID:
                        fragmentSelected = new BuyerProfileFragment();
                        fragmentSelected.setArguments(bundle);
                        break;
                    case BUYER_PRODUCT_CATEGORIES_ID:
                        fragmentSelected = new BuyerProductCategoriesFragment();
                        fragmentSelected.setArguments(bundle);
                        break;
                    case STATUS_ID:
                        fragmentSelected = new BuyerOrderStatusFragment();
                        fragmentSelected.setArguments(bundle);
                        break;
                    case LOGOUT_ID:
                        fragmentSelected = new BuyerLogOutFragment();
                        fragmentSelected.setArguments(bundle);
                        break;
                }
                fragmentSelected.setArguments(bundle);
                assert fragmentSelected != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.buyer_root_container,fragmentSelected).commit();
            }
        });
    }
}