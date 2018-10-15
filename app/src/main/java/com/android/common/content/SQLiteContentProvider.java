package com.android.common.content;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteTransactionListener;
import android.net.Uri;
import java.util.ArrayList;

public abstract class SQLiteContentProvider extends ContentProvider implements SQLiteTransactionListener {
    /* renamed from: kq */
    private volatile boolean f396kq;
    /* renamed from: kr */
    private SQLiteOpenHelper f397kr;
    /* renamed from: ks */
    protected SQLiteDatabase f398ks;
    /* renamed from: kt */
    private final ThreadLocal<Boolean> f399kt = new ThreadLocal();

    /* renamed from: sv */
    protected abstract void mo1584sv();

    /* renamed from: sy */
    protected abstract int mo1587sy(Uri uri, String str, String[] strArr);

    /* renamed from: sz */
    protected abstract SQLiteOpenHelper mo1588sz(Context context);

    /* renamed from: ta */
    protected abstract int mo1589ta(Uri uri, ContentValues contentValues, String str, String[] strArr);

    /* renamed from: tb */
    protected abstract Uri mo1590tb(Uri uri, ContentValues contentValues);

    /* renamed from: su */
    public int mo1583su() {
        return 500;
    }

    public boolean onCreate() {
        this.f397kr = mo1588sz(getContext());
        return true;
    }

    /* renamed from: tc */
    private boolean m244tc() {
        return this.f399kt.get() != null ? ((Boolean) this.f399kt.get()).booleanValue() : false;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri tb;
        if (m244tc()) {
            tb = mo1590tb(uri, contentValues);
            if (tb != null) {
                this.f396kq = true;
            }
        } else {
            this.f398ks = this.f397kr.getWritableDatabase();
            this.f398ks.beginTransactionWithListener(this);
            try {
                tb = mo1590tb(uri, contentValues);
                if (tb != null) {
                    this.f396kq = true;
                }
                this.f398ks.setTransactionSuccessful();
                mo1585sw();
            } finally {
                this.f398ks.endTransaction();
            }
        }
        return tb;
    }

    public int bulkInsert(Uri uri, ContentValues[] contentValuesArr) {
        int length = contentValuesArr.length;
        this.f398ks = this.f397kr.getWritableDatabase();
        this.f398ks.beginTransactionWithListener(this);
        int i = 0;
        while (i < length) {
            try {
                if (mo1590tb(uri, contentValuesArr[i]) != null) {
                    this.f396kq = true;
                }
                boolean z = this.f396kq;
                SQLiteDatabase sQLiteDatabase = this.f398ks;
                this.f398ks.yieldIfContendedSafely();
                this.f398ks = sQLiteDatabase;
                this.f396kq = z;
                i++;
            } catch (Throwable th) {
                this.f398ks.endTransaction();
            }
        }
        this.f398ks.setTransactionSuccessful();
        this.f398ks.endTransaction();
        mo1585sw();
        return length;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int ta;
        if (m244tc()) {
            ta = mo1589ta(uri, contentValues, str, strArr);
            if (ta > 0) {
                this.f396kq = true;
            }
        } else {
            this.f398ks = this.f397kr.getWritableDatabase();
            this.f398ks.beginTransactionWithListener(this);
            try {
                ta = mo1589ta(uri, contentValues, str, strArr);
                if (ta > 0) {
                    this.f396kq = true;
                }
                this.f398ks.setTransactionSuccessful();
                mo1585sw();
            } finally {
                this.f398ks.endTransaction();
            }
        }
        return ta;
    }

    public int delete(Uri uri, String str, String[] strArr) {
        int sy;
        if (m244tc()) {
            sy = mo1587sy(uri, str, strArr);
            if (sy > 0) {
                this.f396kq = true;
            }
        } else {
            this.f398ks = this.f397kr.getWritableDatabase();
            this.f398ks.beginTransactionWithListener(this);
            try {
                sy = mo1587sy(uri, str, strArr);
                if (sy > 0) {
                    this.f396kq = true;
                }
                this.f398ks.setTransactionSuccessful();
                mo1585sw();
            } finally {
                this.f398ks.endTransaction();
            }
        }
        return sy;
    }

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException{
        this.f398ks = this.f397kr.getWritableDatabase();
        this.f398ks.beginTransactionWithListener(this);
        try {
            this.f399kt.set(Boolean.valueOf(true));
            int size = arrayList.size();
            ContentProviderResult[] contentProviderResultArr = new ContentProviderResult[size];
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                i++;
                if (i > mo1583su()) {
                    throw new OperationApplicationException("Too many content provider operations between yield points. The maximum number of operations per yield point is 500", i2);
                }
                ContentProviderOperation contentProviderOperation = (ContentProviderOperation) arrayList.get(i3);
                if (i3 > 0 && contentProviderOperation.isYieldAllowed()) {
                    boolean z = this.f396kq;
                    if (this.f398ks.yieldIfContendedSafely(4000)) {
                        this.f398ks = this.f397kr.getWritableDatabase();
                        this.f396kq = z;
                        i2++;
                        i = 0;
                    } else {
                        i = 0;
                    }
                }
                contentProviderResultArr[i3] = contentProviderOperation.apply(this, contentProviderResultArr, i3);
            }
            this.f398ks.setTransactionSuccessful();
            return contentProviderResultArr;
        } finally {
            this.f399kt.set(Boolean.valueOf(false));
            this.f398ks.endTransaction();
            mo1585sw();
        }
    }

    public void onBegin() {
        mo1586sx();
    }

    public void onCommit() {
        mo1582st();
    }

    public void onRollback() {
    }

    /* renamed from: sx */
    protected void mo1586sx() {
    }

    /* renamed from: st */
    protected void mo1582st() {
    }

    /* renamed from: sw */
    protected void mo1585sw() {
        if (this.f396kq) {
            this.f396kq = false;
            mo1584sv();
        }
    }
}
