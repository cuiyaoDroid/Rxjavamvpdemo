package com.xianzhi.rxmvp.data.testsource.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.xianzhi.rxmvp.base.data.BaseDbHelper;


/**
 * Created by yaocui on 2017/9/18.
 *
 */

public class TestDbHelper extends BaseDbHelper{

    public TestDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TestLocalContract.CREATE_TABLE);
    }
}
