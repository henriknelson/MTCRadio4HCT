package com.microntek.radio;

/* renamed from: com.microntek.radio.e */
public interface IRadioService {
    /* renamed from: iw */
    void mo1827iw();

    /* renamed from: ix */
    void mo1828ix();

    /* renamed from: ja */
    void mo1829ja();

    /* renamed from: jd */
    void mo1830jd();

    /* renamed from: je */
    void mo1831je(int i);

    /* renamed from: jf */
    void mo1832jf();

    /* renamed from: jg */
    void mo1833jg();

    /* renamed from: jh */
    int getRadioBand();

    /* renamed from: ji */
    boolean getAfOn();

    /* renamed from: jj */
    boolean getLocOn();

    /* renamed from: jk */
    int getPty();

    /* renamed from: jl */
    boolean getStOn();

    /* renamed from: jm */
    boolean getTaOn();

    /* renamed from: jn */
    int getRadioChannel();

    /* renamed from: jo */
    int getCurrentFrequency();

    /* renamed from: jp */
    int mo1842jp();

    /* renamed from: jq */
    int mo1843jq();

    /* renamed from: jr */
    String mo1844jr(int i);

    /* renamed from: js */
    int mo1845js();

    /* renamed from: jt */
    int[] mo1846jt();

    /* renamed from: ju */
    boolean mo1847ju();

    /* renamed from: jv */
    String mo1848jv();

    /* renamed from: jw */
    int mo1849jw();

    /* renamed from: jx */
    String mo1850jx();

    /* renamed from: jy */
    boolean mo1851jy();

    /* renamed from: jz */
    void mo1852jz();

    /* renamed from: ka */
    boolean mo1853ka();

    /* renamed from: kb */
    boolean mo1854kb();

    /* renamed from: kc */
    boolean mo1855kc();

    /* renamed from: kd */
    void startSearch();

    /* renamed from: ke */
    void mo1857ke(int radioBand, int radioChannel);

    /* renamed from: kf */
    void setRadioSeekAuto();

    /* renamed from: kg */
    void setRadioSeekUp();

    /* renamed from: kh */
    void setRadioSeekDown();

    /* renamed from: ki */
    void moveBar(int i);

    /* renamed from: kj */
    void mo1862kj(int i);

    /* renamed from: kk */
    void mo1863kk(int radioBand, int radioChannel);

    /* renamed from: km */
    void mo1864km(int i);

    /* renamed from: kn */
    void setRadioTuneDown();

    /* renamed from: ko */
    void setRadioTuneUp();

    /* renamed from: kp */
    void mo1867kp(boolean z);

    /* renamed from: kq */
    void setCallbackListener(ServiceCallback serviceCallback);

    /* renamed from: kr */
    void mo1869kr();

    /* renamed from: ks */
    void mo1870ks();
}
