package com.nzdeveloper009.affordablefunctionoutfit.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.nzdeveloper009.affordablefunctionoutfit.R;

import org.w3c.dom.Text;

public class SplashActivity extends AppCompatActivity {

    TextView app_name;
    ImageView logo, splash_bg;
    LottieAnimationView lottieAnimationView;

    private static int SPLASH_SCREEN = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        app_name = findViewById(R.id.app_name);
        logo = findViewById(R.id.logo);
        splash_bg = findViewById(R.id.splash_bg);
        lottieAnimationView = findViewById(R.id.lottie);

        splash_bg.animate().translationY(-3000).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2700).setDuration(1000).setStartDelay(4000);
        app_name.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(2000).setDuration(1000).setStartDelay(4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), WhoActivity.class));
                finish();
            }
        }, SPLASH_SCREEN);
    }
}