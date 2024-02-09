package com.example.sqlliteapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    private Button btnGoogleMaps;
    private Button btnSQLiteDatabase;
    private Button btnFirebaseData; // Added button for Firebase Data
    private ImageView logoutIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnGoogleMaps = findViewById(R.id.btnGoogleMaps);
        btnSQLiteDatabase = findViewById(R.id.btnSQLiteDatabase);
        logoutIcon = findViewById(R.id.logoutIcon);

        btnGoogleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Google Maps Activity
                Intent intent = new Intent(Home.this, google_maps.class);
                startActivity(intent);
            }
        });

        btnSQLiteDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start SQLiteDatabase Activity
                Intent intent = new Intent(Home.this, Sqlite_Database.class);
                startActivity(intent);
            }
        });


        // Set OnClickListener for logout icon
        logoutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement your logout logic here
                logout();
            }
        });
    }

    private void logout() {
        // Add your logout logic here
        // For example, you can navigate to the login activity
        Intent intent = new Intent(Home.this, MainActivity.class);
        startActivity(intent);
        finish(); // Optionally finish the current activity
    }
}
