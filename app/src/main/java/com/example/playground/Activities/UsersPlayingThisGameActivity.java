package com.example.playground.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.playground.Classes.User;
import com.example.playground.Classes.UsersRecycleViewAdapter;
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
        usersAdapter = new UsersRecycleViewAdapter(this, users, true);
        recyclerView.setAdapter(usersAdapter);
        searchView = findViewById(R.id.users_search_view);
        searchView.clearFocus();
    }

    private void getPlayersPlayingGame() {
        //TODO
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users");

        //gets all the users data
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                //gets all games played by user
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User user = snap.getValue(User.class);

                    assert user != null;
                    if (user != null) {

                        if (!user.getId().equals(currentUser.getUid()) &&
                                user.getGamesPlaying().contains(getIntent().getStringExtra("selectedGameName")))
                            users.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}