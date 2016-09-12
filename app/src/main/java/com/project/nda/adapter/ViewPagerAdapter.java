package com.project.nda.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.project.nda.Fragment.TaiKhoan_Fragment;
import com.project.nda.Fragment.ThuTien_Fragment;
import com.project.nda.Fragment.BaoCao_Fragment;
import com.project.nda.Fragment.ChiTien_Fragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
        {
            return new ChiTien_Fragment();
        }
        else if (position == 1)
        {
            return new ThuTien_Fragment();
        }
        else if (position == 2)
        {
            return new TaiKhoan_Fragment();
        }
        else if (position == 3)
        {
            return new BaoCao_Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
