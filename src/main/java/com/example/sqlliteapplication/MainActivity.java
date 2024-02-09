package com.example.sqlliteapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_reg);
        editTextEmail = findViewById(R.id.email_login);
        editTextPassword = findViewById(R.id.login_password);

        // Set PasswordTransformationMethod to hide the password
        editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate the login
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (isValidLogin(email, password)) {
                    // Successful login, start the HomeActivity
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);

                    // Show welcome message
                    Toast.makeText(MainActivity.this, "Welcome to ALBRANDZ TECHNOLOGY", Toast.LENGTH_SHORT).show();
                } else {
                    // Invalid credentials, show a toast
                    Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidLogin(String email, String password) {
        // Query the database to check if the email and password match
        String[] projection = {DatabaseHelper.COLUMN_EMAIL, DatabaseHelper.COLUMN_PASSWORD};
        String selection = DatabaseHelper.COLUMN_EMAIL + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isValid = cursor.getCount() > 0;

        cursor.close();

        return isValid;
    }
}
