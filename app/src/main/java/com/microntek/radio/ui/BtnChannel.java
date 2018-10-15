package com.microntek.radio.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.microntek.radio.R;

public class BtnChannel extends FrameLayout {
    private int f648ay;

    public BtnChannel(Context context) {
        super(context);
    }

    public BtnChannel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setFrequencyText(String frequencyText) {
        Log.i("nu.cliffords.radioUiBtnChannel","setFrequencyText");
        ((TextView) findViewById(R.id.txt_freq)).setText(frequencyText);
    }

    public void mo1889av(int i) {
        this.f648ay = i;
        ((TextView) findViewById(R.id.txt_channel)).setText(getContext().getString((this.f648ay < 18 ? this.f648ay : this.f648ay - 18) + R.string.p1));
    }

    public int mo1888au() {
        return this.f648ay;
    }
}
