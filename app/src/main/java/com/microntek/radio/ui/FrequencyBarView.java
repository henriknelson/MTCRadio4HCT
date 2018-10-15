package com.microntek.radio.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.microntek.radio.R;

public class FrequencyBarView extends View {
    /* renamed from: az */
    private boolean f649az = false;
    /* renamed from: ba */
    private Bitmap f650ba = null;
    /* renamed from: bb */
    private FreqencyBarCallbackInterface f651bb;
    /* renamed from: bc */
    private int f652bc = 111500000;
    /* renamed from: bd */
    private int f653bd = 83000000;
    /* renamed from: be */
    private int f654be = 108000000;
    /* renamed from: bf */
    private int f655bf = 87500000;
    /* renamed from: bg */
    private int f656bg = 50000;
    private int freq = 87500000;

    public FrequencyBarView(Context context) {
        super(context);
    }

    public FrequencyBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FrequencyBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.f650ba == null) {
            this.f650ba = BitmapFactory.decodeResource(getResources(), R.drawable.fb_ptr);
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(this.f650ba, (float) (((((this.freq - this.f653bd) / 1000) * getWidth()) / ((this.f652bc - this.f653bd) / 1000)) - (this.f650ba.getWidth() / 2)), 0.0f, new Paint());
        super.onDraw(canvas);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.f650ba != null) {
            this.f650ba.recycle();
            this.f650ba = null;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                m851bd();
                break;
            case 1:
            case 3:
                m852be();
                break;
            case 2:
                this.freq = m849bb((((((int) motionEvent.getX()) * ((this.f652bc - this.f653bd) / 1000)) / getMeasuredWidth()) * 1000) + this.f653bd);
                invalidate();
                m850bc();
                break;
        }
        return true;
    }

    /* renamed from: bd */
    private void m851bd() {
        if (this.f651bb != null) {
            this.f651bb.mo1914bg(this);
        }
    }

    /* renamed from: be */
    private void m852be() {
        if (this.f651bb != null) {
            this.f651bb.mo1915bh(this);
        }
    }

    /* renamed from: ax */
    public int getFrequency() {
        return this.freq;
    }

    /* renamed from: bb */
    private int m849bb(int i) {
        if (this.f649az) {
            if (i < this.f655bf) {
                return this.f655bf;
            }
            if (i <= 74000000) {
                return (((i - 65000000) / 30000) * 30000) + 65000000;
            }
            if (i < 81000000) {
                return 74000000;
            }
            if (i < 87500000) {
                return 87500000;
            }
            return i <= 108000000 ? (((i - 87500000) / 100000) * 100000) + 87500000 : 108000000;
        } else if (i < this.f655bf) {
            return this.f655bf;
        } else {
            if (i > this.f654be) {
                return this.f654be;
            }
            return (((i - this.f655bf) / this.f656bg) * this.f656bg) + this.f655bf;
        }
    }

    /* renamed from: bc */
    private void m850bc() {
        if (this.f651bb != null) {
            this.f651bb.onFrequencyChanged(this);
        }
    }

    /* renamed from: az */
    public void mo1893az(int i, int i2, int i3, boolean z) {
        if (z) {
            this.f649az = false;
            this.f655bf = i;
            this.f654be = i2;
            this.f656bg = i3;
            this.f653bd = 436000;
            this.f652bc = 1804000;
            getBackground().setLevel(3);
            return;
        }
        this.f655bf = i;
        this.f654be = i2;
        this.f656bg = i3;
        if (this.f655bf == 87500000 || this.f655bf == 87600000) {
            this.f649az = false;
            this.f653bd = 86065000;
            this.f652bc = 109435000;
            getBackground().setLevel(2);
        } else if (this.f654be == 74000000 || this.f654be == 90000000) {
            this.f649az = false;
            this.f653bd = 63250000;
            this.f652bc = 91750000;
            getBackground().setLevel(0);
        } else {
            this.f649az = true;
            this.f653bd = 61990000;
            this.f652bc = 110610000;
            getBackground().setLevel(1);
        }
    }

    /* renamed from: ba */
    public void mo1894ba(int i) {
        this.freq = m849bb(i);
        invalidate();
    }

    /* renamed from: ay */
    public void setFrequencyBarCallbackListener(FreqencyBarCallbackInterface freqencyBarCallbackInterface) {
        this.f651bb = freqencyBarCallbackInterface;
    }
}
