package com.example.ebaycatalogsearch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> singleItemFragmentList = new ArrayList<>();
    private final ArrayList<String> singleItemFragmentTitleList =  new ArrayList<>();

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        singleItemFragmentList.add(fragment);
        singleItemFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return singleItemFragmentTitleList.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return singleItemFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return singleItemFragmentList.size();
    }
}
