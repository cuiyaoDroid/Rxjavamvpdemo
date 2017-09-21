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

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.xianzhi.rxmvp.R;
import com.xianzhi.rxmvp.data.testsource.Test;
import com.xianzhi.rxmvp.data.testsource.TestRepository;
import com.xianzhi.rxmvp.data.testsource.local.TestLocalDataSource;
import com.xianzhi.rxmvp.data.testsource.remote.TestRemoteDataSource;
import com.xianzhi.rxmvp.util.adapter.decoration.SpaceItemDecoration;
import com.xianzhi.rxmvp.util.schedulers.SchedulerProvider;
import com.xianzhi.rxmvp.util.tool.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Displays an add or edit task screen.
 */
public class TestActivity extends AppCompatActivity implements TestContract.View,TestAdapter.OnListItemClickListener {
    private final static String TAG = TestActivity.class.getSimpleName();

    private TestContract.Presenter mTestPresenter;

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;
    @Bind(R.id.navigation_list)
    RecyclerView testRecyclerView;
    @Bind(R.id.refresh_btn)
    Button refreshBtn;
    @Bind(R.id.add_btn)
    Button addBtn;


    private TestAdapter testAdapter;
    private List<Test>testList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setSupportActionBar(toolbar);
        }

        new TestPresenter(
                TestRepository.getInstance(TestRemoteDataSource.getInstance(),TestLocalDataSource.getInstance(getApplicationContext(), SchedulerProvider.getInstance())),
                this,
                SchedulerProvider.getInstance());

        testList = new ArrayList<>();

        testRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        testRecyclerView.setHasFixedSize(true);
        testRecyclerView.addItemDecoration(new SpaceItemDecoration(DensityUtil.dip2px(getApplicationContext(),10)));

        testRecyclerView.setAdapter(testAdapter = new TestAdapter(this,testList));
        testAdapter.setOnListItemClickListener(this);

        refreshBtn.setOnClickListener(v -> mTestPresenter.loadDatas(true));
        addBtn.setOnClickListener(v -> mTestPresenter.randomAddNew());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTestPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTestPresenter.unsubscribe();
        TestLocalDataSource.destroyInstance();
    }

    @Override
    public void setPresenter(TestContract.Presenter presenter) {
        mTestPresenter = checkNotNull(presenter);
    }

    @Override
    public void showTestList(List<Test> testList) {
        this.testList.clear();
        this.testList.addAll(testList);
        testAdapter.notifyDataSetChanged();

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void showErrorInfo() {

    }

    @Override
    public void hindProgressDialog() {

    }

    @Override
    public void onItemClick(int position) {

    }
}
