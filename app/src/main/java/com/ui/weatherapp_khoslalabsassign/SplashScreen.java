package com.ui.weatherapp_khoslalabsassign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    ImageView loading;
    RotateAnimation rotate;
    TextView loadingtext;

    Handler handler,handler2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        loading=(ImageView)findViewById(R.id.loading);
         loadingtext = (TextView)findViewById(R.id.loadingtext);
        loading.setVisibility(View.INVISIBLE);
        loadingtext.setVisibility(View.INVISIBLE);
        handler = new Handler();
        handler2= new Handler();

       LoadingAnim();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, MainActivity.class);

                startActivity(i);

                // close this activity

                finish();
            }
        },4000);
    }

    public  void LoadingAnim()
    {
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.VISIBLE);
                loadingtext.setVisibility(View.VISIBLE);
                rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(3000);
                rotate.setInterpolator(new LinearInterpolator());

                loading.startAnimation(rotate);
            }
        },1000);
    }
}
