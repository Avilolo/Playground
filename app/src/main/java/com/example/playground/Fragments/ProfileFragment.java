package com.example.playground.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.playground.Activities.LoginActivity;
import com.example.playground.Classes.User;
import com.example.playground.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    CircleImageView profileImage;
    TextView profileName, infoName;
    Toolbar toolbar;
    FirebaseUser firebaseUser;
    DatabaseReference databaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        findViews(view);
        setHasOptionsMenu(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);
                //check if objects creation went wrong
                assert firebaseUser != null;
                assert user != null;

                profileName.setText(user.getUsername());
                profileName.setText(user.getUsername());
                if (user.getImageURL().equals("default"))
                    profileImage.setImageResource(R.drawable.default_profile);
                else
                    Glide.with(ProfileFragment.this).load(user.getImageURL()).into(profileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("fail", "onCancelled: ");
            }
        });
        return view;
    }

    private void findViews(View view) {
        profileImage = view.findViewById(R.id.user_profile_image);
        profileName = view.findViewById(R.id.profile_name_tv);
        infoName = view.findViewById(R.id.name_info_tv);
        toolbar = view.findViewById(R.id.profile_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.logout_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                return true;
        }
        return false;
    }





















}