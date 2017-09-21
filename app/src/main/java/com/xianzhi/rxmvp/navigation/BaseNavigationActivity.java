package com.xianzhi.rxmvp.navigation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import com.xianzhi.rxmvp.R;
import com.xianzhi.rxmvp.util.adapter.decoration.SpaceItemDecoration;
import com.xianzhi.rxmvp.util.tool.DensityUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class BaseNavigationActivity extends AppCompatActivity implements NavigationAdapter.OnListItemClickListener{

    @Bind(R.id.navigation_list)
    RecyclerView navigationList;
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    private NavigationAdapter navigationAdapter;
    private List<NavigationBean>navigationBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setSupportActionBar(toolbar);
        }

        initDemoList();
    }

    protected abstract List<NavigationBean> initNavigationData();


    private void initDemoList(){

        navigationBeanList = initNavigationData();

        navigationList.setLayoutManager(new LinearLayoutManager(this));

        navigationList.setHasFixedSize(true);

        //Use this now
        navigationList.addItemDecoration(new SpaceItemDecoration(DensityUtil.dip2px(getApplicationContext(),10)));

        navigationList.setAdapter(navigationAdapter = new NavigationAdapter(this,navigationBeanList));
        navigationAdapter.setOnListItemClickListener(this);

    }


    @Override
    public void onItemClick(int position) {
        if(position<0||position>=navigationBeanList.size()){
            return;
        }
        NavigationBean navigationBean = navigationBeanList.get(position);
        Intent intent = new Intent(getApplicationContext(),navigationBean.getActivity());
        startActivity(intent);
    }



    public class NavigationBean {
        private int id;
        private String name;
        private String info;
        private Class activity;

        public NavigationBean(int id, String name, String info, Class activity) {
            this.id = id;
            this.name = name;
            this.info = info;
            this.activity = activity;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public Class getActivity() {
            return activity;
        }

        public void setActivity(Class activity) {
            this.activity = activity;
        }
    }

}
