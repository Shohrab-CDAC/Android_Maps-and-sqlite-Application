package com.example.sqlliteapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        // Set the duration of the splash screen in milliseconds
        int splashDuration = 4000; // 4 seconds

        // Use a Handler to delay the navigation after the specified duration
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an Intent to start the MainActivity
                Intent intent = new Intent(splash.this, MainActivity.class);
                startActivity(intent);

                // Finish the current activity to prevent going back to the splash screen
                finish();
            }
        }, splashDuration);
    }
}
