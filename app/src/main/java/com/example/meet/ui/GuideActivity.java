package com.example.meet.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.framework.base.BasePagerAdapter;
import com.example.framework.base.BaseUIActivity;
import com.example.framework.manager.MediaPlayerManager;
import com.example.framework.utils.AnimUtils;
import com.example.meet.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseUIActivity implements View.OnClickListener {


    private ViewPager mMViewPager;
    private ImageView mIvMusicSwitch;
    private TextView mTvGuideSkip;
    private ImageView mIvGuidePoint1;
    private ImageView mIvGuidePoint2;
    private ImageView mIvGuidePoint3;

    /**
     * 1.viewpager : 适配器|帧动画播放
     * 2.小圆点的逻辑
     * 3.歌曲的播放
     * 4.属性动画旋转
     * 5.跳转
     */

    private View view1, view2, view3;
    private List<View> mPageList = new ArrayList<>();
    private BasePagerAdapter mPagerAdapter;
    private ImageView mIvGuideStar, mIvGuideNight, mIvGuideSmile;
    private MediaPlayerManager mGuideMusic;
    private ObjectAnimator mAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }

    private void initView() {
        mMViewPager = findViewById(R.id.mViewPager);
        mIvMusicSwitch = findViewById(R.id.iv_music_switch);
        mTvGuideSkip = findViewById(R.id.tv_guide_skip);
        mIvGuidePoint1 = findViewById(R.id.iv_guide_point_1);
        mIvGuidePoint2 = findViewById(R.id.iv_guide_point_2);
        mIvGuidePoint3 = findViewById(R.id.iv_guide_point_3);

        mIvMusicSwitch.setOnClickListener(this);
        mTvGuideSkip.setOnClickListener(this);


        view1 = View.inflate(this, R.layout.layout_pager_guide_1, null);
        view2 = View.inflate(this, R.layout.layout_pager_guide_2, null);
        view3 = View.inflate(this, R.layout.layout_pager_guide_3, null);

        mPageList.add(view1);
        mPageList.add(view2);
        mPageList.add(view3);

        //开启viewpager预加载
        mMViewPager.setOffscreenPageLimit(mPageList.size());

        mPagerAdapter = new BasePagerAdapter(mPageList);
        mMViewPager.setAdapter(mPagerAdapter);


        //帧动画
        mIvGuideStar = view1.findViewById(R.id.iv_guide_star);
        mIvGuideNight = view2.findViewById(R.id.iv_guide_night);
        mIvGuideSmile = view3.findViewById(R.id.iv_guide_smile);

        //播放帧动画
        AnimationDrawable animStar = (AnimationDrawable) mIvGuideStar.getBackground();
        animStar.start();
        AnimationDrawable animNight = (AnimationDrawable) mIvGuideNight.getBackground();
        animNight.start();
        AnimationDrawable animSmile = (AnimationDrawable) mIvGuideSmile.getBackground();
        animSmile.start();


        //小圆点逻辑
        mMViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //歌曲的逻辑
        startMusic();

    }

    /**
     * 播放音乐
     */
    private void startMusic() {
        mGuideMusic = new MediaPlayerManager();
        mGuideMusic.setLooping(true);
        AssetFileDescriptor guideMusic = getResources().openRawResourceFd(R.raw.guide);
        mGuideMusic.startPlay(guideMusic);

        //旋转动画
        mAnim = AnimUtils.rotation(mIvMusicSwitch);
        mAnim.start();
    }

    /**
     * 动态选择小圆点
     */
    private void selectPoint(int position) {
        switch (position) {
            case 0:
                mIvGuidePoint1.setImageResource(R.drawable.img_guide_point_p);
                mIvGuidePoint2.setImageResource(R.drawable.img_guide_point);
                mIvGuidePoint3.setImageResource(R.drawable.img_guide_point);
                break;
            case 1:
                mIvGuidePoint1.setImageResource(R.drawable.img_guide_point);
                mIvGuidePoint2.setImageResource(R.drawable.img_guide_point_p);
                mIvGuidePoint3.setImageResource(R.drawable.img_guide_point);
                break;
            case 2:
                mIvGuidePoint1.setImageResource(R.drawable.img_guide_point);
                mIvGuidePoint2.setImageResource(R.drawable.img_guide_point);
                mIvGuidePoint3.setImageResource(R.drawable.img_guide_point_p);
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_music_switch:
                if (mGuideMusic.MEDIA_STATUS == MediaPlayerManager.MEDIA_STATUS_PAUSE){
                    mAnim.start();
                    mGuideMusic.continuePlay();
                    mIvMusicSwitch.setImageResource(R.drawable.img_guide_music);
                }else if (mGuideMusic.MEDIA_STATUS == MediaPlayerManager.MEDIA_STATUS_PLAY){
                    mAnim.pause();
                    mGuideMusic.pausePlay();
                    mIvMusicSwitch.setImageResource(R.drawable.img_guide_music_off);
                }
                break;
            case R.id.tv_guide_skip:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGuideMusic.stopPlay();
    }
}
