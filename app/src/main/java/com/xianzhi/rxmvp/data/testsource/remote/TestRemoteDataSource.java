package com.xianzhi.rxmvp.data.testsource.remote;

import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xianzhi.rxmvp.base.retrofit.RetrofitFactory;
import com.xianzhi.rxmvp.data.testsource.Test;
import com.xianzhi.rxmvp.data.testsource.TestDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by yaocui on 2017/9/20.
 *
 */

public class TestRemoteDataSource implements TestDataSource {

    private static final String TAG = TestRemoteDataSource.class.getSimpleName();
    @Nullable
    private static TestRemoteDataSource INSTANCE;

    public static TestDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestRemoteDataSource();
        }
        return INSTANCE;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public Observable<List<Test>> getDatas() {
        return RetrofitFactory.getInstance().getDatas("1","json")
                .flatMap(new Function<ResponseBody, ObservableSource<List<Test>>>() {
                    @Override
                    public ObservableSource<List<Test>> apply(@NonNull ResponseBody responseBody) throws Exception {
                        String responesString = responseBody.string();
                        Log.i(TAG, responesString);
                        responseBody.close();

                        TestBean testBean = JSON.parseObject(responesString,TestBean.class);
                        List<Test>tests = new ArrayList<>();
                        for(TestBean.ResultsEntity resultsEntity : testBean.getResults()){
                            tests.add(new Test(resultsEntity.getEmail(),resultsEntity.getPhonenum(),resultsEntity.getMessage(),1));
                        }

                        return Observable.just(tests);
                    }
                }).onErrorReturn(throwable -> {
                    Log.i(TAG,"error");
                    throwable.printStackTrace();
                    return new ArrayList<>();
                });
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void saveDatas(List<Test> testList) {

    }

    @Override
    public void saveData(Test test) {

    }

    @Override
    public Observable<Boolean> addNewData(Test test) {
        return RetrofitFactory.getInstance().addNewData(test.getId(),test.getTitle(),test.getDescription(),""+test.getId()).flatMap(new Function<ResponseBody, ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> apply(@NonNull ResponseBody responseBody) throws Exception {
                return Observable.just(responseBody.string().length()>0);
            }
        });
    }
}