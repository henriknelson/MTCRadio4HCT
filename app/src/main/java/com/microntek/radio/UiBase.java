package com.microntek.radio;

import android.app.Activity;

/* renamed from: com.microntek.radio.c */
public abstract class UiBase {
    /* renamed from: ch */
    protected OnFuncInterface mOnFuncInterface;
    /* renamed from: ci */
    protected Activity mActivity;

    /* renamed from: a */
    public abstract void showFrequencyText(boolean z);

    /* renamed from: aa */
    public abstract void mo1802aa(int i, int i2, int[][] iArr, boolean z);

    /* renamed from: ab */
    public abstract void mo1803ab();

    /* renamed from: ac */
    public abstract void mo1804ac(int i);

    /* renamed from: ad */
    public abstract void mo1805ad();

    /* renamed from: c */
    public abstract int getFrequency();

    /* renamed from: f */
    public abstract int getLayout();

    /* renamed from: h */
    public abstract void setupViews();

    /* renamed from: i */
    public abstract boolean mo1809i();

    /* renamed from: j */
    public abstract void mo1810j(boolean z);

    /* renamed from: k */
    public abstract void mo1811k(boolean z);

    /* renamed from: l */
    public abstract void mo1812l(boolean z, boolean z2);

    /* renamed from: m */
    public abstract void mo1813m(int i, int i2, int[][] iArr, boolean z);

    /* renamed from: n */
    public abstract void mo1814n(boolean z);

    /* renamed from: o */
    public abstract void setFrequency(int i);

    /* renamed from: p */
    public abstract void mo1816p(boolean z, boolean z2);

    /* renamed from: q */
    public abstract void mo1817q(boolean z, int i);

    /* renamed from: r */
    public abstract void setPtyChoiceListVisibility(boolean z);

    /* renamed from: s */
    public abstract void setFrequencyTexts(String str);

    /* renamed from: t */
    public abstract void setPtyTextView(String str);

    /* renamed from: u */
    public abstract void setRtText(String str);

    /* renamed from: v */
    public abstract void mo1822v(boolean z, boolean z2);

    /* renamed from: w */
    public abstract void mo1823w(boolean z);

    /* renamed from: x */
    public abstract void mo1824x(boolean z);

    /* renamed from: y */
    public abstract void mo1825y(boolean z, boolean z2);

    /* renamed from: z */
    public abstract void mo1826z(boolean z, boolean z2);
}
