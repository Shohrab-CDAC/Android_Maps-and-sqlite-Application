package com.example.sqlliteapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP_Registration extends AppCompatActivity {

    private EditText editTextPhoneNumber;
    private EditText editTextOTP;
    private Button btnSendOTP;
    private Button btnVerifyOTP;
    private ProgressBar progressBarResend;

    // Timer for OTP auto-fill
    private CountDownTimer countDownTimer;
    private final long timerDuration = 10000; // 10 seconds
    private final long timerInterval = 1000; // 1 second

    // Firebase Authentication
    private FirebaseAuth mAuth;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_registration);

        mAuth = FirebaseAuth.getInstance();

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextOTP = findViewById(R.id.editTextOTP);
        btnSendOTP = findViewById(R.id.btnSendOTP);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);
        progressBarResend = findViewById(R.id.progressBarResend);


        
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to send OTP
                String phoneNumber = editTextPhoneNumber.getText().toString().trim();

                if (!TextUtils.isEmpty(phoneNumber)) {
                    sendOTP(phoneNumber);
                } else {
                    // Phone number not entered, display toast message
                    Toast.makeText(OTP_Registration.this, "Please enter your phone number with the country code", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to verify OTP
                String enteredOTP = editTextOTP.getText().toString().trim();

                if (!TextUtils.isEmpty(enteredOTP) && enteredOTP.length() == 6) {
                    // OTP verification successful
                    verifyOTP(enteredOTP);
                } else {
                    // Invalid OTP
                    Toast.makeText(OTP_Registration.this, "Invalid OTP. Please enter a 6-digit OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendOTP(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                10,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        // Automatically filled by Firebase on successful verification
                        String autoFilledCode = phoneAuthCredential.getSmsCode();
                        if (autoFilledCode != null && autoFilledCode.length() == 6) {
                            editTextOTP.setText(autoFilledCode);
                            hideProgressBarResend();
                        }
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // Handle verification failure
                        Toast.makeText(OTP_Registration.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        hideProgressBarResend();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        // Code has been sent, save the verification ID to use later
                        verificationId = s;
                        showProgressBarResend();
                    }
                }
        );
    }

    private void verifyOTP(String enteredOTP) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, enteredOTP);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        Toast.makeText(OTP_Registration.this, "OTP Verified Successfully", Toast.LENGTH_SHORT).show();

                        // Proceed to any desired logic (e.g., navigate to home activity)
                        Intent intent = new Intent(OTP_Registration.this, Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(OTP_Registration.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void simulateOTPSend() {
        // Simulate OTP sending process
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // Auto-fill received OTP after 10 seconds
                        editTextOTP.setText("123456");
                        hideProgressBarResend();
                    }
                },
                timerDuration
        );
    }

    private void showProgressBarResend() {
        progressBarResend.setVisibility(View.VISIBLE);

        // Start a timer to hide the progress bar after 10 seconds
        countDownTimer = new CountDownTimer(timerDuration, timerInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Do nothing while waiting for the timer to finish
            }

            @Override
            public void onFinish() {
                hideProgressBarResend();
            }
        }.start();
    }

    private void hideProgressBarResend() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        progressBarResend.setVisibility(View.INVISIBLE);
    }
}
