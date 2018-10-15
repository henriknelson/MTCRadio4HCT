package com.microntek.radio.ui;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class MyScroller extends Scroller {
    private int f705au = 1500;
    private Ui mUi;

    public MyScroller(Ui ui, Context context, Interpolator interpolator) {
        super(context, interpolator);
        this.mUi = ui;
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        super.startScroll(i, i2, i3, i4, this.f705au);
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        super.startScroll(i, i2, i3, i4, this.f705au);
    }

    /* renamed from: at */
    public void mo1910at(int i) {
        this.f705au = i;
    }
}
