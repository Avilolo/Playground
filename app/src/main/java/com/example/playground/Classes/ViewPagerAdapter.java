//package com.example.playground.Classes;
//
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentStatePagerAdapter;
//import androidx.viewpager2.adapter.FragmentStateAdapter;
//
//import java.util.ArrayList;
//
//public class ViewPagerAdapter extends FragmentStateAdapter {
//
//    private ArrayList<Fragment> fragments;
//    private ArrayList<String> titles;
//
//    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
//        super(fragmentActivity);
//        fragments = new ArrayList<>();
//        titles = new ArrayList<>();
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) { return fragments.get(position); }
//
//    @Override
//    public int getItemCount() {
//        return fragments.size();
//    }
//
//    public void addFragment(Fragment fragment, String title) {
//        fragments.add(fragment);
//        titles.add(title);
//    }
//
//    @Nullable
//    public CharSequence getPageTitle(int position) {
//        return titles.get(position);
//    }
//}
