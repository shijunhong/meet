package com.example.framework.manager;

import android.content.Context;

import cn.bmob.v3.Bmob;

/**
 * bmob管理类
 * */
public class BmobManager {

    private static final String BMOB_SDK_ID="c566bdddde92c7eeb0cfd262e399d430";

    private volatile static BmobManager mInstance = null;
    private BmobManager(){

    }

    public static BmobManager getInstance(){
        if (mInstance == null){
            synchronized (BmobManager.class){
                if (mInstance==null){
                    mInstance= new BmobManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化bmob
     * */
    public void initBmob(Context mContext){
        Bmob.initialize(mContext, BMOB_SDK_ID);
    }

}
