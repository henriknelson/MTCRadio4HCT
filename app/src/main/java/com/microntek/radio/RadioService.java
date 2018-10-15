package com.microntek.radio;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.microntek.CarManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.provider.Settings.System;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static android.microntek.IRTable.IR_AF;
import static android.microntek.IRTable.IR_AMS;
import static android.microntek.IRTable.IR_APS;
import static android.microntek.IRTable.IR_BND;
import static android.microntek.IRTable.IR_BND_SYS;
import static android.microntek.IRTable.IR_DOWN;
import static android.microntek.IRTable.IR_LOC;
import static android.microntek.IRTable.IR_N1;
import static android.microntek.IRTable.IR_N2;
import static android.microntek.IRTable.IR_N3;
import static android.microntek.IRTable.IR_N4;
import static android.microntek.IRTable.IR_N5;
import static android.microntek.IRTable.IR_N6;
import static android.microntek.IRTable.IR_NEXT;
import static android.microntek.IRTable.IR_PREV;
import static android.microntek.IRTable.IR_PS;
import static android.microntek.IRTable.IR_RADIO;
import static android.microntek.IRTable.IR_RADIO_BND;
import static android.microntek.IRTable.IR_SEEKDOWN;
import static android.microntek.IRTable.IR_SEEKUP;
import static android.microntek.IRTable.IR_ST;
import static android.microntek.IRTable.IR_TA;
import static android.microntek.IRTable.IR_TUNEDOWN;
import static android.microntek.IRTable.IR_TUNEUP;
import static android.microntek.IRTable.IR_UP;

public class RadioService extends Service {


    private class RadioServiceImpl extends Binder implements IRadioService {

        public boolean mo1855kc() {
            Log.i("nu.cliffords.radio","mo1855kc");
            Log.i("nu.cliffords.radio","mo1855kc returns f610ds =" + f610ds);
            return f610ds;
        }

        /* renamed from: jt */
        public int[] mo1846jt() {
            Log.i("nu.cliffords.radio","mo1846jt");
            int[] iArr = new int[30];
            for (int i = 0; i < 5; i++) {
                for (int i2 = 0; i2 < 6; i2++) {
                    iArr[(i * 6) + i2] = f594dc[i][i2];
                }
            }
            Log.i("nu.cliffords.radio","mo1846jt returns iArr =" + iArr);
            return iArr;
        }

        /* renamed from: jo */
        public int getCurrentFrequency() {
            return mCurrentFrequency;
        }

        /* renamed from: jh */
        public int getRadioBand() {
            return mRadioBand;
        }

        /* renamed from: jn */
        public int getRadioChannel() {
            return mRadioChannel;
        }

        /* renamed from: jm */
        public boolean getTaOn() {
            return taOn;
        }

        /* renamed from: ji */
        public boolean getAfOn() {
            return afOn;
        }

        /* renamed from: jk */
        public int getPty() {
            return ptyPreference;
        }

        /* renamed from: jj */
        public boolean getLocOn() {
            return locOn;
        }

        /* renamed from: jl */
        public boolean getStOn() {
            return stOn;
        }

        /* renamed from: ju */
        public boolean mo1847ju() {
            return f620ec;
        }

        /* renamed from: jy */
        public boolean mo1851jy() {
            return f622ee;
        }

        /* renamed from: jv */
        public String mo1848jv() {
            Log.i("nu.cliffords.radio","mo1848jv");
            if (f617dz != null) {
                Log.i("nu.cliffords.radio","mo1848jv returns f617dz = " + f617dz);
                return f617dz;
            }
            Log.i("nu.cliffords.radio","mo1848jv returns ''");
            return "";
        }

        /* renamed from: jx */
        public String mo1850jx() {
            Log.i("nu.cliffords.radio","mo1850jx");
            if (f619eb != null) {
                Log.i("nu.cliffords.radio","mo1850jx returns f619eb = " + f619eb);
                return f619eb;
            }
            Log.i("nu.cliffords.radio","mo1850jx returns ''");
            return "";
        }

        /* renamed from: jw */
        public int mo1849jw() {
            return f618ea;
        }

        /* renamed from: ki */
        public void moveBar(int i) {
            Log.i("nu.cliffords.radio","moveBar");
            mo1872iv();
            mCurrentFrequency = i;
            m619ft(mCurrentFrequency);
            m625fz();
            m626ga();
            m627gb();
        }

        /* renamed from: ko */
        public void setRadioTuneUp() {
            mo1872iv();
            mRadioChannel = -1;
            m584eh(false);
            setParameters("ctl_radio_tune=up");
        }

        /* renamed from: kn */
        public void setRadioTuneDown() {
            mo1872iv();
            mRadioChannel = -1;
            m584eh(false);
            setParameters("ctl_radio_tune=down");
        }

        /* renamed from: kg */
        public void setRadioSeekUp() {
            mo1872iv();
            setParameters("ctl_radio_seek=up");
        }

        /* renamed from: kh */
        public void setRadioSeekDown() {
            mo1872iv();
            setParameters("ctl_radio_seek=down");
        }

        /* renamed from: kf */
        public void setRadioSeekAuto() {
            mo1872iv();
            setParameters("ctl_radio_seek=auto");
        }

        /* renamed from: ke */
        public void mo1857ke(int radioBand, int radioChannel) {
            Log.i("nu.cliffords.radio","mo1857ke(radioBand="+radioBand+",radioChannel="+radioChannel+")");
            if (!f610ds) {
                mRadioBand = radioBand;
                mRadioChannel = radioChannel;
                f594dc[mRadioBand][mRadioChannel] = mCurrentFrequency;
                setRadioFrequencyPreference(mRadioBand, mRadioChannel, mCurrentFrequency);
                m623fx();
            }
        }

        /* renamed from: kk */
        public void mo1863kk(int radioBand, int radioChannel) {
            setRadioBandAndChannel(radioBand, radioChannel);
        }

        /* renamed from: kl */
        public void setChannel(int i) { setRadioBandAndChannel(mRadioBand, i); }

        /* renamed from: kd */
        public void startSearch() {
            Log.i("nu.cliffords.radio","startSearch");
            if (f608dq) {
                mo1872iv();
                m622fw(false);
                m621fv(false);
                return;
            }
            m622fw(true);
            m621fv(false);
            mo1871iu();
        }

        /* renamed from: iv */
        public void mo1872iv() {
            Log.i("nu.cliffords.radio","mo1872iv");
            if (f608dq) {
                f614dw.removeCallbacks(f580cn);
                f608dq = false;
                m588em(true);
                m622fw(false);
            }
        }

        /* renamed from: iu */
        public void mo1871iu() {
            Log.i("nu.cliffords.radio","mo1871iu");
            if (!f608dq) {
                if (f610ds) {
                    setRadioSeekAuto();
                }
                f608dq = true;
                if (m600ez()) {
                    f607dp = 0;
                } else {
                    f607dp = 18;
                }
                f631en = 0;
                f614dw.post(f580cn);
            }
        }

        /* renamed from: jg */
        public void mo1833jg() {
            Log.i("nu.cliffords.radio","mo1833jg - switchTaOn?");
            if (m600ez() && supportsRds) {
                //TODO: taOn ^= 1;
                taOn = !taOn;
                setTaPreference(taOn);
                setParameters("ctl_radio_ta=" + (taOn ? 1 : 0));
                m624fy();
            }
        }

        /* renamed from: iw */
        public void mo1827iw() {
            Log.i("nu.cliffords.radio","mo1827iw - switchAfOn?");
            if (m600ez() && supportsRds) {
                //TODO: afOn ^= 1;
                afOn = !afOn;
                setAfPreference(afOn);
                setParameters("ctl_radio_af=" + (afOn ? 1 : 0));
                m624fy();
            }
        }

        /* renamed from: jd */
        public void mo1830jd() {
            Log.i("nu.cliffords.radio","mo1830jd - switchLocOn?");
            if (m600ez()) {
                //TODO: locOn ^= 1;
                locOn = !locOn;
                setLocPreference(locOn);
                setParameters("ctl_radio_loc=" + (locOn ? 1 : 0));
                m624fy();
            }
        }

        /* renamed from: jf */
        public void mo1832jf() {
            Log.i("nu.cliffords.radio","mo1832jf - switchStOn?");
            if (m600ez()) {
                //TODO: stOn ^= 1;
                stOn = !stOn;
                setStPreference(stOn);
                setParameters("ctl_radio_st=" + (stOn ? 1 : 0));
                m627gb();
            }
        }

        /* renamed from: je */
        public void mo1831je(int i) {
            Log.i("nu.cliffords.radio","mo1831je - setPtyPreference?");
            if (m600ez() && supportsRds && ptyPreference != i) {
                ptyPreference = i;
                setPtyPreference(ptyPreference);
                setParameters("ctl_radio_pty=" + ptyPreference);
                m624fy();
            }
        }

        /* renamed from: iz */
        public void previousBand() {
            Log.i("nu.cliffords.radio","previousBand");
            mRadioBand = (mRadioBand + 4) % 5;
            setRadioBandAndChannel(mRadioBand, mRadioChannel);
        }

        /* renamed from: iy */
        public void nextBand() {
            Log.i("nu.cliffords.radio","nextBand");
            mRadioBand = (mRadioBand + 1) % 5;
            setRadioBandAndChannel(mRadioBand, mRadioChannel);
        }

        /* renamed from: ja */
        public void mo1829ja() {
            Log.i("nu.cliffords.radio","mo1829ja");
            mo1872iv();
            if (m600ez()) {
                f587cu = mRadioBand;
                f592cz = mRadioChannel;
                if (f586ct >= 0) {
                    setRadioBandAndChannel(f586ct, f591cy);
                    return;
                } else {
                    setRadioBandAndChannel(3, 0);
                    return;
                }
            }
            f586ct = mRadioBand;
            f591cy = mRadioChannel;
            if (f587cu >= 0) {
                setRadioBandAndChannel(f587cu, f592cz);
            } else {
                setRadioBandAndChannel(0, 0);
            }
        }

        /* renamed from: jb */
        public void nextChannel() {
            Log.i("nu.cliffords.radio","nextChannel");
            if (mRadioChannel == -1) {
                mRadioChannel = 0;
            } else {
                mRadioChannel = (mRadioChannel + 1) % 6;
                if (mRadioChannel == 0) {
                    if (m600ez()) {
                        mRadioBand = (mRadioBand + 1) % 3;
                    } else {
                        mRadioBand = (mRadioBand % 2) + 3;
                    }
                }
            }
            setRadioBandAndChannel(mRadioBand, mRadioChannel);
        }

        /* renamed from: jc */
        public void previousChannel() {
            Log.i("nu.cliffords.radio","previousChannel");
            if (mRadioChannel == -1) {
                mRadioChannel = 5;
            } else {
                mRadioChannel = (mRadioChannel + 5) % 6;
                if (mRadioChannel == 5) {
                    if (m600ez()) {
                        mRadioBand = (mRadioBand + 2) % 3;
                    } else {
                        mRadioBand = (mRadioBand % 2) + 3;
                    }
                }
            }
            setRadioBandAndChannel(mRadioBand, mRadioChannel);
        }

        /* renamed from: jp */
        public int mo1842jp() {
            Log.i("nu.cliffords.radio","mo1842jp");
            int i;
            if (m600ez()) {
                i = 0;
            } else {
                i = 1;
            }
            Log.i("nu.cliffords.radio","mo1842jp returns f59dd[i][1] = " + f595dd[i][1]);
            return f595dd[i][1];
        }

        /* renamed from: jq */
        public int mo1843jq() {
            Log.i("nu.cliffords.radio","mo1843jq");
            return f595dd[m600ez() ? 0 : 1][0];
        }

        /* renamed from: js */
        public int mo1845js() {
            Log.i("nu.cliffords.radio","mo1845js");
            return f595dd[m600ez() ? 0 : 1][2];
        }

        /* renamed from: ix */
        public void mo1828ix() {
            Log.i("nu.cliffords.radio","mo1828ix");
            mo1872iv();
            if (mRadioBand == 4) {
                mRadioBand = 3;
            } else if (mRadioBand == 3) {
                mRadioBand = 4;
            } else if (mRadioBand == 2) {
                mRadioBand = 0;
            } else {
                mRadioBand = mRadioBand + 1;
            }
            setRadioBandAndChannel(mRadioBand, mRadioChannel);
        }

        /* renamed from: ka */
        public boolean mo1853ka() {
            Log.i("nu.cliffords.radio","mo1853ka");
            return m600ez();
        }

        /* renamed from: kb */
        public boolean mo1854kb() {
            Log.i("nu.cliffords.radio","mo1854kb");
            return (m600ez() && supportsRds) ? f624eg : false;
        }

        /* renamed from: jr */
        public String mo1844jr(int i) {
            Log.i("nu.cliffords.radio","mo1844jr");
            if (f624eg) {
                Log.i("nu.cliffords.radio","mo1844jr returns f624eg = " + f624eg);
                return mo1782eq(i);
            }
            Log.i("nu.cliffords.radio","mo1844jr returns ''");
            return "";
        }

        /* renamed from: kj */
        public void mo1862kj(int i) {
            Log.i("nu.cliffords.radio","mo1862kj");
            if (f630em) {
                f630em = false;
                if (i != 0) {
                    mo1864km(i);
                } else if (mResetFlag) {
                    mResetFlag = false;
                    m619ft(mCurrentFrequency);
                    m625fz();
                    m623fx();
                    m626ga();
                    m627gb();
                }
            }
        }

        /* renamed from: km */
        public void mo1864km(int i) {
            Log.i("nu.cliffords.radio","mo1864km");
            mRadioChannel = -1;
            mCurrentFrequency = i;
            m619ft(mCurrentFrequency);
            if (mCurrentFrequency > 10000000) {
                if (m599ey()) {
                    mRadioBand = 0;
                }
            } else if (m600ez()) {
                mRadioBand = 3;
            }
            m623fx();
            m625fz();
            m626ga();
            m627gb();
            setRadioBandAndChannelPreference(mRadioBand, mRadioChannel);
        }

        /* renamed from: kq */
        public void setCallbackListener(ServiceCallback serviceCallback) {
            Log.i("nu.cliffords.radio","setCallbackListener");
            mServiceCallback = serviceCallback;
        }

        /* renamed from: jz */
        public void mo1852jz() {
            Log.i("nu.cliffords.radio","mo1852jz - startRadio?");
            m598ex();
        }

        /* renamed from: kp */
        public void mo1867kp(boolean z) {
            Log.i("nu.cliffords.radio","mo1867kp z is shouldRequestAudioFocus? z = " + z);
            if (z) {
                requestAudioFocus();
            } else {
                abandonAudioFocus();
            }
        }

        /* renamed from: ks */
        public void mo1870ks() {
            requestAudioFocus();
        }

        /* renamed from: kr */
        public void mo1869kr() {
            abandonAudioFocus();
        }
    }

    private static final int[][][] f575da;
    private final int f576cj = 1;
    private final int f577ck = 0;

    private BroadcastReceiver onSystemCommandReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("nu.cliffords.radio","onSystemCommandReceiver - action = " + action);
            if (action.equals("com.microntek.bootcheck")) {
                action = intent.getStringExtra("class");
                if (!action.equals("com.microntek.radio")) {
                    if (action.equals("phonecallin")) {
                        f601dj = true;
                    } else if (action.equals("phonecallout")) {
                        f601dj = false;
                    } else {
                        if (mServiceCallback != null) {
                            mServiceCallback.mo1789bl();
                        }
                        abandonAudioFocus();
                    }
                }
            } else if ((action.equals("com.microntek.finish") || action.equals("com.microntek.removetask")) && intent.getStringExtra("class").equals("com.microntek.radio")) {
                if (mServiceCallback != null) {
                    mServiceCallback.mo1789bl();
                }
                abandonAudioFocus();
            }
        }
    };
    /* renamed from: cm */
    private int f579cm = 0;
    /* renamed from: cn */
    private Runnable f580cn;
    /* renamed from: co */
    private OnAudioFocusChangeListener mOnAudioFocusChanged = new OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            Log.i("nu.cliffords.radio","mOnAudioFocusChanged - focusChange = " + focusChange);
            if (focusChange != -3) {
                if (focusChange == 1) {
                    setParameters("av_focus_gain=fm");
                    hasAudioFocus = true;
                } else if (focusChange == -2) {
                    setParameters("av_focus_loss=fm");
                    hasAudioFocus = false;
                } else if (focusChange == -1) {
                    setParameters("av_focus_loss=fm");
                    hasAudioFocus = false;
                    abandonAudioFocus();
                    if (mServiceCallback != null) {
                        mServiceCallback.mo1789bl();
                    }
                }
            }
        }
    };
    /* renamed from: cp */
    private BroadcastReceiver onRadioCommandReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("nu.cliffords.radio","onRadioCommandReceiver - action = " + action);
            if (action.equals("hct.radio.power.switch")) {
                if (mCarManager.getStringState("av_channel").equals("fm") && hasAudioFocus == false) {
                    abandonAudioFocus();
                } else {
                    requestAudioFocus();
                }
                return;
            }
            if (action.equals("hct.radio.band.prev")) {
                mRadioServiceImplementation.previousBand();
            } else if (action.equals("hct.radio.band.next")) {
                mRadioServiceImplementation.nextBand();
            } else if (action.equals("hct.radio.channel.prev")) {
                mRadioServiceImplementation.previousChannel();
            } else if (action.equals("hct.radio.channel.next")) {
                mRadioServiceImplementation.nextChannel();
            } else if (action.equals("hct.radio.channel.one")) {
                mRadioServiceImplementation.setChannel(0);
            } else if (action.equals("hct.radio.channel.two")) {
                mRadioServiceImplementation.setChannel(1);
            } else if (action.equals("hct.radio.channel.three")) {
                mRadioServiceImplementation.setChannel(2);
            } else if (action.equals("hct.radio.channel.four")) {
                mRadioServiceImplementation.setChannel(3);
            } else if (action.equals("hct.radio.channel.five")) {
                mRadioServiceImplementation.setChannel(4);
            } else if (action.equals("hct.radio.channel.six")) {
                mRadioServiceImplementation.setChannel(5);
            } else if (action.equals("hct.radio.bar.move")) {
                mRadioServiceImplementation.moveBar(intent.getIntExtra("FREQ", 0));
            } else if (action.equals("hct.radio.request.data")) {
                requestData();
                return;
            } else if (action.equals("com.microntek.request.radio")) {
                action = intent.getStringExtra("type");
                Log.i("nu.cliffords.radio","onRadioCommandReceiver - extra action type = " + action);
                if (action.equals("list")) {
                    requestRadioList();
                } else if (action.equals("state")) {
                    requestRadioState();
                }
                return;
            }
            requestAudioFocus();
        }
    };
    /* renamed from: cq */
    private SharedPreferences mSharedPreferences = null;
    /* renamed from: cr */
    private AudioManager mAudioManager = null;
    /* renamed from: cs */
    protected final String f585cs = "fm";
    /* renamed from: ct */
    private int f586ct = -1;
    /* renamed from: cu */
    private int f587cu = -1;
    /* renamed from: cv */
    private int[] f588cv = new int[]{0, 0, 0, 1, 1};
    /* renamed from: cw */
    private boolean f589cw = false;
    /* renamed from: cx */
    private Button f590cx;
    /* renamed from: cy */
    private int f591cy = 0;
    /* renamed from: cz */
    private int f592cz = 0;
    /* renamed from: db */
    private boolean hasAudioFocus = false;
    /* renamed from: dc */
    private int[][] f594dc = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{5, 6}));
    /* renamed from: dd */
    private int[][] f595dd = new int[][]{new int[]{87500000, 108000000, 50000}, new int[]{522000, 1620000, 9000}};
    /* renamed from: de */
    private int[][] f596de = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{60, 2}));
    /* renamed from: df */
    private LayoutParams mLayoutParams;
    /* renamed from: dg */
    boolean afOn = true;
    /* renamed from: dh */
    private int mArea = 0;
    /* renamed from: di */
    private int mRadioBand = 0;
    /* renamed from: dj */
    private boolean f601dj = false;
    /* renamed from: dk */
    private ServiceCallback mServiceCallback = null;
    /* renamed from: dl */
    private CarManager mCarManager = null;
    /* renamed from: dm */
    private int mRadioChannel = 0;
    /* renamed from: dn */
    private int mCurrentFrequency = 0;
    /* renamed from: do */
    boolean locOn = false;
    /* renamed from: dp */
    private int f607dp;
    /* renamed from: dq */
    private boolean f608dq = false;
    /* renamed from: dr */
    int ptyPreference = 0;
    /* renamed from: ds */
    private boolean f610ds = false;
    /* renamed from: dt */
    boolean stOn = false;
    /* renamed from: du */
    private int f612du = 0;
    /* renamed from: dv */
    boolean taOn = false;
    /* renamed from: dw */
    private Handler f614dw = new Handler(){
        public void handleMessage(Message message) {
            Log.i("nu.cliffords.radio","f614dw - handleMessage - message.what = " + message.what);
            switch (message.what) {
                case 1:
                    f621ed = false;
                    f622ee = false;
                    f616dy = 0;
                    f618ea = 0;
                    f617dz = "";
                    f619eb = "";
                    m626ga();
                    return;
                case 2:
                    if (f617dz != null && f617dz.equals("")) {
                        m610fk(mCurrentFrequency, f617dz);
                        if (!f610ds) {
                            m630ge();
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    /* renamed from: dx */
    boolean f615dx = false;
    /* renamed from: dy */
    int f616dy = 0;
    /* renamed from: dz */
    String f617dz = "";
    /* renamed from: ea */
    int f618ea;
    /* renamed from: eb */
    String f619eb = "";
    /* renamed from: ec */
    boolean f620ec;
    /* renamed from: ed */
    boolean f621ed;
    /* renamed from: ee */
    boolean f622ee;
    /* renamed from: ef */
    boolean supportsRds = false;
    /* renamed from: eg */
    boolean f624eg = false;
    /* renamed from: eh */
    private boolean mResetFlag = false;
    /* renamed from: ei */
    private int f626ei = 0;
    /* renamed from: ej */
    private int[][] f627ej = new int[][]{new int[]{0, 3}, new int[]{3, 2}};
    /* renamed from: ek */
    private int f628ek = 0;
    /* renamed from: el */
    private RadioServiceImpl mRadioServiceImplementation = new RadioServiceImpl();
    /* renamed from: em */
    private boolean f630em = false;
    /* renamed from: en */
    private int f631en;
    /* renamed from: eo */
    private View f632eo;
    /* renamed from: ep */
    private boolean f633ep = false;
    /* renamed from: eq */
    private WindowManager mWindowManager;

    static {
        int [][][]r0 = new int[8][][];
        r0[0] = new int[][]{new int[]{87500000, 90100000, 98100000, 106100000, 108000000, 87500000}, new int[]{522000, 603000, 999000, 1404000, 1620000, 522000}};
        r0[1] = new int[][]{new int[]{87500000, 90100000, 98100000, 106100000, 108000000, 87500000}, new int[]{522000, 603000, 999000, 1404000, 1620000, 522000}};
        r0[2] = new int[][]{new int[]{65000000, 67100000, 70400000, 72500000, 74000000, 65000000}, new int[]{522000, 603000, 999000, 1404000, 1620000, 522000}};
        r0[3] = new int[][]{new int[]{65000000, 67100000, 70400000, 72500000, 74000000, 65000000}, new int[]{522000, 603000, 999000, 1404000, 1620000, 522000}};
        r0[4] = new int[][]{new int[]{87500000, 90100000, 98100000, 106100000, 108000000, 87500000}, new int[]{530000, 600000, 1000000, 1400000, 1710000, 530000}};
        r0[5] = new int[][]{new int[]{87500000, 90100000, 98100000, 106100000, 107900000, 87500000}, new int[]{530000, 600000, 1000000, 1400000, 1710000, 530000}};
        r0[6] = new int[][]{new int[]{76000000, 76400000, 85600000, 87000000, 90000000, 76000000}, new int[]{522000, 603000, 999000, 1404000, 1629000, 522000}};
        r0[7] = new int[][]{new int[]{87500000, 90100000, 98100000, 106100000, 108000000, 87500000}, new int[]{522000, 603000, 999000, 1404000, 1710000, 522000}};
        f575da = r0;
    }

    /* renamed from: el */
    private void requestAudioFocus() {
        Log.i("nu.cliffords.radio","requestAudioFocus");
        this.mAudioManager.requestAudioFocus(this.mOnAudioFocusChanged, 3, 1);
        if (!this.hasAudioFocus) {
            setParameters("av_focus_gain=fm");
            this.hasAudioFocus = true;
        }
        setParameters("av_channel_enter=fm");
        notifyRadioTurnedOn();
    }

    /* renamed from: ek */
    private void abandonAudioFocus() {
        Log.i("nu.cliffords.radio","abandonAudioFocus");
        this.mAudioManager.abandonAudioFocus(this.mOnAudioFocusChanged);
        if (this.hasAudioFocus) {
            this.hasAudioFocus = false;
            setParameters("av_focus_loss=fm");
        }
        setParameters("av_channel_exit=fm");
        notifyRadioTurnedOff();
    }

    private void notifyRadioTurnedOn() {
        Log.i("nu.cliffords.radio","notifyRadioTurnedOn");
        Intent intent = new Intent("com.android.MTClauncher.action.INSTALL_WIDGETS");
        intent.putExtra("myWidget.action", 10520);
        intent.putExtra("myWidget.packageName", "com.microntek.radio");
        sendBroadcast(intent);
        intent = new Intent("com.microntek.canbusdisplay");
        intent.putExtra("type", "radio-on");
        sendBroadcast(intent);
        requestData();
    }

    private void notifyRadioTurnedOff() {
        Log.i("nu.cliffords.radio","notifyRadioTurnedOff");
        Intent intent = new Intent("com.android.MTClauncher.action.INSTALL_WIDGETS");
        intent.putExtra("myWidget.action", 10521);
        intent.putExtra("myWidget.packageName", "com.microntek.radio");
        sendBroadcast(intent);
        intent = new Intent("com.microntek.canbusdisplay");
        intent.putExtra("type", "radio-off");
        sendBroadcast(intent);
        m618fs(0);
    }

    private void setParameters(String str) {
        Log.i("nu.cliffords.radio","setParameters("+str+")");
        if (this.mCarManager != null) {
            this.mCarManager.setParameters(str);
        }
    }

    /* renamed from: es */
    private String getParameters(String str) {
        if(BuildConfig.DEBUG){
            if (str.equals("cfg_rds=")) { return "1"; }
            if (str.equals("cfg_radio_area=")) { return "1"; }
        }
        Log.i("nu.cliffords.radio","getParameters("+str+")");
        if (this.mCarManager != null) {
            String retParams = this.mCarManager.getParameters(str);
            Log.i("nu.cliffords.radio","getParameters("+str+") returns " + retParams);
            return retParams;
        }
        return null;
    }

    /* renamed from: ef */
    private void handleKeyPress(int i) {
        Log.i("nu.cliffords.radio","handleKeyPress("+i+")");
        if (!this.f601dj) {
            boolean equals = ((RunningTaskInfo) ((ActivityManager) getSystemService(Service.ACTIVITY_SERVICE)).getRunningTasks(40).get(0)).topActivity.getPackageName().equals("com.microntek.radio");
            boolean equals2 = SystemProperties.get("ro.product.customer.sub").equals("KLD25");
            if (this.hasAudioFocus) {
                switch (i) {
                    case IR_BND_SYS:
                    case IR_BND:
                    case IR_RADIO_BND:
                        this.mRadioServiceImplementation.nextBand();
                        m624fy();
                        break;
                    case IR_UP:
                    case IR_PREV:
                        if (equals || !equals2) {
                            this.mRadioServiceImplementation.previousChannel();
                            break;
                        }
                        break;
                    case IR_DOWN:
                    case IR_NEXT:
                        if (equals || !equals2) {
                            this.mRadioServiceImplementation.nextChannel();
                            break;
                        }
                        break;
                    case IR_SEEKDOWN:
                        this.mRadioServiceImplementation.setRadioSeekDown();
                        break;
                    case IR_SEEKUP:
                        this.mRadioServiceImplementation.setRadioSeekUp();
                        break;
                    case IR_N1:
                        this.mRadioServiceImplementation.setChannel(0);
                        break;
                    case IR_N2:
                        this.mRadioServiceImplementation.setChannel(1);
                        break;
                    case IR_N3:
                        this.mRadioServiceImplementation.setChannel(2);
                        break;
                    case IR_N4:
                        this.mRadioServiceImplementation.setChannel(3);
                        break;
                    case IR_N5:
                        this.mRadioServiceImplementation.setChannel(4);
                        break;
                    case IR_N6:
                        this.mRadioServiceImplementation.setChannel(5);
                        break;
                    case IR_RADIO:
                        if (m597ew()) {
                            this.mRadioServiceImplementation.nextBand();
                            m624fy();
                            break;
                        }
                        break;
                    case IR_APS:
                        this.mRadioServiceImplementation.startSearch();
                        break;
                    case IR_TUNEDOWN:
                        this.mRadioServiceImplementation.setRadioTuneDown();
                        break;
                    case IR_TUNEUP:
                        this.mRadioServiceImplementation.setRadioTuneUp();
                        break;
                    case IR_PS:
                        break;
                    case IR_AF:
                        this.mRadioServiceImplementation.mo1827iw();
                        break;
                    case IR_TA:
                        this.mRadioServiceImplementation.mo1833jg();
                        break;
                    case IR_AMS:
                        this.mRadioServiceImplementation.setRadioSeekAuto();
                        break;
                    case IR_ST:
                        this.mRadioServiceImplementation.mo1832jf();
                        m624fy();
                        break;
                    case IR_LOC:
                        this.mRadioServiceImplementation.mo1830jd();
                        break;
                    default:
                        return;
                }
                requestAudioFocus();
            }
        }
    }

    /* renamed from: eg */
    private void m583eg(Bundle bundle) {
        boolean z = true;
        String string = bundle.getString("type");
        Log.i("nu.cliffords.radio","m583eg - bundle type= " + string);
        int i;
        int i2;
        int i3;
        byte[] byteArray;
        if (string.equals("seek_start")) {
            this.f612du = 0;
            this.f610ds = true;
            m621fv(true);
            m584eh(false);
            this.mRadioChannel = -1;
            m623fx();
            m625fz();
            m626ga();
            m627gb();
            setRadioBandAndChannelPreference(this.mRadioBand, this.mRadioChannel);
        } else if (string.equals("seek_end")) {
            this.f610ds = false;
            m621fv(false);
            m584eh(true);
        } else if (string.equals("seek_found")) {
            this.f612du = bundle.getInt("strength");
            m623fx();
            m626ga();
            m627gb();
            setCurrentFrequencyPreference(this.mCurrentFrequency);
            m584eh(true);
        } else if (string.equals("seek_start_auto")) {
            this.f612du = 0;
            this.f610ds = true;
            m621fv(true);
            i = m600ez() ? 0 : 1;
            this.mCurrentFrequency = this.f595dd[i][0];
            this.mRadioBand = this.f627ej[i][0];
            this.mRadioChannel = -1;
            this.f626ei = 0;
            this.f579cm = 0;
            for (i2 = 0; i2 < 60; i2++) {
                int i4 = 0;
                while (i4 < 2) {
                    try {
                        this.f596de[i2][i4] = 0;
                        i4++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            this.f628ek = this.f627ej[i][1] * 6;
            m584eh(false);
            m623fx();
            m625fz();
            m626ga();
            m627gb();
            setRadioBandAndChannelPreference(this.mRadioBand, this.mRadioChannel);
        } else if (string.equals("seek_end_auto")) {
            int i5 = -1;
            this.f610ds = false;
            m621fv(false);
            if (this.f579cm > this.f628ek) {
                int[] iArr = new int[2];
                Boolean valueOf;
                do {
                    valueOf = Boolean.valueOf(false);
                    i = 0;
                    while (i < this.f579cm) {
                        if (i != this.f579cm - 1 && this.f596de[i + 1][0] < this.f596de[i][0]) {
                            valueOf = Boolean.valueOf(true);
                            iArr[0] = this.f596de[i][0];
                            iArr[1] = this.f596de[i][1];
                            this.f596de[i][0] = this.f596de[i + 1][0];
                            this.f596de[i][1] = this.f596de[i + 1][1];
                            this.f596de[i + 1][0] = iArr[0];
                            this.f596de[i + 1][1] = iArr[1];
                        }
                        i++;
                    }
                } while (valueOf.booleanValue());
                iArr = new int[this.f628ek];
                for (i = 0; i < this.f628ek; i++) {
                    iArr[i] = this.f596de[(this.f579cm - this.f628ek) + i][1];
                }
                Arrays.sort(iArr);
                for (i = 0; i < this.f628ek; i++) {
                    i2 = i / 6;
                    int[][] iArr2 = this.f627ej;
                    if (m600ez()) {
                        i3 = 0;
                    } else {
                        i3 = 1;
                    }
                    i3 = iArr2[i3][0] + i2;
                    this.f594dc[i3][i % 6] = iArr[i];
                    setRadioFrequencyPreference(i3, i % 6, iArr[i]);
                }
            }
            m584eh(true);
            int[][] iArr3 = this.f627ej;
            if (m600ez()) {
                i5 = 0;
            }
            setRadioBandAndChannel(iArr3[i5][0], 0);
        } else if (string.equals("seek_found_auto")) {
            this.f612du = bundle.getInt("strength");
            if (this.f626ei < this.f627ej[m600ez() ? 0 : 1][1] * 6) {
                i3 = this.f626ei / 6;
                int[][] iArr4 = this.f627ej;
                if (m600ez()) {
                    i = 0;
                } else {
                    i = 1;
                }
                i = iArr4[i][0] + i3;
                this.f594dc[i][this.f626ei % 6] = this.mCurrentFrequency;
                setRadioFrequencyPreference(i, this.f626ei % 6, this.mCurrentFrequency);
                this.mRadioChannel = this.f626ei % 6;
                this.mRadioBand = i;
            } else {
                i = m600ez() ? 2 : 4;
                this.f594dc[i][5] = this.mCurrentFrequency;
                setRadioFrequencyPreference(i, 5, this.mCurrentFrequency);
                this.mRadioChannel = 5;
                this.mRadioBand = i;
            }
            this.f596de[this.f626ei][0] = this.f612du;
            this.f596de[this.f626ei][1] = this.mCurrentFrequency;
            if (this.f626ei < 59) {
                this.f626ei++;
            }
            this.f579cm = this.f626ei;
            if (this.f626ei >= this.f628ek) {
                if (this.mCurrentFrequency >= (m600ez() ? 108000000 : 1620000)) {
                    int[][] iArr5 = this.f627ej;
                    if (m600ez()) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    setRadioBandAndChannel(iArr5[i][0], 0);
                    this.f610ds = false;
                    m584eh(true);
                    return;
                }
            }
            m623fx();
        } else if (string.equals("rt")) {
            if (this.f615dx && this.f624eg == false) {
                byteArray = bundle.getByteArray("value");
                try {
                    if (this.mArea == 0) {
                        string = new String(byteArray, "gb2312").trim();
                    } else {
                        string = new String(byteArray, "iso8859-1").trim();
                    }
                    if (!(this.f617dz == null || this.f619eb.equals(string) == true)) {
                        this.f619eb = string;
                        m626ga();
                    }
                    m602fb();
                } catch (UnsupportedEncodingException e2) {
                }
            }
        } else if (string.equals("freq")) {
            this.mCurrentFrequency = bundle.getInt("value");
            m625fz();
            m623fx();
            m626ga();
            m627gb();
            if (!this.f610ds) {
                setRadioBandAndChannelPreference(this.mRadioBand, this.mRadioChannel);
                setCurrentFrequencyPreference(this.mCurrentFrequency);
                m584eh(true);
            }
        } else if (string.equals("tp")) {
            if (bundle.getInt("value") != 1) {
                z = false;
            }
            m602fb();
            if (this.f622ee != z) {
                this.f622ee = z;
                m626ga();
            }
        } else if (string.equals("ta")) {
            if (bundle.getInt("value") != 1) {
                z = false;
            }
            if (z) {
                m628gc();
            } else {
                mo1783fd();
            }
            m602fb();
            if (this.f621ed != z) {
                this.f621ed = z;
                m626ga();
            }
        } else if (string.equals("psn")) {
            if (this.f615dx && this.f624eg == false) {
                byteArray = bundle.getByteArray("value");
                try {
                    if (this.mArea == 0) {
                        string = new String(byteArray, "gb2312").trim();
                    } else {
                        string = new String(byteArray, "iso8859-1").trim();
                    }
                    if (this.f617dz == null || this.f617dz.equals(string) != false) {
                        this.f617dz = string;
                        m626ga();
                        m610fk(this.mCurrentFrequency, this.f617dz);
                        if (!this.f610ds) {
                            m630ge();
                        }
                    }
                    m602fb();
                } catch (UnsupportedEncodingException e3) {
                }
            }
        } else if (string.equals("pi")) {
            i = bundle.getInt("value");
            m602fb();
            if (this.f616dy != i) {
                this.f616dy = i;
                m626ga();
            }
        } else if (string.equals("pty")) {
            i = bundle.getInt("value");
            m602fb();
            if (this.f618ea != i) {
                this.f618ea = i;
                m626ga();
            }
        } else if (string.equals("stereo")) {
            if (bundle.getInt("value") != 1) {
                z = false;
            }
            m602fb();
            if (this.f620ec != z) {
                this.f620ec = z;
                if (!this.f610ds) {
                    m627gb();
                }
            }
        }
    }

    /* renamed from: fe */
    private void resetRadioPreferences() {
        Log.i("nu.cliffords.radio","resetRadioPreferences");
        Log.v("Radio_TAG", "prepare to reset Radio Preference !!!");
        Editor edit = this.mSharedPreferences.edit();
        edit.clear();
        edit.commit();
        int i = 0;
        while (i < 5) {
            for (int i2 = 0; i2 < 6; i2++) {
                if (this.mArea == 3 && (i == 1 || i == 2)) {
                    this.f594dc[i][i2] = f575da[4][this.f588cv[i]][i2];
                } else {
                    this.f594dc[i][i2] = f575da[this.mArea][this.f588cv[i]][i2];
                }
                edit.putInt("RadioFrequency" + ((i * 6) + i2), this.f594dc[i][i2]);
            }
            i++;
        }
        this.mRadioBand = 0;
        this.mRadioChannel = 0;
        this.mCurrentFrequency = this.f594dc[this.mRadioBand][this.mRadioChannel];
        this.stOn = true;
        this.locOn = false;
        this.ptyPreference = 0;
        if ("0".equals(getParameters("sta_function=22"))) {
            this.afOn = false;
        } else {
            this.afOn = true;
        }
        this.taOn = false;
        edit.putInt("RadioBand", this.mRadioBand);
        edit.putInt("RadioChannel", this.mRadioChannel);
        edit.putInt("CurrentFreq", this.mCurrentFrequency);
        edit.commit();
        setStPreference(this.stOn);
        setLocPreference(this.locOn);
        setPtyPreference(this.ptyPreference);
        setAfPreference(this.afOn);
        setTaPreference(this.taOn);
        setCurrentRegionPreference(this.mArea);
    }

    /* renamed from: fa */
    private void loadPreferences() {
        Log.i("nu.cliffords.radio","loadPreferences");
        this.mResetFlag = getFirstBootPreference();
        Log.v("Radio_TAG", "loadPreferencse()->mResetFlag = " + this.mResetFlag);
        try {
            this.mArea = Integer.parseInt(getParameters("cfg_radio_area="));
        } catch (Exception e) {
            this.mArea = 0;
        }
        if (this.mArea > 7 || this.mArea < 0) {
            this.mArea = 0;
        }
        if (this.mArea != getCurrentRegionPreference()) {
            Log.v("Radio_TAG", "loadPreferences()-> mArea != area , so set mResetFlag as true");
            this.mResetFlag = true;
        }
        switch (this.mArea) {
            case 0:
                this.f595dd[0][0] = 87500000;
                this.f595dd[0][1] = 108000000;
                this.f595dd[0][2] = 100000;
                this.f595dd[1][0] = 522000;
                this.f595dd[1][1] = 1620000;
                this.f595dd[1][2] = 9000;
                break;
            case 1:
                this.f595dd[0][0] = 87500000;
                this.f595dd[0][1] = 108000000;
                this.f595dd[0][2] = 50000;
                this.f595dd[1][0] = 522000;
                this.f595dd[1][1] = 1620000;
                this.f595dd[1][2] = 9000;
                break;
            case 2:
                this.f595dd[0][0] = 65000000;
                this.f595dd[0][1] = 74000000;
                this.f595dd[0][2] = 30000;
                this.f595dd[1][0] = 522000;
                this.f595dd[1][1] = 1620000;
                this.f595dd[1][2] = 9000;
                break;
            case 3:
                this.f595dd[0][0] = 65000000;
                this.f595dd[0][1] = 108000000;
                this.f595dd[0][2] = 30000;
                this.f595dd[1][0] = 522000;
                this.f595dd[1][1] = 1620000;
                this.f595dd[1][2] = 9000;
                break;
            case 4:
                this.f595dd[0][0] = 87500000;
                this.f595dd[0][1] = 108000000;
                this.f595dd[0][2] = 100000;
                this.f595dd[1][0] = 530000;
                this.f595dd[1][1] = 1710000;
                this.f595dd[1][2] = 10000;
                break;
            case 5:
                this.f595dd[0][0] = 87500000;
                this.f595dd[0][1] = 107900000;
                this.f595dd[0][2] = 200000;
                this.f595dd[1][0] = 530000;
                this.f595dd[1][1] = 1710000;
                this.f595dd[1][2] = 10000;
                break;
            case 6:
                this.f595dd[0][0] = 76000000;
                this.f595dd[0][1] = 90000000;
                this.f595dd[0][2] = 100000;
                this.f595dd[1][0] = 522000;
                this.f595dd[1][1] = 1629000;
                this.f595dd[1][2] = 9000;
                break;
            case 7:
                this.f595dd[0][0] = 87500000;
                this.f595dd[0][1] = 108000000;
                this.f595dd[0][2] = 100000;
                this.f595dd[1][0] = 522000;
                this.f595dd[1][1] = 1710000;
                this.f595dd[1][2] = 9000;
                break;
        }
        try {
            boolean isRds;
            if (Integer.parseInt(getParameters("cfg_rds=")) != 0) {
                isRds = true;
            } else {
                isRds = false;
            }
            this.supportsRds = isRds;
        } catch (Exception e2) {
            this.supportsRds = false;
        }
        if (this.mResetFlag) {
            this.f630em = true;
            resetRadioPreferences();
            return;
        }
        this.stOn = getStPreference();
        this.locOn = getLocPreference();
        if (this.supportsRds) {
            this.afOn = getAfPreference();
            this.taOn = getTaPreference();
            this.ptyPreference = getPtyPreference();
        } else {
            this.afOn = false;
            this.taOn = false;
            this.ptyPreference = 0;
        }
        int i = 0;
        while (i < 5) {
            for (int i2 = 0; i2 < 6; i2++) {
                if (this.mArea == 3 && (i == 1 || i == 2)) {
                    this.f594dc[i][i2] = this.mSharedPreferences.getInt("RadioFrequency" + ((i * 6) + i2), f575da[4][this.f588cv[i]][i2]);
                } else {
                    this.f594dc[i][i2] = this.mSharedPreferences.getInt("RadioFrequency" + ((i * 6) + i2), f575da[this.mArea][this.f588cv[i]][i2]);
                }
            }
            i++;
        }
        this.mRadioBand = this.mSharedPreferences.getInt("RadioBand", 0);
        this.mRadioChannel = this.mSharedPreferences.getInt("RadioChannel", 0);
        if (this.mRadioChannel != -1) {
            this.mCurrentFrequency = this.f594dc[this.mRadioBand][this.mRadioChannel];
        } else {
            this.mCurrentFrequency = this.mSharedPreferences.getInt("CurrentFreq", this.f594dc[0][0]);
        }
    }

    /* renamed from: ex */
    private void m598ex() {
        Log.i("nu.cliffords.radio","m598ex - setConfiguration?");
        int i;
        StringBuilder append;
        int i2 = 1;
        if (this.mServiceCallback != null) {
            this.f624eg = this.mServiceCallback.mo1792bo();
        } else {
            this.f624eg = false;
        }
        boolean z = this.supportsRds & this.f624eg;
        if (this.supportsRds != z) {
            StringBuilder append2 = new StringBuilder().append("ctl_radio_rds=");
            if (z) {
                i = 1;
            } else {
                i = 0;
            }
            setParameters(append2.append(i).toString());
        }
        if (z) {
            append = new StringBuilder().append("ctl_radio_af=");
            if (this.afOn) {
                i = 1;
            } else {
                i = 0;
            }
            setParameters(append.append(i).toString());
            append = new StringBuilder().append("ctl_radio_ta=");
            if (this.taOn) {
                i = 1;
            } else {
                i = 0;
            }
            setParameters(append.append(i).toString());
            setParameters("ctl_radio_pty=" + this.ptyPreference);
        }
        append = new StringBuilder().append("ctl_radio_loc=");
        if (this.locOn) {
            i = 1;
        } else {
            i = 0;
        }
        setParameters(append.append(i).toString());
        StringBuilder append3 = new StringBuilder().append("ctl_radio_st=");
        if (!this.stOn) {
            i2 = 0;
        }
        setParameters(append3.append(i2).toString());
        setParameters("ctl_radio_frequency=" + (this.mCurrentFrequency / 1000));
        this.f580cn = new Runnable() {
            @Override
            public void run() {
                if (f608dq) {
                    RadioService radioService;
                    if (f631en == 10) {
                        f631en = 0;
                        f607dp = f607dp + 1;
                        if (f607dp == 18 || f607dp == 30) {
                            if (m600ez()) {
                                setRadioBandAndChannel(0, 0);
                            } else {
                                setRadioBandAndChannel(3, 0);
                            }
                            f608dq = false;
                            m622fw(false);
                            return;
                        }
                    }
                    if (f631en == 0) {
                        setRadioBandAndChannel(f607dp / 6, f607dp % 6);
                    }
                    f631en = f631en + 1;
                    if ((f631en & 1) != 0 || f631en == 10) {
                        m588em(true);
                    } else {
                        m588em(false);
                    }
                    f614dw.postDelayed(f580cn, 500);
                }
            }
        };
    }

    /* renamed from: gd */
    private void setRadioBandAndChannel(int radioBand, int radioChannel) {
        Log.i("nu.cliffords.radio","setRadioBandAndChannel");
        this.mRadioBand = radioBand;
        this.mRadioChannel = radioChannel;
        if (this.mRadioChannel < 0 || this.mRadioChannel > 5) {
            this.mRadioChannel = 0;
        }
        this.mCurrentFrequency = this.f594dc[this.mRadioBand][this.mRadioChannel];
        m619ft(this.mCurrentFrequency);
        m623fx();
        m625fz();
        m626ga();
        m627gb();
        setRadioBandAndChannelPreference(this.mRadioBand, this.mRadioChannel);
    }

    /* renamed from: fz */
    private void m625fz() {
        Log.i("nu.cliffords.radio","m625fz");
        if (this.mServiceCallback != null) {
            this.mServiceCallback.mo1797bt();
        }
        requestData();
    }

    /* renamed from: fy */
    private void m624fy() {
        Log.i("nu.cliffords.radio","m624fy");
        if (this.mServiceCallback != null) {
            this.mServiceCallback.mo1796bs();
        }
    }

    /* renamed from: fx */
    private void m623fx() {
        Log.i("nu.cliffords.radio","m623fx");
        if (this.mServiceCallback != null) {
            this.mServiceCallback.mo1795br();
        }
    }

    /* renamed from: ge */
    private void m630ge() {
        Log.i("nu.cliffords.radio","m630ge");
        if (this.mServiceCallback != null) {
            this.mServiceCallback.mo1800bw();
        }
    }

    /* renamed from: gb */
    private void m627gb() {
        if (this.mServiceCallback != null) {
            this.mServiceCallback.mo1799bv();
        }
    }

    /* renamed from: ga */
    private void m626ga() {
        if (this.mServiceCallback != null) {
            this.mServiceCallback.mo1798bu();
        }
    }

    /* renamed from: fw */
    private void m622fw(boolean z) {
        if (this.mServiceCallback != null) {
            this.mServiceCallback.mo1794bq(z);
        }
    }

    /* renamed from: fv */
    private void m621fv(boolean z) {
        if (this.mServiceCallback != null) {
            this.mServiceCallback.mo1793bp(z);
        }
    }

    /* renamed from: em */
    private void m588em(boolean z) {
        if (this.mServiceCallback != null) {
            this.mServiceCallback.mo1790bm(z);
        }
    }

    /* renamed from: ew */
    private boolean m597ew() {
        if (this.mServiceCallback != null) {
            return this.mServiceCallback.mo1791bn();
        }
        return true;
    }

    /* renamed from: fg */
    private void setCurrentRegionPreference(int i) {
        Editor edit = this.mSharedPreferences.edit();
        edit.putInt("CurrentRegion", i);
        edit.commit();
    }

    /* renamed from: ep */
    private boolean getFirstBootPreference() {
        int i = System.getInt(getContentResolver(), "microntek.firstboot", 128);
        Log.v("Radio_TAG", "getFirstBootPreference()->firstboot = " + i);
        if ((i & 129) == 129) {
            return false;
        }
        System.putInt(getContentResolver(), "microntek.firstboot", 129);
        return true;
    }

    private int getCurrentRegionPreference() {
        if (this.mSharedPreferences != null) {
            if(BuildConfig.DEBUG){
                return 1;
            }
            return this.mSharedPreferences.getInt("CurrentRegion", -1);
        }
        return 0;
    }

    private void setStPreference(boolean stOn) {
        Editor edit = this.mSharedPreferences.edit();
        edit.putBoolean("ston", stOn);
        edit.commit();
    }

    private boolean getStPreference() {
        if (this.mSharedPreferences != null) {
            return this.mSharedPreferences.getBoolean("ston", true);
        }
        return true;
    }

    private void setLocPreference(boolean locOn) {
        Editor edit = this.mSharedPreferences.edit();
        edit.putBoolean("locon", locOn);
        edit.commit();
    }

    private boolean getLocPreference() {
        if (this.mSharedPreferences != null) {
            return this.mSharedPreferences.getBoolean("locon", false);
        }
        return false;
    }

    private void setAfPreference(boolean afOn) {
        Editor edit = this.mSharedPreferences.edit();
        edit.putBoolean("afon", afOn);
        edit.commit();
    }

    private boolean getAfPreference() {
        if (this.mSharedPreferences != null) {
            return this.mSharedPreferences.getBoolean("afon", true);
        }
        return true;
    }

    private void setTaPreference(boolean taOn) {
        Editor edit = this.mSharedPreferences.edit();
        edit.putBoolean("taon", taOn);
        edit.commit();
    }

    private boolean getTaPreference() {
        if (this.mSharedPreferences != null) {
            return this.mSharedPreferences.getBoolean("taon", true);
        }
        return true;
    }

    private void setPtyPreference(int i) {
        Editor edit = this.mSharedPreferences.edit();
        edit.putInt("pty", i);
        edit.commit();
    }

    private int getPtyPreference() {
        if (this.mSharedPreferences != null) {
            return this.mSharedPreferences.getInt("pty", 0);
        }
        return 0;
    }

    private void setRadioFrequencyPreference(int i, int i2, int i3) {
        Editor edit = this.mSharedPreferences.edit();
        edit.putInt("RadioFrequency" + ((i * 6) + i2), i3);
        edit.commit();
    }

    private void setRadioBandAndChannelPreference(int radioBand, int radioChannel) {
        Editor edit = this.mSharedPreferences.edit();
        edit.putInt("RadioBand", radioBand);
        edit.putInt("RadioChannel", radioChannel);
        edit.commit();
    }

    /* renamed from: fj */
    private void setCurrentFrequencyPreference(int currentFrequency) {
        Editor edit = this.mSharedPreferences.edit();
        edit.putInt("CurrentFreq", currentFrequency);
        edit.commit();
    }

    /* renamed from: eq */
    public String mo1782eq(int i) {
        return this.mSharedPreferences.getString("" + i, "");
    }

    /* renamed from: fk */
    private void m610fk(int i, String str) {
        String str2 = "" + i;
        Editor edit = this.mSharedPreferences.edit();
        if (str.equals("")) {
            edit.remove(str2);
        } else {
            edit.putString(str2, str);
        }
        edit.commit();
    }

    /* renamed from: ez */
    private boolean m600ez() {
        Log.i("nu.cliffords.radio","m600ez f588cv[this.mRadioBand] equals 0");
        return this.f588cv[this.mRadioBand] == 0;
    }

    /* renamed from: ey */
    private boolean m599ey() {
        return this.f588cv[this.mRadioBand] == 1;
    }

    /* renamed from: fb */
    private void m602fb() {
        Log.i("nu.cliffords.radio","m602fb");
        this.f614dw.removeMessages(1);
        this.f614dw.sendEmptyMessageDelayed(1, 10000);
    }

    /* renamed from: ft */
    private void m619ft(int i) {
        Log.i("nu.cliffords.radio","m619ft");
        m584eh(false);
        setParameters("ctl_radio_frequency=" + (i / 1000));
    }

    /* renamed from: eh */
    private void m584eh(boolean z) {
        Log.i("nu.cliffords.radio","m584eh");
        if (!z) {
            this.f614dw.removeMessages(2);
            this.f614dw.removeMessages(1);
            this.f615dx = false;
            this.f620ec = false;
            this.f621ed = false;
            this.f622ee = false;
            this.f616dy = 0;
            this.f618ea = 0;
            this.f617dz = "";
            this.f619eb = "";
        } else if (this.f624eg && this.supportsRds == false) {
            this.f615dx = true;
            this.f614dw.removeMessages(2);
            this.f614dw.sendEmptyMessageDelayed(2, 2000);
            m602fb();
        }
    }

    public IBinder onBind(Intent intent) {
        Log.i("nu.cliffords.radio","onBind");
        this.f589cw = true;
        return this.mRadioServiceImplementation;
    }

    public boolean onUnbind(Intent intent) {
        Log.i("nu.cliffords.radio","onUnbind");
        this.f589cw = false;
        m584eh(true);
        return super.onUnbind(intent);
    }

    public void onCreate() {
        Log.i("nu.cliffords.radio","onCreate");
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.mAudioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("hct.radio.request.data");
        intentFilter.addAction("hct.radio.power.switch");
        intentFilter.addAction("hct.radio.band.prev");
        intentFilter.addAction("hct.radio.band.next");
        intentFilter.addAction("hct.radio.channel.next");
        intentFilter.addAction("hct.radio.channel.prev");
        intentFilter.addAction("hct.radio.channel.one");
        intentFilter.addAction("hct.radio.channel.two");
        intentFilter.addAction("hct.radio.channel.three");
        intentFilter.addAction("hct.radio.channel.four");
        intentFilter.addAction("hct.radio.channel.five");
        intentFilter.addAction("hct.radio.channel.six");
        intentFilter.addAction("hct.radio.bar.move");
        intentFilter.addAction("com.microntek.request.radio");
        registerReceiver(this.onRadioCommandReceiver, intentFilter);
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.microntek.bootcheck");
        intentFilter.addAction("com.microntek.removetask");
        intentFilter.addAction("com.microntek.finish");
        registerReceiver(this.onSystemCommandReceiver, intentFilter);
        this.mCarManager = new CarManager();
        this.mCarManager.attach(new Handler(){
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if ("Radio".equals((String) message.obj)) {
                    m583eg(message.getData());
                } else if ("KeyDown".equals((String) message.obj)) {
                    Bundle data = message.getData();
                    if ("key".equals(data.getString("type"))) {
                        handleKeyPress(data.getInt("value"));
                    }
                }
            }
        }, "Radio,KeyDown");
        this.f632eo = LayoutInflater.from(this).inflate(R.layout.ta, null);
        this.f590cx = (Button) this.f632eo.findViewById(R.id.btn_ta2);
        this.f590cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableTa();
                m624fy();
                mo1783fd();
            }
        });
        this.mWindowManager = (WindowManager) getSystemService(Service.WINDOW_SERVICE);
        this.mLayoutParams = new LayoutParams(-1, -1, 2010, 8, -2);
        this.mLayoutParams.gravity = 17;
        this.f630em = true;
        loadPreferences();
    }

    public void onDestroy() {
        Log.i("nu.cliffords.radio","onDestroy");
        this.mCarManager.detach();
        this.f614dw.removeCallbacksAndMessages(null);
        unregisterReceiver(this.onRadioCommandReceiver);
        unregisterReceiver(this.onSystemCommandReceiver);
    }

    @Deprecated
    public void onStart(Intent intent, int i) {
        super.onStart(intent, i);
        Log.i("nu.cliffords.radio","onStart");
        if (!this.f589cw) {
            this.f630em = true;
        }
    }

    /* renamed from: ej */
    public void disableTa() {
        Log.i("nu.cliffords.radio","disableTa");
        this.taOn = false;
        setTaPreference(this.taOn);
        setParameters("ctl_radio_ta=0");
    }

    /* renamed from: gc */
    private void m628gc() {
        Log.i("nu.cliffords.radio","m628gc");
        if (this.f633ep) {
            this.mWindowManager.updateViewLayout(this.f632eo, this.mLayoutParams);
            return;
        }
        this.mWindowManager.addView(this.f632eo, this.mLayoutParams);
        this.f633ep = true;
    }

    /* renamed from: fd */
    public void mo1783fd() {
        Log.i("nu.cliffords.radio","mo1783fd");
        if (this.f633ep) {
            this.mWindowManager.removeView(this.f632eo);
            this.f633ep = false;
        }
    }

    /* renamed from: fs */
    private void m618fs(int i) {
        Log.i("nu.cliffords.radio","m618fs("+i+") - notifyWidgetState?");
        boolean z = true;
        Intent intent = new Intent("com.microntek.radio.report");
        intent.putExtra("type", "power");
        if (i != -1) {
            String str = "state";
            if (i != 1) {
                z = false;
            }
            intent.putExtra(str, z);
        } else if (this.mCarManager.getStringState("av_channel").equals("fm")) {
            intent.putExtra("state", true);
        } else {
            intent.putExtra("state", false);
        }
        getApplicationContext().sendBroadcast(intent);
    }

    /* renamed from: fr */
    private void requestData() {
        Log.i("nu.cliffords.radio","requestData - notifyWidgetRadioData?");
        int i;
        int i2 = 0;
        Intent intent = new Intent("com.microntek.canbusdisplay");
        intent.putExtra("type", "radio");
        int length = this.f594dc.length;
        intent.putExtra("group", (length << 4) | this.mRadioBand);
        if (this.mRadioBand < length - 2) {
            intent.putExtra("fre", this.mCurrentFrequency / 10000);
        } else {
            intent.putExtra("fre", this.mCurrentFrequency / 1000);
        }
        intent.putExtra("index", (byte) this.mRadioChannel);
        getApplicationContext().sendBroadcast(intent);
        int[][] iArr = this.f595dd;
        if (m600ez()) {
            i = 0;
        } else {
            i = 1;
        }
        length = iArr[i][0];
        int[][] iArr2 = this.f595dd;
        if (m600ez()) {
            i = 0;
        } else {
            i = 1;
        }
        i = iArr2[i][1];
        iArr2 = this.f595dd;
        if (!m600ez()) {
            i2 = 1;
        }
        i2 = iArr2[i2][2];
        Intent intent2 = new Intent("com.microntek.radio.report");
        intent2.putExtra("type", "content");
        intent2.putExtra("freq", (this.mRadioBand << 28) | this.mCurrentFrequency);
        intent2.putExtra("channel", this.mRadioChannel);
        intent2.putExtra("freqmin", length);
        intent2.putExtra("freqmax", i);
        intent2.putExtra("freqstep", i2);
        intent2.putExtra("isfm", m600ez());
        getApplicationContext().sendBroadcast(intent2);
        m618fs(-1);
    }

    /* renamed from: fq */
    private void requestRadioState() {
        Log.i("nu.cliffords.radio","requestRadioState - notifyWidgetRadioState?");
        Intent intent = new Intent("com.microntek.radio.report");
        intent.putExtra("type", "state");
        if (this.mCarManager.getStringState("av_channel").equals("fm")) {
            intent.putExtra("power", true);
        } else {
            intent.putExtra("power", false);
        }
        intent.putExtra("freq", this.mCurrentFrequency);
        getApplicationContext().sendBroadcast(intent);
    }

    /* renamed from: fp */
    private void requestRadioList() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 5; i++) {
            for (int i2 = 0; i2 < 6; i2++) {
                arrayList.add("rate:" + this.f594dc[i][i2] + ",name:" + (i * 6) + "" + i2);
            }
        }
        Intent intent = new Intent("com.microntek.radio.report");
        intent.putExtra("type", "list");
        intent.putStringArrayListExtra("list", arrayList);
        getApplicationContext().sendBroadcast(intent);
    }
}
