package com.example.playground.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playground.Classes.ViewPagerAdapter;
import com.example.playground.R;
import com.google.android.material.tabs.TabLayout;


public class ChatParentFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_parent, container, false);

        findViews(view);


        return view;
    }

    private void findViews(View view) {
        tabLayout = view.findViewById(R.id.chat_tab);
        viewPager = view.findViewById(R.id.chat_vp);
        viewPagerAdapter = new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.addFragment(new ChatFragment(), "Chat");
        viewPagerAdapter.addFragment(new UsersFragment(), "Users");
    }
}