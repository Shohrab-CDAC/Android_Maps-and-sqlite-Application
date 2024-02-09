package com.example.sqlliteapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity {

    private EditText editTextName, editTextOfficialEmail, editTextPassword, editTextReEnterPassword, editTextNewField;
    private Button btnSignUpRecruiter, btnRegisterUsingOTP;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextName = findViewById(R.id.editTextName);
        editTextOfficialEmail = findViewById(R.id.editTextOfficialEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextReEnterPassword = findViewById(R.id.editTextCompanyName);
        editTextNewField = findViewById(R.id.editTextNewField);
        btnSignUpRecruiter = findViewById(R.id.btnSignUpRecruiter);
        btnRegisterUsingOTP = findViewById(R.id.btnRegisterUsingOTP);

        // Set input type for password fields to make them hidden
        editTextPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editTextReEnterPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);

        databaseHelper = new DatabaseHelper(this);

        btnSignUpRecruiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        btnRegisterUsingOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToOTPRegistration();
            }
        });
    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextOfficialEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String reEnterPassword = editTextReEnterPassword.getText().toString().trim();
        String location = editTextNewField.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(reEnterPassword) || TextUtils.isEmpty(location)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.equals(password, reEnterPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the email is already registered
        if (isEmailRegistered(email)) {
            Toast.makeText(this, "Email address already registered", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save user details to SQLite database
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);
        values.put(DatabaseHelper.COLUMN_LOCATION, location);

        long newRowId = db.insert(DatabaseHelper.TABLE_USERS, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();

            // Show additional welcome message
            Toast.makeText(this, "Welcome to ALBRANDZ TECHNOLOGY", Toast.LENGTH_SHORT).show();

            // Redirect to HomeActivity
            Intent intent = new Intent(Registration.this, Home.class);
            startActivity(intent);
            finish(); // Optional: Finish the current activity to prevent going back to Registration
        } else {
            Toast.makeText(this, "Registration Failed. Please try again.", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private void navigateToOTPRegistration() {
        // Redirect to OTP_Registration Activity
        Intent intent = new Intent(Registration.this, OTP_Registration.class);
        startActivity(intent);
    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean isEmailRegistered(String email) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {DatabaseHelper.COLUMN_EMAIL};
        String selection = DatabaseHelper.COLUMN_EMAIL + "=?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isRegistered = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isRegistered;
    }
}
