package com.example.playground.Classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.playground.Activities.MessageActivity;
import com.example.playground.R;

import java.util.ArrayList;
import java.util.List;

public class UsersPlayingSelectedGameAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private Context context;
    private List<User> users;

    public UsersPlayingSelectedGameAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    public void filterList(String text) {
        List<User> filteredUsers = new ArrayList<>();
        for (User user: users) {
            if (user.getUsername().toLowerCase().contains(text.toLowerCase())) {
                filteredUsers.add(user);
            }
        }
        if (filteredUsers.isEmpty()) {
            //TODO maybe do something here
            return;
        }
        else {
            users = filteredUsers;
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.name.setText(user.getUsername());
        if (user.getImageURL().equals("default"))
            holder.image.setImageResource(R.drawable.default_profile);
        else
            Glide.with(context).load(user.getImageURL()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}