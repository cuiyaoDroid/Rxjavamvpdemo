package com.xianzhi.rxmvp.base.data;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;
import com.xianzhi.rxmvp.util.schedulers.BaseSchedulerProvider;

import io.reactivex.functions.Function;


public abstract class BaseLocalDataSource<T,DB extends BaseDbHelper>{

    @NonNull
    protected final BriteDatabase mDatabaseHelper;

    @NonNull
    protected Function<Cursor, T> mTaskMapperFunction;

    // Prevent direct instantiation.
    protected BaseLocalDataSource(@NonNull Context context,
                                     @NonNull BaseSchedulerProvider schedulerProvider) {
        DB dbHelper = Dbhelper(context);
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, schedulerProvider.io());
        mTaskMapperFunction = this::getData;
    }

    @NonNull
    private T getData(@NonNull Cursor c) {
        return Data(c);
    }
    protected abstract T Data(@NonNull Cursor cursor);
    protected abstract DB Dbhelper(Context context);
}
