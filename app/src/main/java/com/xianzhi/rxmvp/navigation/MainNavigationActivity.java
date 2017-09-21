package com.xianzhi.rxmvp.navigation;

import android.os.Bundle;


import com.xianzhi.rxmvp.R;
import com.xianzhi.rxmvp.test.TestActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaocui on 2017/7/27.
 *
 */

public class MainNavigationActivity extends BaseNavigationActivity {
    @Override
    protected List<NavigationBean> initNavigationData() {
        List<NavigationBean>navigationBeanList = new ArrayList<>();

        navigationBeanList.add(new NavigationBean(1,"接口调用","三级缓存", TestActivity.class));

        return navigationBeanList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
    }
}
