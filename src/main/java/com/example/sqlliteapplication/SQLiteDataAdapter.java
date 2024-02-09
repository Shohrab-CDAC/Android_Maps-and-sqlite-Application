// SQLiteDataAdapter.java
package com.example.sqlliteapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SQLiteDataAdapter extends RecyclerView.Adapter<SQLiteDataAdapter.ViewHolder> {

    private List<UserModel> userList;

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewUserName;
        private TextView textViewUserEmail;
        private TextView textViewUserLocation;
        private TextView textViewUserPassword; // New TextView for encrypted password

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewUserEmail = itemView.findViewById(R.id.textViewUserEmail);
            textViewUserLocation = itemView.findViewById(R.id.textViewUserLocation);
            textViewUserPassword = itemView.findViewById(R.id.textViewUserPassword);
        }

        // Bind method to set data to views
        void bind(UserModel user) {
            textViewUserName.setText(""+ user.getName());
            textViewUserEmail.setText(""+ user.getEmail());
            textViewUserLocation.setText(""+ user.getLocation());

            // Encrypt the password before displaying
            String encryptedPassword = encryptPassword(user.getPassword());
            textViewUserPassword.setText(""+ encryptedPassword);
        }

        // Method to encrypt the password (You need to implement your encryption logic here)
        private String encryptPassword(String password) {
            // Implement your encryption logic here
            // For example, you can use libraries like BCrypt or your own encryption algorithm
            return password; // Replace this with your actual encryption logic
        }
    }
}
