package com.example.playground.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.playground.Fragments.HomeFragment;
import com.example.playground.Fragments.ProfileFragment;
import com.example.playground.Fragments.FriendsFragment;
import com.example.playground.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    FriendsFragment friendsFragment = new FriendsFragment();
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
                        getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment, friendsFragment).commit();
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
}