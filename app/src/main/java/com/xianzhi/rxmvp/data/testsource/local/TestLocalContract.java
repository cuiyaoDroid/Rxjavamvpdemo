package com.xianzhi.rxmvp.data.testsource.local;

import com.google.auto.value.AutoValue;
import com.xianzhi.rxmvp.data.TestModel;


/**
 * Created by yaocui on 2017/9/21.
 *
 */

@AutoValue
public abstract class TestLocalContract implements TestModel {
    public static final Factory<TestLocalContract> FACTORY = new Factory<>(AutoValue_TestLocalContract::new);
}
