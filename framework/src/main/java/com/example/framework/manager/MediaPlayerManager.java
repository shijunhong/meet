package com.example.framework.manager;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.framework.utils.LogUtils;

import java.io.IOException;

public class MediaPlayerManager {

    //播放
    public static final int MEDIA_STATUS_PLAY = 0;
    //暂停
    public static final int MEDIA_STATUS_PAUSE = 1;
    //停止
    public static final int MEDIA_STATUS_STOP = 2;

    public int MEDIA_STATUS = MEDIA_STATUS_STOP;

    private static final int H_PROGRESS = 1000;


    private OnMusicProgressListener musicProgressListener;

    /**
     * 计算歌曲的进度
     * 1.开始播放的时候就开始循环计算时长
     * 2.将进度计算结果对外抛出
     * */
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what){
                case H_PROGRESS:
                    if (musicProgressListener != null){
                        //拿到当前时长
                        int currentPosition = getCurrentPosition();
                        int pos = (int) (((float) currentPosition ) /((float)mMediaPlayer.getDuration()) * 100);
                        musicProgressListener.OnProgress(currentPosition,pos);
                        mHandler.sendEmptyMessageDelayed(H_PROGRESS,1000);
                    }
                    break;
            }

            return false;
        }
    });



    private MediaPlayer mMediaPlayer;
    public MediaPlayerManager(){
        mMediaPlayer = new MediaPlayer();
    }

    /**
     * 是否在播放
     * */
    public boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }

    /**
     * 开始播放
     *
     * @param path*/
    public void startPlay(AssetFileDescriptor path){
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path.getFileDescriptor(),path.getStartOffset(),path.getLength());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            MEDIA_STATUS = MEDIA_STATUS_PLAY;
            mHandler.sendEmptyMessage(H_PROGRESS);
        } catch (IOException e) {
            LogUtils.e(e.toString());
            e.printStackTrace();
        }
    }
    /**
     * 开始播放
     *
     * @param path*/
    public void startPlay(String path){
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            MEDIA_STATUS = MEDIA_STATUS_PLAY;
            mHandler.sendEmptyMessage(H_PROGRESS);
        } catch (IOException e) {
            LogUtils.e(e.toString());
            e.printStackTrace();
        }
    }


    /**
     * 暂停播放
     * */
    public void pausePlay(){
        if (isPlaying()){
            mMediaPlayer.pause();
            MEDIA_STATUS = MEDIA_STATUS_PAUSE;
            mHandler.removeMessages(H_PROGRESS);

        }
    }

    /**
     * 继续播放
     * */
    public void continuePlay(){
        mMediaPlayer.start();
        MEDIA_STATUS = MEDIA_STATUS_PLAY;
        mHandler.sendEmptyMessage(H_PROGRESS);

    }

    /**
     * 停止播放
     * */
    public void stopPlay(){
        mMediaPlayer.stop();
        MEDIA_STATUS = MEDIA_STATUS_STOP;
        mHandler.removeMessages(H_PROGRESS);

    }


    /**
     * 获取当前位置
     * */
    public int getCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * 获取总时长
     * */
    public int getDuration(){
        return mMediaPlayer.getDuration();
    }

    /***
     *
     * 是否循环
     */
    public void setLooping(boolean isLooping){
        mMediaPlayer.setLooping(isLooping);
    }


    /**
     * 跳转位置
     * */
    public void seekTo(int ms){
        mMediaPlayer.seekTo(ms);
    }


    /**
     * 播放结束监听
     * */
    public void setOnComplteionListener(MediaPlayer.OnCompletionListener listener){
        mMediaPlayer.setOnCompletionListener(listener);
        mHandler.removeMessages(H_PROGRESS);
    }

    /**
     * 播放错误监听
     * */
    public void setOnErrorListener(MediaPlayer.OnErrorListener listener){
        mMediaPlayer.setOnErrorListener(listener);
    }
    /**
     * 播放进度回调监听
     * */
    public void setOnProgressListener(OnMusicProgressListener listener){
        musicProgressListener = listener;
    }
    public interface OnMusicProgressListener{
        void OnProgress(int progress,int pos);
    }

}