/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xianzhi.rxmvp.test;

import android.support.annotation.NonNull;
import com.xianzhi.rxmvp.data.testsource.Test;
import com.xianzhi.rxmvp.data.testsource.TestDataSource;
import com.xianzhi.rxmvp.util.schedulers.BaseSchedulerProvider;

import java.util.Random;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.google.common.base.Preconditions.checkNotNull;


public class TestPresenter implements TestContract.Presenter {

    private final static String TAG = TestPresenter.class.getSimpleName();

    @NonNull
    private final TestDataSource mTestsRepository;

    @NonNull
    private final TestContract.View mTestView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeDisposable mSubscriptions;


    public TestPresenter(@NonNull TestDataSource tasksRepository,
                         @NonNull TestContract.View TestView,
                         @NonNull BaseSchedulerProvider schedulerProvider) {
        mTestsRepository = checkNotNull(tasksRepository);
        mTestView = checkNotNull(TestView);

        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null!");
        mSubscriptions = new CompositeDisposable();
        mTestView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadDatas(false);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void saveData(String title, String description,String id) {
        Test test = new Test(title,description,id,1);
        mTestsRepository.saveData(test);
    }

    @Override
    public void loadDatas(boolean refresh) {
        if(refresh){
            mTestsRepository.refreshData();
        }

        mTestView.showProgressDialog();
        Disposable subscription = mTestsRepository.getDatas()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mTestView::hindProgressDialog)
                .doOnError(throwable -> {
                    throwable.printStackTrace();
                    mTestView.showErrorInfo();
                })
                .subscribe(mTestView::showTestList);

        mSubscriptions.add(subscription);
    }

    private void loadDataquiet(){
        Disposable subscription = mTestsRepository.getDatas()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(mTestView::showTestList);
        mSubscriptions.add(subscription);
    }
    @Override
    public void randomAddNew() {
        Random random = new Random();
        int index = random.nextInt();
        Test test = new Test("id"+index,"title"+index,"description"+index,index);

        mTestView.showProgressDialog();
        Disposable subscription = mTestsRepository.addNewData(test)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(mTestView::hindProgressDialog)
                .doOnError(throwable -> {
                    throwable.printStackTrace();
                    mTestView.showErrorInfo();
                }).subscribe(aBoolean -> loadDataquiet());
        mSubscriptions.add(subscription);
    }

}
