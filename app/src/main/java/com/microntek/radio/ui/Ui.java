package com.microntek.radio.ui;

import android.app.Activity;
import android.os.Handler;
import android.os.SystemProperties;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.microntek.radio.OnFuncInterface;
import com.microntek.radio.R;
import com.microntek.radio.UiBase;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

//viewPager.mo1018yd = viewPager.getAdapter()
//viewPager.mo1010xo = viewPager.setCurrentItem()
//viewPager.mo1014xs = viewPager.setAdapter()
//pagerAdapter.mo1121wb = pagerAdapter.notifyDataChangeSet()

public class Ui extends UiBase implements OnClickListener {


    private class MyOnTouchListener implements OnTouchListener {
        private long f710bk;
        private boolean f711bl = false;
        private boolean f712bm = false;

        public boolean onTouch(View view, MotionEvent motionEvent) {
            Log.i("nu.cliffords.radioUi","MyOnTouchListener.onTouch");
            switch (motionEvent.getAction()) {
                case 0:
                    view.setPressed(true);
                    this.f712bm = true;
                    break;
                case 1:
                    if (this.f712bm) {
                        this.f710bk = motionEvent.getEventTime() - motionEvent.getDownTime();
                        if (this.f710bk > 100) {
                            mOnFuncInterface.sendBeepBroadcast();
                            if (f670aj) {
                                if (view.getId() == R.id.btn_next) {
                                    mOnFuncInterface.onWlgkCustomerAndNextButtonClicked();
                                } else {
                                    mOnFuncInterface.onWlgkCustomerAndPreviousButtonClicked();
                                }
                            } else if (view.getId() == R.id.btn_next) {
                                mOnFuncInterface.btnNext();
                            } else {
                                mOnFuncInterface.btnPrevious();
                            }
                        }
                        view.setPressed(false);
                        this.f712bm = false;
                        this.f710bk = 0;
                        this.f711bl = false;
                        f681k.removeCallbacks(f661a);
                        f681k.postDelayed(f661a, 2000);
                        break;
                    }
                    break;
                case 2:
                    if (this.f712bm) {
                        this.f710bk = motionEvent.getEventTime() - motionEvent.getDownTime();
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        int width = view.getWidth();
                        int height = view.getHeight();
                        if (x >= -10 && x <= width + 10 && y >= -10 && y <= height + 10) {
                            if (this.f710bk >= 1000 && this.f711bl != false) {
                                this.f711bl = true;
                                //TODO: f670aj = f670aj ^ 1;
                                f670aj = !f670aj;
                            }
                            f681k.removeCallbacks(f661a);
                            f681k.postDelayed(f661a, 2000);
                            break;
                        }
                        this.f712bm = false;
                        this.f710bk = 0;
                        this.f711bl = false;
                        view.setPressed(false);
                        break;
                    }
                    break;
                case 3:
                    view.setPressed(false);
                    this.f712bm = false;
                    break;
            }
            return true;
        }
    }

    private class FreqencyBarCallbackListener implements FreqencyBarCallbackInterface {
        public void mo1914bg(FrequencyBarView frequencyBarView) {
            Log.i("nu.cliffords.radioUi","FreqencyBarCallbackListener.mo1914bg");
        }

        /* renamed from: bh */
        public void mo1915bh(FrequencyBarView frequencyBarView) {
            Log.i("nu.cliffords.radioUi","FreqencyBarCallbackListener.mo1915bh");
        }

        /* renamed from: bf */
        public void onFrequencyChanged(FrequencyBarView frequencyBarView) {
            Log.i("nu.cliffords.radioUi","FreqencyBarCallbackListener.onFrequencyChanged");
            int ax = frequencyBarView.getFrequency();
            mOnFuncInterface.sendActiveBroadcast();
            mOnFuncInterface.mo1761df(ax);
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        public void onPageSelected(int i) {
            Log.i("nu.cliffords.radioUi","MyOnPageChangeListener.onPageSelected");
            int count = viewPager.getAdapter().getCount();
            if (i == 0) {
                viewPager.setCurrentItem(1, true);
            } else if (i == count - 1) {
                viewPager.setCurrentItem(count - 2, true);
            }
        }

        public void onPageScrolled(int i, float f, int i2) {
            Log.i("nu.cliffords.radioUi","MyOnPageChangeListener.onPageScrolled");
            mOnFuncInterface.sendActiveBroadcast();
        }

        public void onPageScrollStateChanged(int i) {
        }
    }

    private class MyOnTouchListener2 implements OnTouchListener {
        private long f714bo;
        private boolean f715bp = false;
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Log.i("nu.cliffords.radioUi","MyOnPageChangeListener2.onTouch");
            BtnChannel btnChannel = (BtnChannel) view.getParent().getParent();
            int au = btnChannel.mo1888au();
            switch (motionEvent.getAction()) {
                case 0:
                    btnChannel.setPressed(true);
                    this.f715bp = true;
                    break;
                case 1:
                    if (this.f715bp) {
                        this.f714bo = motionEvent.getEventTime() - motionEvent.getDownTime();
                        if (this.f714bo < 1500) {
                            f670aj = false;
                            mOnFuncInterface.sendBeepBroadcast();
                            mOnFuncInterface.mo1762dg(au / 6, au % 6);
                        }
                        btnChannel.setPressed(false);
                        this.f715bp = false;
                        break;
                    }
                    break;
                case 2:
                    if (this.f715bp) {
                        this.f714bo = motionEvent.getEventTime() - motionEvent.getDownTime();
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        int width = btnChannel.getWidth();
                        int height = btnChannel.getHeight();
                        if (x >= -10 && x <= width + 10 && y >= -10 && y <= height + 10) {
                            if (this.f714bo >= 1500) {
                                this.f715bp = false;
                                f670aj = false;
                                mOnFuncInterface.sendBeepBroadcast();
                                mOnFuncInterface.mo1757db(au / 6, au % 6);
                                btnChannel.setPressed(false);
                                break;
                            }
                        }
                        this.f715bp = false;
                        btnChannel.setPressed(false);
                        break;
                    }
                    break;
                case 3:
                    btnChannel.setPressed(false);
                    this.f715bp = false;
                    break;
            }
            return true;
        }
    }

    private static final int[] mFrequencyStrings = new int[]{R.string.fm1, R.string.fm2, R.string.fm3, R.string.am1, R.string.am2};
    private static int[] mChannelButtons = new int[]{R.id.btn_channel1, R.id.btn_channel2, R.id.btn_channel3, R.id.btn_channel4, R.id.btn_channel5, R.id.btn_channel6};
    private Runnable f661a;
    private MyUIPagerAdapter f662aa;
    private MyUIPagerAdapter f663ab;
    private ViewPager viewPager;
    private TextView mPtyTextView;
    private View mRdsPartView;
    String f667ag = "";
    private ImageView mStIndicatorImageView;
    private TextView mTpIndicatorImageView;
    private boolean f670aj = false;
    private MyOnTouchListener mOnNextOrPreviousButtonTouchListener;
    private TextView mFrequencyBandTextView;
    private int[] f673b = new int[5];
    private TextView mAfBtnTextView;
    private BtnChannel[] f675d = new BtnChannel[30];
    private MyButton mLocButton;
    private TextView mPtyBtnTextView;
    private MyButton mStButton;
    private TextView mTaBtnTextView;
    private MyOnTouchListener2 f680j;
    private Handler f681k = new Handler();
    private ImageView f682l;
    private TextView mLocTextView;
    private TextView mStTextView;
    private ImageView f685o;
    private View[] mChannelButtonViewArray = new View[5];
    private MyBaseAdapter mBaseAdapter;
    private boolean f688r = true;
    private FrameLayout mPtyChoiceListLayout;
    private ListView mPtyListView;
    private int mCurrentFrequency = -1;
    private FrequencyBarView mFrequencyBarView;
    private LayoutInflater mLayoutInflater;
    private List<View> f694x;
    private List<View> f695y;

    private OnLongClickListener onNextOrPreviousButtonLongClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            Log.i("nu.cliffords.radioUi","onNextOrPreviousButtonLongClickListener.onLongClick");
            switch (view.getId()) {
                case R.id.btn_prev:
                    mOnFuncInterface.btnPrevious();
                    break;
                case R.id.btn_next:
                    mOnFuncInterface.btnNext();
                    break;
            }
            return true;
        }
    };

    public Ui(Activity activity) {
        this.mActivity = activity;
        try {
            this.mOnFuncInterface = (OnFuncInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "Must implement OnFuncInterface");
        }
    }

    public int getLayout() {
        return R.layout.radio;
    }

    public void setupViews() {
        this.mActivity.findViewById(R.id.active).setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mOnFuncInterface.sendActiveBroadcast();
                return false;
            }
        });

        int i;

        this.mFrequencyBarView = (FrequencyBarView) this.mActivity.findViewById(R.id.freq_bar);
        this.mFrequencyBarView.setFrequencyBarCallbackListener(new FreqencyBarCallbackListener());
        this.mLayoutInflater = this.mActivity.getLayoutInflater();
        for (i = 0; i < this.mChannelButtonViewArray.length; i++) {
            this.mChannelButtonViewArray[i] = this.mLayoutInflater.inflate(R.layout.radio_c1, null);
        }
        this.mOnNextOrPreviousButtonTouchListener = new MyOnTouchListener();
        this.f680j = new MyOnTouchListener2();
        this.mFrequencyBandTextView = (TextView) this.mActivity.findViewById(R.id.freq_band);
        for (int i2 = 0; i2 < this.mChannelButtonViewArray.length * 6; i2++) {
            this.f675d[i2] = (BtnChannel) this.mChannelButtonViewArray[i2 / 6].findViewById(mChannelButtons[i2 % 6]);
            this.f675d[i2].findViewById(R.id.btn_channel).setOnTouchListener(this.f680j);
            this.f675d[i2].mo1889av(i2);
        }
        this.viewPager = (ViewPager) this.mActivity.findViewById(R.id.viewpagerLayout);
        this.f694x = new ArrayList();
        this.f694x.add(new View(this.mActivity));
        for (i = 0; i < 3; i++) {
            this.f694x.add(this.mChannelButtonViewArray[i]);
        }
        this.f694x.add(new View(this.mActivity));
        this.f695y = new ArrayList();
        this.f695y.add(new View(this.mActivity));
        this.f695y.add(this.mChannelButtonViewArray[3]);
        this.f695y.add(this.mChannelButtonViewArray[4]);
        this.f695y.add(new View(this.mActivity));
        this.f662aa = new MyUIPagerAdapter(this, this.f694x, false);
        this.f663ab = new MyUIPagerAdapter(this, this.f695y, true);
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mScroller");
            declaredField.setAccessible(true);
            MyScroller myScroller = new MyScroller(this, this.viewPager.getContext(), new AccelerateInterpolator());
            declaredField.set(this.viewPager, myScroller);
            myScroller.mo1910at(300);
        } catch (Exception e) {
        }
        this.viewPager.setAdapter(this.f662aa);
        this.viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        this.f661a = new Runnable() {
            @Override
            public void run() {
                f670aj = false;
            }
        };
        this.mStTextView = (TextView) this.mActivity.findViewById(R.id.st_sw);
        this.mLocTextView = (TextView) this.mActivity.findViewById(R.id.loc_sw);
        this.mStIndicatorImageView = (ImageView) this.mActivity.findViewById(R.id.stindicator);
        this.mTpIndicatorImageView = (TextView) this.mActivity.findViewById(R.id.tpindicator);
        this.mPtyTextView = (TextView) this.mActivity.findViewById(R.id.txt_pty);
        this.mStButton = (MyButton) this.mActivity.findViewById(R.id.btn_st);
        this.mLocButton = (MyButton) this.mActivity.findViewById(R.id.btn_loc);
        this.mRdsPartView = this.mActivity.findViewById(R.id.rdspart);
        this.mAfBtnTextView = (TextView) this.mActivity.findViewById(R.id.btn_af);
        this.mTaBtnTextView = (TextView) this.mActivity.findViewById(R.id.btn_ta);
        this.mPtyBtnTextView = (TextView) this.mActivity.findViewById(R.id.btn_pty);
        this.mPtyChoiceListLayout = (FrameLayout) this.mActivity.findViewById(R.id.content_list);
        this.mPtyListView = (ListView) this.mActivity.findViewById(R.id.list_pty);
        this.mStButton.setOnClickListener(this);
        this.mLocButton.setOnClickListener(this);
        this.mAfBtnTextView.setOnClickListener(this);
        this.mTaBtnTextView.setOnClickListener(this);
        this.mPtyBtnTextView.setOnClickListener(this);
        this.mBaseAdapter = new MyBaseAdapter(this, this.mActivity, getPtyListChoices());
        this.mPtyListView.setAdapter(this.mBaseAdapter);
        this.mPtyListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mOnFuncInterface.onPtySelected(position);
                    }
                });
        String customer = SystemProperties.get("ro.product.customer");
        if (customer == null || !customer.equals("WLGK")) {
            this.mActivity.findViewById(R.id.btn_prev).setOnTouchListener(this.mOnNextOrPreviousButtonTouchListener);
            this.mActivity.findViewById(R.id.btn_next).setOnTouchListener(this.mOnNextOrPreviousButtonTouchListener);
        } else {
            this.mActivity.findViewById(R.id.btn_prev).setOnClickListener(this);
            this.mActivity.findViewById(R.id.btn_next).setOnClickListener(this);
            this.mActivity.findViewById(R.id.btn_prev).setOnLongClickListener(this.onNextOrPreviousButtonLongClickListener);
            this.mActivity.findViewById(R.id.btn_next).setOnLongClickListener(this.onNextOrPreviousButtonLongClickListener);
        }
        this.mActivity.findViewById(R.id.btn_search).setOnClickListener(this);
        this.mActivity.findViewById(R.id.btn_search).setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnFuncInterface.startSearch();
                return true;
            }
        });
        this.mActivity.findViewById(R.id.btn_am).setOnClickListener(this);
        this.mActivity.findViewById(R.id.btn_fm).setOnClickListener(this);
    }

    /* renamed from: ac */
    public void mo1804ac(int i) {
        Log.i("nu.cliffords.radioUi","mo1804ac("+i+")");
        boolean z = false;
        this.mBaseAdapter.mo1904ak(i);
        this.mBaseAdapter.notifyDataSetChanged();
        if (i != 0) {
            z = true;
        }
        this.mPtyBtnTextView.setSelected(z);
    }

    /* renamed from: x */
    public void mo1824x(boolean z) {
        Log.i("nu.cliffords.radioUi","mo1824x("+z+")");
        if (z) {
            this.mRdsPartView.setVisibility(View.VISIBLE);
            this.mPtyBtnTextView.setVisibility(View.VISIBLE);
            return;
        }
        this.mRdsPartView.setVisibility(View.GONE);
        this.mPtyBtnTextView.setVisibility(View.INVISIBLE);
        this.mPtyChoiceListLayout.setVisibility(View.INVISIBLE);
    }

    /* renamed from: n */
    public void mo1814n(boolean z) {
    }

    /* renamed from: q */
    public void mo1817q(boolean z, int i) {
    }

    /* renamed from: r */
    public void setPtyChoiceListVisibility(boolean shouldBeVisible) {
        Log.i("nu.cliffords.radioUi","setPtyChoiceListVisibility(shouldBeVisible-"+shouldBeVisible+")");
        if (this.mPtyChoiceListLayout == null) {
            return;
        }
        if (shouldBeVisible) {
            this.mPtyChoiceListLayout.setVisibility(View.VISIBLE);
        } else {
            this.mPtyChoiceListLayout.setVisibility(View.INVISIBLE);
        }
    }

    /* renamed from: j */
    public void mo1810j(boolean z) {
    }

    /* renamed from: k */
    public void mo1811k(boolean z) {
    }

    /* renamed from: a */
    public void showFrequencyText(boolean showFrequency) {
        Log.i("nu.cliffords.radioUi","showFrequencyText(showFrequency-"+showFrequency+")");
        if (showFrequency) {
            ((TextView) this.mActivity.findViewById(R.id.freq)).setVisibility(View.VISIBLE);
        } else {
            ((TextView) this.mActivity.findViewById(R.id.freq)).setVisibility(View.INVISIBLE);
        }
    }

    /* renamed from: l */
    public void mo1812l(boolean z, boolean z2) {
        Log.i("nu.cliffords.radioUi","mo1812l(z-"+z+",z2-"+z2+")");
        if (z) {
            if (this.mAfBtnTextView != null) {
                this.mAfBtnTextView.setSelected(z2);
                this.mAfBtnTextView.setVisibility(View.VISIBLE);
            }
            if (this.f682l == null) {
                return;
            }
            if (z2) {
                this.f682l.setVisibility(View.VISIBLE);
                return;
            } else {
                this.f682l.setVisibility(View.INVISIBLE);
                return;
            }
        }
        if (this.mAfBtnTextView != null) {
            this.mAfBtnTextView.setSelected(false);
            this.mAfBtnTextView.setVisibility(View.INVISIBLE);
        }
        if (this.f682l != null) {
            this.f682l.setVisibility(View.INVISIBLE);
        }
    }

    /* renamed from: z */
    public void mo1826z(boolean z, boolean z2) {
        Log.i("nu.cliffords.radioUi","mo1826z(z-"+z+",z2-"+z2+")");
        if (z) {
            if (this.mTaBtnTextView != null) {
                this.mTaBtnTextView.setSelected(z2);
                this.mTaBtnTextView.setVisibility(View.VISIBLE);
            }
            if (this.f685o == null) {
                return;
            }
            if (z2) {
                this.f685o.setVisibility(View.VISIBLE);
                return;
            } else {
                this.f685o.setVisibility(View.INVISIBLE);
                return;
            }
        }
        if (this.mTaBtnTextView != null) {
            this.mTaBtnTextView.setSelected(false);
            this.mTaBtnTextView.setVisibility(View.INVISIBLE);
        }
        if (this.f685o != null) {
            this.f685o.setVisibility(View.INVISIBLE);
        }
    }

    public void mo1825y(boolean z, boolean z2) {
        Log.i("nu.cliffords.radioUi","mo1825y(z-"+z+",z2-"+z2+")");
        if (z) {
            if (this.mStButton != null) {
                this.mStButton.setSelected(z2);
                this.mStButton.setVisibility(View.VISIBLE);
            }
            if (this.mStTextView == null) {
                return;
            }
            if (z2) {
                this.mStTextView.setVisibility(View.VISIBLE);
                return;
            } else {
                this.mStTextView.setVisibility(View.INVISIBLE);
                return;
            }
        }
        if (this.mStButton != null) {
            this.mStButton.setSelected(false);
            this.mStButton.setVisibility(View.INVISIBLE);
        }
        if (this.mStTextView != null) {
            this.mStTextView.setVisibility(View.INVISIBLE);
        }
    }

    /* renamed from: p */
    public void mo1816p(boolean z, boolean z2) {
        Log.i("nu.cliffords.radioUi","mo1816p(z-"+z+",z2-"+z2+")");
        if (z) {
            this.mLocButton.setSelected(z2);
            this.mLocButton.setVisibility(View.VISIBLE);
            if (this.mLocTextView == null) {
                return;
            }
            if (z2) {
                this.mLocTextView.setVisibility(View.VISIBLE);
                return;
            } else {
                this.mLocTextView.setVisibility(View.INVISIBLE);
                return;
            }
        }
        this.mLocButton.setSelected(false);
        this.mLocButton.setVisibility(View.INVISIBLE);
        if (this.mLocTextView != null) {
            this.mLocTextView.setVisibility(View.INVISIBLE);
        }
    }

    /* renamed from: v */
    public void mo1822v(boolean z, boolean z2) {
        Log.i("nu.cliffords.radioUi","mo1822v(z-"+z+",z2-"+z2+")");
        if (!z) {
            this.mStIndicatorImageView.setVisibility(View.INVISIBLE);
        } else if (z2) {
            this.mStIndicatorImageView.setVisibility(View.VISIBLE);
        } else {
            this.mStIndicatorImageView.setVisibility(View.INVISIBLE);
        }
    }

    /* renamed from: w */
    public void mo1823w(boolean z) {
        Log.i("nu.cliffords.radioUi","mo1823w(z-"+z+")");
        if (this.mTpIndicatorImageView == null) {
            return;
        }
        if (z) {
            this.mTpIndicatorImageView.setVisibility(View.VISIBLE);
        } else {
            this.mTpIndicatorImageView.setVisibility(View.INVISIBLE);
        }
    }

    /* renamed from: s */
    public void setFrequencyTexts(String frequency) {
        Log.i("nu.cliffords.radioUi","setFrequencyTexts(frequency-"+frequency+")");
        this.f667ag = frequency;
        if (frequency.equals("")) {
            ((TextView) this.mActivity.findViewById(R.id.freq)).setText(formatFrequencyString(this.mCurrentFrequency));
            ((TextView) this.mActivity.findViewById(R.id.freq)).setTextSize(this.mActivity.getResources().getDimension(R.dimen.sizeb));
            return;
        }
        ((TextView) this.mActivity.findViewById(R.id.freq)).setText(frequency);
        ((TextView) this.mActivity.findViewById(R.id.freq)).setTextSize(this.mActivity.getResources().getDimension(R.dimen.sizes));
    }

    /* renamed from: t */
    public void setPtyTextView(String ptyText) {
        Log.i("nu.cliffords.radioUi","setPtyTextView(ptyText-"+ptyText+")");
        this.mPtyTextView.setText(ptyText);
    }

    /* renamed from: u */
    public void setRtText(String rtText) {
        Log.i("nu.cliffords.radioUi","setRtText(rtText-"+rtText+")");
        ((TextView) this.mActivity.findViewById(R.id.txt_rt)).setText(rtText);
    }

    public void setFrequency(int frequency) {
        Log.i("nu.cliffords.radioUi","setFrequency(frequency-"+frequency+")");
        if (this.mCurrentFrequency != frequency) {
            this.mCurrentFrequency = frequency;
            this.mFrequencyBarView.mo1893az(this.mOnFuncInterface.mo1750cu(), this.mOnFuncInterface.mo1749ct(), this.mOnFuncInterface.mo1752cw(), this.mOnFuncInterface.mo1753cx());
            this.mFrequencyBarView.mo1894ba(frequency);
            if (this.f667ag.equals("")) {
                ((TextView) this.mActivity.findViewById(R.id.freq)).setText(formatFrequencyString(frequency));
            }
            ((TextView) this.mActivity.findViewById(R.id.tv_hz)).setText(formatBandString(frequency));
        }
    }

    public int getFrequency() {
        return this.mCurrentFrequency;
    }

    public void mo1813m(int i, int i2, int[][] iArr, boolean z) {
        Log.i("nu.cliffords.radioUi","mo1813m + setupButtonChannels-");
        int i3 = 0;
        while (i3 < 30) {
            BtnChannel btnChannel = getButtonChannel(i3);
            if (i3 / 6 > 2 || z) {
                btnChannel.setFrequencyText(formatFrequencyString(iArr[i3 / 6][i3 % 6]));
            } else {
                String cv = this.mOnFuncInterface.mo1751cv(iArr[i3 / 6][i3 % 6]);
                if (cv.equals("")) {
                    cv = formatFrequencyString(iArr[i3 / 6][i3 % 6]);
                }
                btnChannel.setFrequencyText(cv);
            }
            if (i3 % 6 == i2 && i3 / 6 == i) {
                btnChannel.setSelected(true);
                btnChannel.setEnabled(false);
            } else {
                btnChannel.setSelected(false);
                btnChannel.setEnabled(true);
            }
            btnChannel.setPressed(false);
            i3++;
        }
        if (i > 2) {
            if (!((MyUIPagerAdapter) this.viewPager.getAdapter()).mo1909ao()) {
                this.viewPager.setAdapter(this.f663ab);
                this.viewPager.setCurrentItem(i - 2, false);
            } else if (this.f688r) {
                this.f688r = false;
                this.viewPager.setCurrentItem(i - 2, false);
            } else {
                this.viewPager.setCurrentItem(i - 2, true);
            }
        } else if (((MyUIPagerAdapter) this.viewPager.getAdapter()).mo1909ao()) {
            this.viewPager.setAdapter(this.f662aa);
            this.viewPager.setCurrentItem(i + 1, false);
        } else if (this.f688r) {
            this.f688r = false;
            this.viewPager.setCurrentItem(i + 1, false);
        } else {
            this.viewPager.setCurrentItem(i + 1, true);
        }
        this.mFrequencyBandTextView.setText(this.mActivity.getString(mFrequencyStrings[i]));
    }

    /* renamed from: aa */
    public void mo1802aa(int i, int i2, int[][] iArr, boolean z) {
        Log.i("nu.cliffords.radioUi","mo1802aa");
        for (int i3 = 0; i3 < this.mChannelButtonViewArray.length * 6; i3++) {
            BtnChannel b = getButtonChannel(i3);
            if (i3 / 6 > 2 || z) {
                b.setFrequencyText(formatFrequencyString(iArr[i3 / 6][i3 % 6]));
            } else {
                String cv = this.mOnFuncInterface.mo1751cv(iArr[i3 / 6][i3 % 6]);
                if (cv.equals("")) {
                    cv = formatFrequencyString(iArr[i3 / 6][i3 % 6]);
                }
                b.setFrequencyText(cv);
            }
        }
    }

    private String formatFrequencyString(int frequency) {
        String str;
        if (frequency > 10000000) {
            int i2 = (frequency % 1000000) / 10000;
            if (i2 == 0) {
                str = "00";
            } else if (i2 < 10) {
                str = "0" + i2;
            } else {
                str = "" + i2;
            }
            str = (frequency / 1000000) + "." + str;
        } else {
            str = (frequency / 1000) + "";
        }
        return str.equals("0") ? "" : str;
    }

    private String formatBandString(int frequency) {
        if (frequency > 10000000) {
            return "MHz";
        }
        return "KHz";
    }

    private BtnChannel getButtonChannel(int i) {
        Log.i("nu.cliffords.radioUi","getButtonChannel");
        return this.f675d[i];
    }

    private ArrayList<String> getPtyListChoices() {
        Log.i("nu.cliffords.radioUi","getPtyListChoices");
        ArrayList<String> arrayList = new ArrayList();
        for (int i = 0; i < 32; i++) {
            arrayList.add(this.mActivity.getString(R.string.pty00 + i));
        }
        return arrayList;
    }

    public void onClick(View view) {
        Log.i("nu.cliffords.radioUi","onClick");
        this.f670aj = false;
        String customer = SystemProperties.get("ro.product.customer");
        if (customer != null && customer.equals("WLGK")) {
            switch (view.getId()) {
                case R.id.btn_prev:
                    this.mOnFuncInterface.onWlgkCustomerAndPreviousButtonClicked();
                    return;
                case R.id.btn_next:
                    this.mOnFuncInterface.onWlgkCustomerAndNextButtonClicked();
                    return;
            }
        }
        switch (view.getId()) {
            case R.id.btn_af:
                this.mOnFuncInterface.onAfButtonClicked();
                break;
            case R.id.btn_ta:
                this.mOnFuncInterface.onTaButtonClicked();
                break;
            case R.id.btn_pty:
                this.mOnFuncInterface.onPtyButtonClicked();
                break;
            case R.id.btn_search:
                this.mOnFuncInterface.onSearchButtonClicked();
                return;
            case R.id.btn_st:
                this.mOnFuncInterface.onStButtonClicked();
                break;
            case R.id.btn_loc:
                this.mOnFuncInterface.onLocButtonClicked();
                break;
            case R.id.btn_am:
                if (this.mOnFuncInterface.mo1753cx()) {
                    this.mOnFuncInterface.mo1739cj();
                } else {
                    this.mOnFuncInterface.mo1740ck();
                }
                return;
            case R.id.btn_fm:
                if (this.mOnFuncInterface.mo1754cy()) {
                    this.mOnFuncInterface.mo1739cj();
                } else {
                    this.mOnFuncInterface.mo1740ck();
                }
                return;
        }
    }

    /* renamed from: i */
    public boolean mo1809i() {
        return true;
    }

    /* renamed from: ab */
    public void mo1803ab() {
    }

    /* renamed from: ad */
    public void mo1805ad() {
        Log.i("nu.cliffords.radioUi","mo1805ad");
        this.f688r = true;
        if (this.viewPager != null) {
            int cg = this.mOnFuncInterface.getRadioBand();
            if (cg > 2) {
                this.viewPager.setAdapter(this.f663ab);
                this.viewPager.setCurrentItem(cg - 2, false);
                this.f663ab.notifyDataSetChanged();
            } else {
                this.viewPager.setAdapter(this.f662aa);
                this.viewPager.setCurrentItem(cg + 1, false);
                this.f662aa.notifyDataSetChanged();
            }
        }
    }
}
