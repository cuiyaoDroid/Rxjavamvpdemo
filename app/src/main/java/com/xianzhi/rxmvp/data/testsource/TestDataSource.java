package com.xianzhi.rxmvp.data.testsource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by yaocui on 2017/9/18.
 *
 */

public interface TestDataSource {

    Observable<List<Test>> getDatas();
    void refreshData();
    void saveDatas(List<Test> testList);
    void saveData(Test test);
    Observable<Boolean> addNewData(Test test);
}
