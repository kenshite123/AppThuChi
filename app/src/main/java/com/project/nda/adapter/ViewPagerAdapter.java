package com.project.nda.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.project.nda.fragment.AccountFragment;
import com.project.nda.fragment.RecieveMoneyFragment;
import com.project.nda.fragment.ReportFragment;
import com.project.nda.fragment.SpendMoneyFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
        {
            return new SpendMoneyFragment();
        }
        else if (position == 1)
        {
            return new RecieveMoneyFragment();
        }
        else if (position == 2)
        {
            return new AccountFragment();
        }
        else if (position == 3)
        {
            return new ReportFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
