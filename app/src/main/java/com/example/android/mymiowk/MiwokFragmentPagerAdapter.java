package com.example.android.mymiowk;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;


public class MiwokFragmentPagerAdapter extends PagerAdapter {

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    public Fragment getItem() {
        return new NumbersFragmentActivity();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
