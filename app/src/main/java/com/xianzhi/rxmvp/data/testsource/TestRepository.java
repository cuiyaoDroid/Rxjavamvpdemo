package com.xianzhi.rxmvp.data.testsource;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by yaocui on 2017/9/19.
 *
 */

public class TestRepository implements TestDataSource {


    private static final String TAG = TestRepository.class.getSimpleName();
    @Nullable
    private static TestRepository INSTANCE = null;

    @NonNull
    private final TestDataSource mTestRemoteDataSource;

    @NonNull
    private final TestDataSource mTestLocalDataSource;

    private boolean mCacheIsDirty = false;

    private Map<String,Test> mCachedTests;

    private TestRepository(@NonNull TestDataSource testRemoteDataSource,
                            @NonNull TestDataSource testLocalDataSource) {
        mTestRemoteDataSource = checkNotNull(testRemoteDataSource);
        mTestLocalDataSource = checkNotNull(testLocalDataSource);
    }
    public static TestRepository getInstance(@NonNull TestDataSource testRemoteDataSource,
                                              @NonNull TestDataSource testLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TestRepository(testRemoteDataSource, testLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<Test>> getDatas() {

        if (mCachedTests != null && !mCachedTests.isEmpty() && !mCacheIsDirty) {
            Log.i(TAG,"mCachedTests dataget");
            return Observable.fromIterable(mCachedTests.values()).toList().toObservable();
        } else if (mCachedTests == null) {
            mCachedTests = new LinkedHashMap<>();
        }

        Observable<List<Test>> remoteTests = loadTestRemoteDataSource();
        if (mCacheIsDirty) {
            return remoteTests;
        } else {
            // Query the local storage if available. If not, query the network.
            Observable<List<Test>>localTests = loadTestsLocalDataSource();

            return Observable.concat(localTests, remoteTests)
                    .firstElement().toObservable();
        }
    }

    private Observable<List<Test>> loadTestRemoteDataSource(){

        return mTestRemoteDataSource.getDatas()
                .filter(testList -> !testList.isEmpty())
                .flatMap(new Function<List<Test>, ObservableSource<List<Test>>>() {
                    @Override
                    public ObservableSource<List<Test>> apply(@io.reactivex.annotations.NonNull List<Test> testList) throws Exception {
                        mCachedTests.clear();
                        return Observable.fromIterable(testList).doOnNext(test -> mCachedTests.put(test.getId(),test)).toList().toObservable();
                    }
                })
                .doOnNext(mCachedTests->{
                    mTestLocalDataSource.refreshData();
                    mTestLocalDataSource.saveDatas(mCachedTests);
                })
                .doOnComplete(()-> mCacheIsDirty=false);
    }

    private Observable<List<Test>> loadTestsLocalDataSource(){

        return mTestLocalDataSource.getDatas()
                .firstElement()//problem : https://github.com/square/sqlbrite/issues/123
                .toObservable()
                .filter(testList -> !testList.isEmpty())
                .flatMap(new Function<List<Test>, ObservableSource<List<Test>>>() {
                    @Override
                    public ObservableSource<List<Test>> apply(@io.reactivex.annotations.NonNull List<Test> testList) throws Exception {
                        mCachedTests.clear();
                        return Observable.fromIterable(testList).doOnNext(test -> mCachedTests.put(test.getId(),test)).toList().toObservable();
                    }
                })
                .doOnNext(testList->Log.i(TAG,"mTestLocalDataSource doOnNext"))
                .doOnComplete(()->Log.i(TAG,"mTestLocalDataSource doOnComplete"));
    }


    @Override
    public void refreshData() {
        mCacheIsDirty = true;
        mTestRemoteDataSource.refreshData();
    }

    @Override
    public void saveDatas(List<Test> testList) {
        mTestLocalDataSource.saveDatas(testList);
    }

    @Override
    public void saveData(Test test) {
        mTestLocalDataSource.saveData(test);
    }

    @Override
    public Observable<Boolean> addNewData(Test test) {
        mTestLocalDataSource.saveData(test);
        mCachedTests.put(test.getId(),test);
        return mTestRemoteDataSource.addNewData(test);
    }
}
