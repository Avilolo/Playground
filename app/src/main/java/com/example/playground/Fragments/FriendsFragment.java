package com.example.playground.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashMap;
import java.util.List;


public class FriendsFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private UsersRecycleViewAdapter usersAdapter;
    private List<User> friends;
    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        findViews(view);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerView.setVisibility(View.VISIBLE);
                usersAdapter.filterList(newText);
                return false;
            }
        });


        return view;
    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.users_rv);
        searchView = view.findViewById(R.id.users_search_view);
        friends = new ArrayList<>();
        getFriendsFromDatabase();
        usersAdapter = new UsersRecycleViewAdapter(getContext(), friends, true);
        recyclerView.setAdapter(usersAdapter);
        searchView.clearFocus();
    }

    private void getFriendsFromDatabase() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friends.clear();
                /*First we get current user as object so we can iterate
                over his friends list and check each other user id.
                instead using two data changes we just loop twice.
                 */
                User currentUser = null;
                //First loop
                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                    User user1 = snapshot1.getValue(User.class);

                    //check if objects creation went wrong
                    assert firebaseUser != null;
                    assert user1 != null;

                    //Get current user as user object and checks if
                    // friends lists contains any other users id
                    if (user1.getId().equals(firebaseUser.getUid())
                            && (user1.getFriends() != null)) {
                        currentUser = user1;
                        continue;   //once we found current user we can stop loop
//                        if (currentUser.getFriends().containsKey(user.getId()))
//                            friends.add(user);
                    }
                }
                //Second loop
                for (DataSnapshot snapshot2 : dataSnapshot.getChildren()) {
                    User user2 = snapshot2.getValue(User.class);

                    //check if objects creation went wrong
                    assert firebaseUser != null;
                    assert user2 != null;

                    if (!user2.getId().equals(currentUser.getId())
                            && currentUser.getFriends().containsKey(user2.getId())) {
                            friends.add(user2);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void status(String status) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        databaseRef.updateChildren(hashMap);
    }

    @Override
    public void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    public void onPause() {
        super.onPause();
        status("offline");
    }
}