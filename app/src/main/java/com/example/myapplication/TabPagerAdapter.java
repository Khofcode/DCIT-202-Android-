package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class TabPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_TABS = 3; // Number of tabs

    public TabPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Return the appropriate fragment based on the tab position
        switch (position) {
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}

