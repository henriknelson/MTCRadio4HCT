package com.microntek.radio.ui;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import java.util.List;

class MyUIPagerAdapter extends PagerAdapter {
    private boolean f702ar;
    private List<View> mList;
    final Ui mUi;

    public MyUIPagerAdapter(Ui ui, List<View> list, boolean z) {
        this.mUi = ui;
        this.mList = list;
        this.f702ar = z;
    }

    /* renamed from: ao */
    public boolean mo1909ao() {
        return this.f702ar;
    }

    @Override
    public void destroyItem(View view, int i, Object obj) {
        ((ViewPager) view).removeView((View) this.mList.get(i % this.mList.size()));
    }

    public void mo1108am(View view) {

    }

    public int getCount() {
        return this.mList.size();
    }

    @Override
    public Object instantiateItem(View view, int i) {
        int size = i % this.mList.size();
        ((ViewPager) view).addView((View) this.mList.get(size), 0);
        return this.mList.get(size);
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    /* renamed from: as */
    public void mo1113as(View view) {
    }
}
