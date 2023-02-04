package com.example.playground.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import java.util.List;
import java.util.Map;

public class UsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private UsersRecycleViewAdapter usersAdapter;
    private List<User> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        findViews(view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        users = new ArrayList<>();

        readUsers();

        return view;
    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.users_rv);
    }

    private void readUsers() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                DatabaseReference friendsRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid())
                        .child("friends");

                friendsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot ssnapshot) {
                        users.clear();
                        User u = dataSnapshot.getValue(User.class);

                        Map<String, String> friends = (Map<String, String>)  ssnapshot.getValue();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            //check if objects creation went wrong
                            assert firebaseUser != null;
                            assert user != null;

                            if ((!(user.getId().equals(firebaseUser.getUid())))
                            && (friends != null))
                             {
                                 if (friends.containsKey(user.getId()))
                                    users.add(user);
                                //TODO understand how to create user = current user object and check if his
                                //TODO friends contains any of the users names
                            }
                        }
                        usersAdapter = new UsersRecycleViewAdapter(getContext(), users);
                        recyclerView.setAdapter(usersAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}