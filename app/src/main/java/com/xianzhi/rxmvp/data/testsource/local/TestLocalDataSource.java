package com.xianzhi.rxmvp.data.testsource.local;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqldelight.SqlDelightStatement;
import com.xianzhi.rxmvp.base.data.BaseLocalDataSource;
import com.xianzhi.rxmvp.data.testsource.Test;
import com.xianzhi.rxmvp.data.testsource.TestDataSource;
import com.xianzhi.rxmvp.util.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by yaocui on 2017/9/18.
 *
 */

public class TestLocalDataSource extends BaseLocalDataSource<Test,TestDbHelper> implements TestDataSource {


    @Nullable
    private static TestLocalDataSource INSTANCE;

    public static TestDataSource getInstance(
            @NonNull Context context,
            @NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new TestLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private TestLocalDataSource(@NonNull Context context, @NonNull BaseSchedulerProvider schedulerProvider) {
        super(context, schedulerProvider);
    }

    @Override
    protected Test Data(@NonNull Cursor cursor) {
        String itemId = cursor.getString(cursor.getColumnIndexOrThrow(TestLocalContract.ENTRYID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(TestLocalContract.TITLE));
        String description =
                cursor.getString(cursor.getColumnIndexOrThrow(TestLocalContract.DESCRIPTION));
        int completed =
                cursor.getInt(cursor.getColumnIndexOrThrow(TestLocalContract.COMPLETED));
        return new Test(title, description, itemId, completed);
    }

    @Override
    protected TestDbHelper Dbhelper(Context context) {
        return new TestDbHelper(context);
    }


    @Override
    public Observable<List<Test>> getDatas() {
        SqlDelightStatement sqlDelightStatement = TestLocalContract.FACTORY.selectAll();
        return mDatabaseHelper.createQuery(TestLocalContract.TABLE_NAME, sqlDelightStatement.statement,sqlDelightStatement.args)
                .mapToList(mTaskMapperFunction);
    }

    @Override
    public void refreshData() {
        mDatabaseHelper.delete(TestLocalContract.TABLE_NAME,null);
    }

    @Override
    public void saveDatas(List<Test> testList) {
        BriteDatabase.Transaction transition = mDatabaseHelper.newTransaction();
        TestLocalContract.InsertRow insertRow = new TestLocalContract.InsertRow(mDatabaseHelper.getWritableDatabase());
        for (Test test:testList) {
            insertRow.bind(test.getId(), test.getTitle(), test.getDescription(), test.getmCompleted() + "");
            mDatabaseHelper.executeInsert(TestLocalContract.TABLE_NAME,insertRow.program);
        }
        transition.markSuccessful();
        transition.end();
    }

    @Override
    public void saveData(Test test) {
        TestLocalContract.InsertRow insertRow = new TestLocalContract.InsertRow(mDatabaseHelper.getWritableDatabase());
        insertRow.bind(test.getId(),test.getTitle(),test.getDescription(),test.getmCompleted()+"");
        mDatabaseHelper.executeInsert(TestLocalContract.TABLE_NAME,insertRow.program);
    }

    @Override
    public Observable<Boolean> addNewData(Test test) {
        return null;
    }
}
