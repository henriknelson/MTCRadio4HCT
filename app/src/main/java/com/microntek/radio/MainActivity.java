package com.microntek.radio;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.microntek.CarManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;

import com.microntek.radio.ui.Ui;

public class MainActivity extends BaseActivity implements OnFuncInterface {

    private class RadioServiceCallback implements ServiceCallback
    {
        public void mo1797bt() { if (mRadioService != null) { mo1766ca(mRadioService.getCurrentFrequency()); } }
        public void mo1796bs() {
            mo1765bz();
        }
        public void mo1795br() {
            mo1764by();
        }
        public void mo1800bw() {
            mo1770ce();
        }
        public void mo1799bv() {
            mo1768cc();
        }
        public void mo1798bu() {
            mo1767cb();
        }
        public void mo1794bq(boolean z) {
            mUi.mo1811k(z);
        }
        public void mo1793bp(boolean z) {
            mUi.mo1810j(z);
        }
        public void mo1790bm(boolean z) {
            mUi.showFrequencyText(z);
        }
        public boolean mo1792bo() { return mUi.mo1809i(); }
        public void mo1789bl() {
            finish();
        }
        public boolean mo1791bn() {
            return f555cc;
        }
    }

    private RadioServiceCallback mRadioServiceCallback = new RadioServiceCallback();
    private CarManager mCarManager = null;
    private boolean f550bx = false;
    private int displayWidth = -1;

    protected ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("nu.cliffords.radioActivity","serviceConnection.onServiceConnected");
            mRadioService = (IRadioService) service;
            mRadioService.setCallbackListener(mRadioServiceCallback);
            mRadioService.mo1852jz();
            mRadioService.mo1870ks();
            mo1763bx();
            mRadioService.mo1862kj(freq);
            mRadioService.mo1867kp(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("nu.cliffords.radioActivity","serviceConnection.onServiceDisconnected");
            mRadioService.mo1867kp(false);
            mRadioService.setCallbackListener(null);
            mRadioService = null;
        }
    };
    private Ui mUi;
    private boolean showPtyButtonBar = false;
    private boolean f555cc = false;

    private boolean m542ch() {
        Log.i("nu.cliffords.radioActivity","m542ch -- isDisplaySupported?");
        if (this.displayWidth == 1024 || this.displayWidth == 800 || this.displayWidth <= 505 || this.displayWidth <= 393) {
            Log.i("nu.cliffords.radioActivity","m542ch returns true");
            return true;
        }
        Log.i("nu.cliffords.radioActivity","m542ch returns false");
        return false;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.i("nu.cliffords.radioActivity","onConfigurationChanged");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.displayWidth = displayMetrics.widthPixels;
        if (m542ch()) {
            this.mUi = new Ui(this);
            setContentView(this.mUi.getLayout());
            this.mUi.setupViews();
            this.mRadioService.mo1852jz();
            mo1763bx();
            this.mRadioService.mo1862kj(this.freq);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.i("nu.cliffords.radioActivity","onCreate");
        Intent intent = new Intent("com.microntek.bootcheck");
        intent.putExtra("class", "com.microntek.radio");
        sendBroadcast(intent);
        bindService(new Intent(this, RadioService.class), this.serviceConnection, Context.BIND_AUTO_CREATE);
        this.mUi = new Ui(this);
        setContentView(this.mUi.getLayout());
        this.mUi.setupViews();
        this.mCarManager = new CarManager();
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    try {
                        Thread.sleep(1000);
                        if (mUi.getFrequency() > 0) {
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                } while (!mCarManager.getBooleanState("boot_complete"));
                mRadioService.mo1852jz();
            }
        }).start();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("nu.cliffords.radioActivity","onNewIntent");
        setIntent(intent);
        m543dh();
    }

    private void m543dh() {
        Log.i("nu.cliffords.radioActivity","m543dh");
        Intent intent = getIntent();
        if (intent.hasExtra("freq")) {
            try {
                this.freq = Integer.parseInt(intent.getStringExtra("freq"));
            } catch (Exception e) {
            }
            if (this.mRadioService != null) {
                this.mRadioService.mo1864km(this.freq);
            }
        }
    }

    private void m541cf() {
        Log.i("nu.cliffords.radioActivity","m541cf");
        if (!this.f550bx) {
            this.f550bx = true;
            if (this.mRadioService != null) {
                this.mRadioService.mo1869kr();
            }
            unbindService(this.serviceConnection);
            startService(new Intent(this, RadioService.class));
        }
    }

    public void finish() {
        Log.i("nu.cliffords.radioActivity","finish");
        Log.i("chun", "radio finish");
        m541cf();
        super.finish();
    }

    protected void onDestroy() {
        Log.i("nu.cliffords.radioActivity","onDestroy");
        Log.i("chun", "radio onDestroy");
        m541cf();
        super.onDestroy();
    }

    protected void onResume() {
        Log.i("nu.cliffords.radioActivity","onResume");
        super.onResume();
        this.mUi.mo1803ab();
        this.mUi.mo1805ad();
        this.f555cc = true;
        if (this.mRadioService != null) {
            this.mRadioService.mo1870ks();
        }
        sendBroadcast(new Intent("com.microntek.musicclockreset"));
    }

    public void onBackPressed() {
        finish();
    }

    protected void onPause() {
        Log.i("nu.cliffords.radioActivity","onPause");
        this.f555cc = false;
        super.onPause();
    }

    /* renamed from: bx */
    public void mo1763bx() {
        Log.i("nu.cliffords.radioActivity","mo1763bx");
        mo1769cd();
        mo1764by();
        mo1765bz();
    }

    /* renamed from: ca */
    public void mo1766ca(int frequency) {
        Log.i("nu.cliffords.radioActivity","mo1766ca");
        this.mUi.setFrequency(frequency);
        sendActiveBroadcast();
    }

    /* renamed from: bz */
    public void mo1765bz() {
        Log.i("nu.cliffords.radioActivity","mo1765bz - setupGUIstate?");
        if (mo1715dm()) {
            this.mUi.mo1825y(true, mo1716dn());
            this.mUi.mo1816p(true, mo1717do());
            if (mo1718dp()) {
                this.mUi.mo1826z(true, mo1719dq());
                this.mUi.mo1812l(true, mo1720dr());
                this.mUi.mo1817q(true, mo1721ds());
                this.mUi.mo1824x(true);
            } else {
                this.mUi.mo1824x(false);
                this.mUi.mo1826z(false, false);
                this.mUi.mo1812l(false, false);
                this.mUi.mo1817q(false, 0);
                this.mUi.setPtyChoiceListVisibility(false);
                this.showPtyButtonBar = false;
            }
            this.mUi.mo1814n(true);
            return;
        }
        this.mUi.mo1814n(false);
        this.mUi.mo1824x(false);
        this.mUi.mo1825y(false, false);
        this.mUi.mo1816p(false, false);
        this.mUi.mo1826z(false, false);
        this.mUi.mo1812l(false, false);
        this.mUi.mo1817q(false, 0);
        this.mUi.setPtyChoiceListVisibility(false);
        this.showPtyButtonBar = false;
    }

    /* renamed from: by */
    public void mo1764by() {
        this.mUi.mo1813m(getRadioBand(), mo1722dt(), mo1723du(), mo1724dv());
    }

    /* renamed from: ce */
    public void mo1770ce() {
        this.mUi.mo1802aa(getRadioBand(), mo1722dt(), mo1723du(), mo1724dv());
    }

    /* renamed from: cd */
    public void mo1769cd() {
        this.mUi.mo1804ac(mo1721ds());
    }

    /* renamed from: cc */
    public void mo1768cc() {
        if (mo1715dm()) {
            this.mUi.mo1822v(mo1716dn(), mo1725dw());
        } else {
            this.mUi.mo1822v(false, mo1725dw());
        }
    }

    /* renamed from: cb */
    public void mo1767cb() {
        if (mo1718dp()) {
            this.mUi.setRtText(mo1726dx());
            this.mUi.setFrequencyTexts(mo1727dy());
            int dz = mo1728dz();
            if (dz != 0) {
                this.mUi.setPtyTextView(getString(dz + R.string.pty00));
            } else {
                this.mUi.setPtyTextView("");
            }
            this.mUi.mo1823w(mo1729ea());
            return;
        }
        this.mUi.setRtText("");
        this.mUi.setFrequencyTexts("");
        this.mUi.setPtyTextView("");
        this.mUi.mo1823w(false);
    }

    /* renamed from: cq */
    public void onTaButtonClicked() {
        mo1730eb();
        mo1765bz();
    }

    /* renamed from: cl */
    public void onAfButtonClicked() {
        mo1731ec();
        mo1765bz();
    }

    /* renamed from: cp */
    public void onStButtonClicked() {
        mo1732ed();
        mo1765bz();
    }

    /* renamed from: cm */
    public void onLocButtonClicked() {
        mo1733ee();
        mo1765bz();
    }

    /* renamed from: cz */
    public void sendBeepBroadcast() {

        sendBroadcast(new Intent("com.microntek.beep"));
    }

    /* renamed from: dg */
    public void mo1762dg(int i, int i2) {
        if (this.mRadioService != null) {
            this.mRadioService.mo1863kk(i, i2);
        }
        mo1765bz();
    }

    /* renamed from: db */
    public void mo1757db(int i, int i2) {
        if (this.mRadioService != null) {
            this.mRadioService.mo1857ke(i, i2);
        }
    }

    /* renamed from: cn */
    public void onPtyButtonClicked() {
        if (mo1718dp()) {
            //TODO: this.showPtyButtonBar ^= 1;
            this.showPtyButtonBar = !this.showPtyButtonBar;
            this.mUi.setPtyChoiceListVisibility(this.showPtyButtonBar);
        }
    }

    /* renamed from: dd */
    public void btnNext() {
        Log.i("nu.cliffords.radioActivity","btnNext");
        if (this.mRadioService != null) {
            this.mRadioService.setRadioSeekUp();
        }
    }

    /* renamed from: de */
    public void btnPrevious() {
        Log.i("nu.cliffords.radioActivity","btnPrevious");
        if (this.mRadioService != null) {
            this.mRadioService.setRadioSeekDown();
        }
    }

    /* renamed from: dc */
    public void onSearchButtonClicked() {
        Log.i("nu.cliffords.radioActivity","onSearchButtonClicked");
        if (this.mRadioService != null) {
            this.mRadioService.setRadioSeekAuto();
        }
    }

    /* renamed from: da */
    public void startSearch() {
        if (this.mRadioService != null) {
            this.mRadioService.startSearch();
        }
    }

    /* renamed from: cr */
    public void onWlgkCustomerAndPreviousButtonClicked() {
        Log.i("nu.cliffords.radioActivity","onWlgkCustomerAndPreviousButtonClicked - setRadioTuneDown?");
        if (this.mRadioService != null) {
            this.mRadioService.setRadioTuneDown();
        }
        this.mUi.mo1810j(false);
    }

    /* renamed from: cs */
    public void onWlgkCustomerAndNextButtonClicked() {
        Log.i("nu.cliffords.radioActivity","onWlgkCustomerAndNextButtonClicked - setRadioTuneUp?");
        if (this.mRadioService != null) {
            this.mRadioService.setRadioTuneUp();
        }
        this.mUi.mo1810j(false);
    }

    /* renamed from: co */
    public void onPtySelected(int i) {
        Log.i("nu.cliffords.radioActivity","onPtySelected");
        this.mUi.mo1804ac(i);
        this.showPtyButtonBar = false;
        this.mUi.setPtyChoiceListVisibility(this.showPtyButtonBar);
        if (this.mRadioService != null) {
            this.mRadioService.mo1831je(i);
        }
        mo1765bz();
    }

    /* renamed from: ct */
    public int mo1749ct() {
        Log.i("nu.cliffords.radioActivity","mo1749ct");
        if (this.mRadioService != null) {
            int retInt = this.mRadioService.mo1842jp();
            Log.i("nu.cliffords.radioActivity","mo1749ct returns " + retInt);
            return retInt;
        }
        Log.i("nu.cliffords.radioActivity","mo1749ct returns " + 0);
        return 0;
    }

    /* renamed from: cu */
    public int mo1750cu() {
        Log.i("nu.cliffords.radioActivity","mo1750cu");
        if (this.mRadioService != null) {
            return this.mRadioService.mo1843jq();
        }
        return 0;
    }

    /* renamed from: cw */
    public int mo1752cw() {
        Log.i("nu.cliffords.radioActivity","mo1752cw");
        if (this.mRadioService != null) {
            return this.mRadioService.mo1845js();
        }
        return 0;
    }

    /* renamed from: cx */
    public boolean mo1753cx() {
        Log.i("nu.cliffords.radioActivity","mo1753cx");
        //TODO: return mo1715dm() ^ 1;
        boolean retBool = !mo1715dm();
        Log.i("nu.cliffords.radioActivity","mo1753cx returns " + retBool);
        return retBool;
    }

    /* renamed from: cy */
    public boolean mo1754cy() {
        Log.i("nu.cliffords.radioActivity","mo1754cy");
        return mo1715dm();
    }

    /* renamed from: ck */
    public void mo1740ck() {
        Log.i("nu.cliffords.radioActivity","mo1740ck");
        if (this.mRadioService != null) {
            this.mRadioService.mo1829ja();
        }
        mo1765bz();
    }

    /* renamed from: cj */
    public void mo1739cj() {
        Log.i("nu.cliffords.radioActivity","mo1739cj");
        if (this.mRadioService != null) {
            this.mRadioService.mo1828ix();
        }
    }

    /* renamed from: df */
    public void mo1761df(int i) {
        Log.i("nu.cliffords.radioActivity","mo1761df - movebar something?");
        if (this.mRadioService != null) {
            this.mRadioService.moveBar(i);
            this.mRadioService.mo1870ks();
        }
    }

    /* renamed from: ci */
    public void sendActiveBroadcast() {
        Log.i("nu.cliffords.radioActivity","sendActiveBroadcast");
        sendBroadcast(new Intent("com.microntek.active"));
    }

    /* renamed from: cv */
    public String mo1751cv(int i) {
        Log.i("nu.cliffords.radioActivity","mo1751cv("+i+")");
        if (this.mRadioService != null) {
            return this.mRadioService.mo1844jr(i);
        }
        return "";
    }

    /* renamed from: cg */
    public int getRadioBand() {
        Log.i("nu.cliffords.radioActivity","getRadioBand");
        if (this.mRadioService != null) {
            return this.mRadioService.getRadioBand();
        }
        return 0;
    }
}
