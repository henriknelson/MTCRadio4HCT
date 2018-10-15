package com.microntek.radio.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.microntek.radio.R;

import java.util.List;

class MyBaseAdapter extends BaseAdapter {
    private Context mContext = null;
    private LayoutInflater mLayoutInflater;
    private List<String> mList;
    private int f700ap = -1;
    private Ui mUi;

    public MyBaseAdapter(Ui ui, Context context, List<String> list) {
        this.mUi = ui;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(this.mContext);
        this.mList = list;
    }

    /* renamed from: ak */
    public void mo1904ak(int i) {
        this.f700ap = i;
    }

    public int getCount() {
        return this.mList.size();
    }

    public String getItem(int i) {
        return (String) this.mList.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.mLayoutInflater.inflate(R.layout.radio_list, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.txt_ptylist);
        String str = ((String) this.mList.get(i)).toString();
        textView.setText(str.substring(str.lastIndexOf(47) + 1));
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_ptylist);
        if (this.f700ap == i) {
            linearLayout.setBackgroundResource(R.drawable.ico_list_sel);
        } else {
            linearLayout.setBackgroundResource(0);
        }
        return view;
    }
}
