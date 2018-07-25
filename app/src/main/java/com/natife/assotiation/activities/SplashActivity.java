package com.natife.assotiation.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.natife.assotiation.R;

import static com.natife.assotiation.MainActivity.start;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            start(SplashActivity.this);
            finish();
        }, 500);
    }


    @Override
    public void onBackPressed() { }
}
