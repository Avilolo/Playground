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
import java.util.Map;

public class UsersFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private UsersRecycleViewAdapter usersAdapter;
    private List<User> users;
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
        users = new ArrayList<>();
        readUsers();
        usersAdapter = new UsersRecycleViewAdapter(getContext(), users, true);
        recyclerView.setAdapter(usersAdapter);
        searchView.clearFocus();
    }

    private void readUsers() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);

                            //check if objects creation went wrong
                            assert firebaseUser != null;
                            assert user != null;

                            if ((!(user.getId().equals(firebaseUser.getUid())))
                                    && (user.getFriends() != null)) {
                                if (user.getFriends().containsKey(user.getId()))
                                    users.add(user);
                            }
                        }



//                DatabaseReference friendsRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid())
//                        .child("friends");
//
//                friendsRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot ssnapshot) {
//                        users.clear();
//                        User u = dataSnapshot.getValue(User.class);
//
//                        Map<String, String> friends = (Map<String, String>) ssnapshot.getValue();
//
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            User user = snapshot.getValue(User.class);
//
//                            //check if objects creation went wrong
//                            assert firebaseUser != null;
//                            assert user != null;
//
//                            if ((!(user.getId().equals(firebaseUser.getUid())))
//                                    && (friends != null)) {
//                                if (friends.containsKey(user.getId()))
//                                    users.add(user);
//                            }
//                        }
//                        usersAdapter = new UsersRecycleViewAdapter(getContext(), users, true);
//                        recyclerView.setAdapter(usersAdapter);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


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