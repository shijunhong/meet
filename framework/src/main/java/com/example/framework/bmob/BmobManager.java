package com.example.framework.bmob;

import android.content.Context;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;


/**
 * bmob管理类
 */
public class BmobManager {

    private static final String BMOB_SDK_ID = "c566bdddde92c7eeb0cfd262e399d430";

    private volatile static BmobManager mInstance = null;

    private BmobManager() {

    }

    public static BmobManager getInstance() {
        if (mInstance == null) {
            synchronized (BmobManager.class) {
                if (mInstance == null) {
                    mInstance = new BmobManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化bmob
     */
    public void initBmob(Context mContext) {
        Bmob.initialize(mContext, BMOB_SDK_ID);
    }


    /**
     * 发送短信验证码
     * @param phone 手机号码
     * @param listener 回调
     * */
    public void requestSMS(String phone, QueryListener<Integer> listener){
        BmobSMS.requestSMSCode(phone,"",listener);
    }

    /**
     * 注册或登录
     * @param phone 手机号
     * @param code 验证码
     * @param listener 回调
     * */
    public void signOrLoginByMobilePhone(String phone, String code, LogInListener<IMUser> listener) {
        BmobUser.signOrLoginByMobilePhone(phone, code, listener);
    }


    /**
     * 获取本地User对象
     * */
    public IMUser getUser(){
        return BmobUser.getCurrentUser(IMUser.class);
    }


}
