package com.example.playground.Classes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playground.R;

import java.util.List;

public class GamesRecycleViewAdapter extends RecyclerView.Adapter<GameViewHolder> {

    private Context context;
    private List<Game> games;

    public GamesRecycleViewAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
    }

    public void setFilteredGames(List<Game> filteredGames) {
        games = filteredGames;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameViewHolder(LayoutInflater.from(context).inflate(R.layout.game_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        holder.name.setText(games.get(position).getName());
        holder.image.setImageResource(games.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO continue
                Log.d("TAG", "onClick: ");
            }
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}
