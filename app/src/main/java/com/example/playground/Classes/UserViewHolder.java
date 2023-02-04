package com.example.playground.Classes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playground.R;

public class UserViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    ImageView image;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        findViews(itemView);
    }

    private void findViews(View itemView) {
        name = itemView.findViewById(R.id.user_name_TV);
        image = itemView.findViewById(R.id.user_image_IV);
    }
}
