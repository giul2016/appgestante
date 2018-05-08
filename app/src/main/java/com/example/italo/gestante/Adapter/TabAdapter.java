package com.example.italo.gestante.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.italo.gestante.Login_gestante;
import com.example.italo.gestante.Login_medico;

import java.util.List;

/**
 * Created by italo on 01/12/2017.
 */


public class TabAdapter extends FragmentPagerAdapter{

    int PAGE_COUNT;
    private List<Fragment> fragments;
    private String tabs[];
    private Context context;

    public TabAdapter(FragmentManager fm, Context context, List<Fragment> fragments, String tabs[]) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
        this.tabs = tabs;
        PAGE_COUNT = fragments.size();
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new Login_gestante();
                break;
            case 1:
                fragment = new Login_medico();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    //recupera titulo aba
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
