package com.microntek.radio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.lang.reflect.Array;

public class BaseActivity extends Activity {
    /* renamed from: ce */
    protected IRadioService mRadioService = null;
    /* renamed from: cf */
    protected int freq = 0;
    /* renamed from: cg */
    private int startbackFlag = 0;

    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("startbackflag", this.startbackFlag);
        super.onSaveInstanceState(bundle);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent.hasExtra("freq")) {
            try {
                this.freq = Integer.parseInt(intent.getStringExtra("freq"));
            } catch (Exception e) {
            }
        }
        if (bundle != null) {
            this.startbackFlag = bundle.getInt("startbackflag");
        }
        if (this.startbackFlag == 0) {
            this.startbackFlag = getIntent().getIntExtra("start", 0);
            if (this.startbackFlag != 0) {
                moveTaskToBack(true);
            }
        }
    }

    /* renamed from: dm */
    protected boolean mo1715dm() {
        if (this.mRadioService != null) {
            return this.mRadioService.mo1853ka();
        }
        return true;
    }

    /* renamed from: dn */
    protected boolean mo1716dn() {
        if (this.mRadioService != null) {
            return this.mRadioService.getStOn();
        }
        return true;
    }

    /* renamed from: do */
    protected boolean mo1717do() {
        if (this.mRadioService != null) {
            return this.mRadioService.getLocOn();
        }
        return true;
    }

    /* renamed from: dp */
    protected boolean mo1718dp() {
        if (this.mRadioService != null) {
            return this.mRadioService.mo1854kb();
        }
        return true;
    }

    /* renamed from: dq */
    protected boolean mo1719dq() {
        if (this.mRadioService != null) {
            return this.mRadioService.getTaOn();
        }
        return true;
    }

    /* renamed from: dr */
    protected boolean mo1720dr() {
        if (this.mRadioService != null) {
            return this.mRadioService.getAfOn();
        }
        return true;
    }

    /* renamed from: ds */
    protected int mo1721ds() {
        if (this.mRadioService != null) {
            return this.mRadioService.getPty();
        }
        return 0;
    }

    /* renamed from: dt */
    protected int mo1722dt() {
        if (this.mRadioService != null) {
            return this.mRadioService.getRadioChannel();
        }
        return 0;
    }

    /* renamed from: du */
    protected int[][] mo1723du() {
        int[][] iArr = (int[][]) Array.newInstance(Integer.TYPE, new int[]{5, 6});
        if (this.mRadioService != null) {
            int[] jt = this.mRadioService.mo1846jt();
            for (int i = 0; i < 5; i++) {
                for (int i2 = 0; i2 < 6; i2++) {
                    iArr[i][i2] = jt[(i * 6) + i2];
                }
            }
        }
        return iArr;
    }

    /* renamed from: dw */
    protected boolean mo1725dw() {
        if (this.mRadioService != null) {
            return this.mRadioService.mo1847ju();
        }
        return true;
    }

    /* renamed from: dv */
    protected boolean mo1724dv() {
        if (this.mRadioService != null) {
            return this.mRadioService.mo1855kc();
        }
        return true;
    }

    /* renamed from: dx */
    protected String mo1726dx() {
        String str = "";
        if (this.mRadioService != null) {
            return this.mRadioService.mo1850jx();
        }
        return str;
    }

    /* renamed from: dy */
    protected String mo1727dy() {
        String str = "";
        if (this.mRadioService != null) {
            return this.mRadioService.mo1848jv();
        }
        return str;
    }

    /* renamed from: dz */
    protected int mo1728dz() {
        if (this.mRadioService != null) {
            return this.mRadioService.mo1849jw();
        }
        return 0;
    }

    /* renamed from: ea */
    protected boolean mo1729ea() {
        if (this.mRadioService != null) {
            return this.mRadioService.mo1851jy();
        }
        return true;
    }

    /* renamed from: ed */
    protected void mo1732ed() {
        if (this.mRadioService != null) {
            this.mRadioService.mo1832jf();
        }
    }

    /* renamed from: eb */
    protected void mo1730eb() {
        if (this.mRadioService != null) {
            this.mRadioService.mo1833jg();
        }
    }

    /* renamed from: ec */
    protected void mo1731ec() {
        if (this.mRadioService != null) {
            this.mRadioService.mo1827iw();
        }
    }

    /* renamed from: ee */
    protected void mo1733ee() {
        if (this.mRadioService != null) {
            this.mRadioService.mo1830jd();
        }
    }
}
