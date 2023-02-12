package com.example.playground.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.playground.Classes.User;
import com.example.playground.Classes.UsersRecycleViewAdapter;
import com.example.playground.R;

import java.util.ArrayList;
import java.util.List;

public class UsersPlayingThisGameActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private UsersRecycleViewAdapter usersAdapter;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_playing_this_game);

        findViews();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        users = new ArrayList<>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                usersAdapter.filterList(newText);
                return false;
            }
        });
    }

    private void findViews() {
        recyclerView = findViewById(R.id.users_rv);
        searchView = findViewById(R.id.users_search_view);
        searchView.clearFocus();
    }

    private void getPlayersPlayingGame() {

    }
}