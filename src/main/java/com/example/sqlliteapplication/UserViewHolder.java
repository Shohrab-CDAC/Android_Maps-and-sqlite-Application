package com.example.sqlliteapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private final TextView userNameTextView;
    private final TextView userEmailTextView;
    private final TextView userPasswordTextView;
    private final TextView userPhoneNumberTextView;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        userNameTextView = itemView.findViewById(R.id.textViewUserName);
        userEmailTextView = itemView.findViewById(R.id.textViewUserEmail);
        userPasswordTextView = itemView.findViewById(R.id.textViewUserPassword);
        userPhoneNumberTextView = itemView.findViewById(R.id.textViewUserPhoneNumber);
    }

    public void bind(UserModel userModel) {
        userNameTextView.setText("Name: " + userModel.getName());
        userEmailTextView.setText("Email: " + userModel.getEmail());
        // Assuming the password should be hidden
        userPasswordTextView.setText("Password: ********");
        userPhoneNumberTextView.setText("Phone Number: " + userModel.getPhoneNumber());
    }
}
