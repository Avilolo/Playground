package com.example.playground.Classes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.playground.R;

public class MessageHolder extends RecyclerView.ViewHolder {

    public TextView show_msg;
    public ImageView profile_image;

    public MessageHolder(View itemView) {
        super(itemView);

        findViews(itemView);
    }

    private void findViews(View itemView) {
        show_msg = itemView.findViewById(R.id.msg_text);
        profile_image = itemView.findViewById(R.id.msg_profile);
    }
}