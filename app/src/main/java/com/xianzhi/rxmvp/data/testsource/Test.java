package com.xianzhi.rxmvp.data.testsource;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by yaocui on 2017/9/18.
 *
 */

public class Test {
    @NonNull
    private final String mId;

    @Nullable
    private final String mTitle;

    @Nullable
    private final String mDescription;

    private final int mCompleted;

    public Test(@Nullable String title, @Nullable String description,
                @NonNull String id,  int completed) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mCompleted = completed;
    }

    public int getmCompleted() {
        return mCompleted;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

}
