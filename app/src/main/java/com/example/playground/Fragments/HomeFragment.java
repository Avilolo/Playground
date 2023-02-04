package com.example.playground.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playground.Classes.MyGamesList;
import com.example.playground.Classes.GamesRecycleViewAdapter;
import com.example.playground.R;


public class HomeFragment extends Fragment {

    private MyGamesList myGamesList;
    private RecyclerView recyclerView;
    private GamesRecycleViewAdapter gamesRecycleViewAdapter;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        findViews(view);
        initVariables();

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
                myGamesList.filterList(newText, gamesRecycleViewAdapter);
                return false;
            }
        });

        return view;
    }

    private void initVariables() {
        myGamesList = new MyGamesList();
        myGamesList.initMyGamesList();
        //recyclerView initiation is at find views
        gamesRecycleViewAdapter = new GamesRecycleViewAdapter(getContext(), myGamesList.getGamesList());
        recyclerView.setAdapter(gamesRecycleViewAdapter);
    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.recycleView);
        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();
    }
}