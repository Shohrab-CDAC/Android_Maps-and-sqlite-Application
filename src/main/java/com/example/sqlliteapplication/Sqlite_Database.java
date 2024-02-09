// SqliteDatabaseActivity.java
package com.example.sqlliteapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Sqlite_Database extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SQLiteDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_database);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SQLite Database Live Entries");

        recyclerView = findViewById(R.id.recyclerViewSqliteData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter
        adapter = new SQLiteDataAdapter();

        // Set adapter to RecyclerView
        recyclerView.setAdapter(adapter);

        // Load SQLite data
        loadSQLiteData();
    }

    private void loadSQLiteData() {
        // Initialize DatabaseHelper
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Get readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define columns to retrieve
        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_EMAIL,
                DatabaseHelper.COLUMN_LOCATION,
                DatabaseHelper.COLUMN_PASSWORD
        };

        // Query the database
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,  // The table to query
                projection,                   // The columns to return
                null,                         // The columns for the WHERE clause
                null,                         // The values for the WHERE clause
                null,                         // Don't group the rows
                null,                         // Don't filter by row groups
                null                          // The sort order
        );

        // Process the cursor and populate data in the adapter
        List<UserModel> userList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
                String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));
                String userLocation = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOCATION));
                String userPassword = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PASSWORD));

                // Create UserModel and add to the list
                UserModel user = new UserModel();
                user.setId(userId);
                user.setName(userName);
                user.setEmail(userEmail);
                user.setLocation(userLocation);
                user.setPassword(userPassword);

                userList.add(user);
            }
            cursor.close();
        }

        // Close the database
        db.close();

        // Update the adapter with the data
        adapter.setUserList(userList);
    }
}
