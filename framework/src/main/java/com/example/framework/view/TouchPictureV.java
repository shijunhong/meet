package com.example.framework.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.framework.R;


public class TouchPictureV extends View {

    //背景
    private Bitmap bgBitmap;
    //背景画笔
    private Paint mPaintBg;

    //空白块
    private Bitmap mNullBitmap;
    //空白块画笔
    private Paint mPaintNull;

    //移动方块
    private Bitmap mMoveBitmap;
    //移动方块画笔
    private Paint mPaintMove;

    //view的宽高
    private int mWidth, mHeight;
    //空白块大小
    private int CARD_SIZE = 200;

    //缺口坐标
    private int LINE_W, LINE_H = 0;
    //移动块坐标
    private int moveX = 200;
    //误差值
    private int errorValues = 10;
    //点击是否在移动方块内部
    private boolean isDownMove = false;

    private OnViewResultListener viewResultListener;

    public void setViewResultListener(OnViewResultListener viewResultListener) {
        this.viewResultListener = viewResultListener;
    }

    public TouchPictureV(Context context) {
        super(context);
        init();
    }


    public TouchPictureV(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchPictureV(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintBg = new Paint();
        mPaintNull = new Paint();
        mPaintMove = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawNullCard(canvas);
        drawMoveCard(canvas);
    }

    /**
     * 绘制移动方块
     */
    private void drawMoveCard(Canvas canvas) {
        //截取空白块位置坐标的bitmap图片
        mMoveBitmap = Bitmap.createBitmap(bgBitmap, LINE_W, LINE_H, CARD_SIZE, CARD_SIZE);
        //绘制在view上
        canvas.drawBitmap(mMoveBitmap, moveX, LINE_H, mPaintMove);
    }

    /**
     * 绘制空白块
     */
    private void drawNullCard(Canvas canvas) {
        //1.获取图片
        mNullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_null_card);
        //2.计算值
        CARD_SIZE = mNullBitmap.getWidth();
        //3.绘制
        LINE_W = mWidth / 3 * 2;
        LINE_H = mHeight / 2 - (CARD_SIZE / 2);
        canvas.drawBitmap(mNullBitmap, LINE_W, LINE_H, mPaintNull);
    }

    /**
     * 绘制背景
     */
    private void drawBg(Canvas canvas) {
        //1.获取图片
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_bg);
        //2.创建一个空的bitmap
        bgBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        //3.将图片绘制到空的bitmap
        Canvas bgCanvas = new Canvas(bgBitmap);
        bgCanvas.drawBitmap(mBitmap, null, new Rect(0, 0, mWidth, mHeight), mPaintBg);
        //4.将bgBitmap绘制到view上
        canvas.drawBitmap(bgBitmap, null, new Rect(0, 0, mWidth, mHeight), mPaintBg);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //判断点击的坐标是否在方块内部，如果是，可以拖动
                if (event.getX() >= moveX && event.getX() <= moveX + CARD_SIZE && event.getY() >= LINE_H && event.getY() <= LINE_H + CARD_SIZE) {
                    isDownMove = true;
                } else {
                    isDownMove = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //防止越界
                if (isDownMove) {
                    if (event.getX() > 0 && event.getX() < mWidth - CARD_SIZE) {
                        moveX = (int) event.getX();
                        invalidate();
                    }
                }

                break;

            case MotionEvent.ACTION_UP:
                /**
                 * 抬起之后做验证
                 * moveX = LINE_W
                 * */
                if (moveX > LINE_W - errorValues && moveX < LINE_W + errorValues) {
                    if (viewResultListener != null) {
                        viewResultListener.onResult();
                    }
                }
                break;
        }
        return true;
    }

    public interface OnViewResultListener {
        void onResult();
    }
}