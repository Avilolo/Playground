package com.example.playground.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.playground.Classes.User;
import com.example.playground.Classes.UsersPlayingSelectedGameAdapter;
import com.example.playground.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersPlayingThisGameActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private UsersPlayingSelectedGameAdapter usersAdapter;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_playing_this_game);

        findViews();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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
        // TODO initiate adapter and set it
        users = new ArrayList<>();
        getPlayersPlayingGame();
        usersAdapter = new UsersPlayingSelectedGameAdapter(this, users);
        recyclerView.setAdapter(usersAdapter);
        searchView = findViewById(R.id.users_search_view);
        searchView.clearFocus();
    }

    private void getPlayersPlayingGame() {
        //TODO
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users");

        //gets all the users data
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //gets all games played by user
                for (DataSnapshot snap1 : snapshot.getChildren()) {
                    User user = snap1.getValue(User.class);

                    assert user != null;
//                    if (user != null) {
                    if (!user.getId().equals(currentUser.getUid())) {
                        DatabaseReference gamesPlayedByUser = FirebaseDatabase
                                .getInstance()
                                .getReference("Users")
                                .child(user.getId())
                                .child("gamesPlaying");
                        //TODO FOR SOME REASON GAMES CHILD IS EMPTY
                        System.out.println("a");
                        gamesPlayedByUser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                users.clear();

                                ArrayList<String> games = (ArrayList<String>) snapshot2.getValue();
                                if (games.contains(getIntent().getStringExtra("selectedGameName"))) {
                                    users.add(user);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}