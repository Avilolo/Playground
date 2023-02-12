package com.example.playground.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

//import com.example.playground.Fragments.ChatParentFragment;
import com.example.playground.Fragments.HomeFragment;
import com.example.playground.Fragments.ProfileFragment;
import com.example.playground.Fragments.UsersFragment;
import com.example.playground.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
//    ChatParentFragment chatParentFragment = new ChatParentFragment();
    UsersFragment usersFragment = new UsersFragment();
    ProfileFragment profileFragment = new ProfileFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment, homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment, homeFragment).commit();
                        return true;
                    case R.id.ic_chat:
                        getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment, usersFragment).commit();
                        return true;
                    case R.id.ic_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment, profileFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    private void findViews() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
    }

//    private void status(String status) {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("status", status);
//
//        databaseRef.updateChildren(hashMap);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        status("online");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        status("offline");
//    }
}