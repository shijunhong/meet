package com.example.meet.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.example.framework.base.BaseActivity;
import com.example.framework.entity.Constants;
import com.example.framework.utils.SpUtils;
import com.example.meet.MainActivity;
import com.example.meet.R;
import com.example.meet.test.TestActivity;

public class IndexActivity extends BaseActivity {

    /**
     * 1.启动页全屏
     * 2.延迟进入主页
     * 3.根据具体逻辑是进入主页还是引导页还是登录页
     * 4.适配刘海屏
     * */


    private static final int SKIP_MAIN = 1000;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what){
                case SKIP_MAIN:
                    startMain();
                    break;
            }


            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        mHandler.sendEmptyMessageDelayed(SKIP_MAIN,2 * 1000);
    }



    /**
     * 进入主页
     * */
    private void startMain() {

        //1.判断app是否第一次启动  install - first run
        boolean isFirstApp = SpUtils.getInstance().getBoolean(Constants.SP_IS_FIRST_APP, true);

        Intent intent = new Intent();

//        if (isFirstApp){
//            //跳转引导页
//            intent.setClass(this,GuideActivity.class);
//            SpUtils.getInstance().putBoolean(Constants.SP_IS_FIRST_APP, false);
//        }else {
//            //如果不是第一次启动，判断是否曾经登录过
//            String token = SpUtils.getInstance().getString(Constants.SP_TOKEN, "");
//            if (TextUtils.isEmpty(token)){
//                //跳转到登录页
//                intent.setClass(this,LoginActivity.class);
//            }else {
//                //跳转到主页
//                intent.setClass(this, MainActivity.class);
//            }
//        }

        intent.setClass(this, TestActivity.class);
        startActivity(intent);
        finish();
    }


}
