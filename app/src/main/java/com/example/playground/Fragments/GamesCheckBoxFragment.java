package com.example.playground.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.playground.Classes.Game;
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


public class GamesCheckBoxFragment extends Fragment {

    ListView listView;
    Button allGamesSaveBtn;
    ArrayList<String> allGamesPlaying;
    ArrayList<String> allGames;
    ArrayAdapter<String> arrayAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games_check_box, container, false);

        findViews(view);

        allGamesSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSelectedItems();
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("gamesPlaying");


                databaseRef.setValue(allGamesPlaying);
                requireActivity().getSupportFragmentManager().beginTransaction().remove(GamesCheckBoxFragment.this).commit();
            }
        });

        return view;
    }

    private void getSelectedItems() {
        for (int i = 0; i < listView.getCount(); i++) {
            if (listView.isItemChecked(i)) {
                allGamesPlaying.add(listView.getItemAtPosition(i).toString());
            }
        }
    }

    private void findViews(View view) {
        listView = view.findViewById(R.id.games_check_box);
        allGamesSaveBtn = view.findViewById(R.id.save_games_btn);
        allGamesPlaying = new ArrayList<String>();
        allGames = new ArrayList<String>();
        initiateAllGames();
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice,
                allGames);
        listView.setAdapter(arrayAdapter);
    }

    private void initiateAllGames() {
        allGames.add("A.V.A");
        allGames.add("Black desert");
        allGames.add("Dark souls 3");
        allGames.add("League of legends");
        allGames.add("Maple story");
        allGames.add("Minecraft");
        allGames.add("Pubg");
        allGames.add("Sea of thieves");
        allGames.add("Terraria");
        allGames.add("Velhaim");
        allGames.add("Warzone");
        allGames.add("World of warcraft");
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.all_games_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.all_games) {
            String itemSelected = "Selected items: \n";
            for (int i = 0; i < listView.getCount(); i++) {
                if (listView.isItemChecked(i)) {
                    itemSelected += listView.getItemAtPosition(i) + "\n";
                }
            }
            Toast.makeText(getContext(), itemSelected, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.all_games) {
//            String itemSelected = "Selected items: \n";
//            for (int i = 0; i < listView.getCount(); i++) {
//                if (listView.isItemChecked(i)) {
//                    itemSelected += listView.getItemAtPosition(i) + "\n";
//                }
//            }
//            Toast.makeText(getContext(), itemSelected, Toast.LENGTH_SHORT).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }

}