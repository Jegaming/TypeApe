package com.jegaming.typeape.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jegaming.typeape.animater.CanvasAnimater;
import com.jegaming.typeape.animater.WordsAnimater;
import com.jegaming.typeape.model.TypeApeModel;
import com.jegaming.typeape.model.Word;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.jegaming.typeape.model.Word.ANIMATION_ROTATION_LEFT;
import static com.jegaming.typeape.model.Word.ANIMATION_ROTATION_RIGHT;

/**
 * Created by Jegaming on 10/22/19.
 */
public class TypeApeView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public static final int STATUS_STANDING = 0;
    public static final int STATUS_ENTERING = 1;

    public static final String TAG = "TypeApeView";

    private SurfaceHolder mHolder;

    private Thread mDrawThread;
    private boolean isRunning;
    private AtomicBoolean isDataChanged;

    private Paint mTextPaint;

    private Canvas mCanvas;
    private Bitmap mBackgroundBm;
    private Matrix mBackgroundMatrix;
    private int mBackgroundHeight;
    private int mBackgroundWidth;
    private int mBackgroundColor;

    /**
     * View的宽高
     */
    private int mWidth, mHeight;

    /**
     * 动画开始的时间点
     */
    private long mStartTimeStamp;

    /**
     * 动画从开始到现在运行的时长
     */
    private long mRunningTime;

    /**
     * 核心数据
     * mTypeApeModel
     * mIndex 当前在绘制句子的索引
     */
    private TypeApeModel mTypeApeModel;
    private TypeApeModel mTempModel;
    private int mIndex;

    /**
     * 界面上绘制word的数量
     */
    private int mMaxBitmap = 10;

    private WordsAnimater mAnimater;
    private CanvasAnimater mCanvasAnimater;
    private int mAngle;//朝向，当前旋转的角度0 90 180 270

    ProcessListener mListener;

    public TypeApeView(Context context) {
        super(context);
        initView();
    }

    public TypeApeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TypeApeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    public void setRecycleSize(int count) {
        mMaxBitmap = count;
    }

    public void setBgImage(Bitmap bm) {
        this.mBackgroundBm = bm;
        this.mBackgroundHeight = bm.getHeight();
        this.mBackgroundWidth = bm.getWidth();
        this.mBackgroundMatrix = new Matrix();
    }

    public void setBgColor(int color) {
        this.mBackgroundColor = color;
    }

    public void setProcessListener(ProcessListener listener){
        this.mListener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 初始化view
     */
    private void initView() {
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        getHolder().addCallback(this);
        isDataChanged = new AtomicBoolean(true);
    }

    public void setData(TypeApeModel model) {
        mTempModel = model;
        isDataChanged.set(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mHolder = holder;
        mDrawThread = new Thread(this);
        mDrawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    private void reset() {
        mRunningTime = 0;
        mIndex = 0;
        mAngle = 0;
        mAnimater = new WordsAnimater(mTypeApeModel.wordList);
        mCanvasAnimater = new CanvasAnimater(mTypeApeModel.wordList);
        mStartTimeStamp = System.currentTimeMillis();
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning) {

            if (isDataChanged.get()) {
                mTypeApeModel = mTempModel;
                if (mTypeApeModel == null || mTypeApeModel.wordList == null) {
                    continue;
                }
                reset();
                isDataChanged.set(false);
            }
            try {
                mCanvas = mHolder.lockCanvas();
                if (mCanvas != null) {
                    if (mTypeApeModel == null) {
                        Log.e(TAG, "empty TypeModel data!");
                        continue;
                    }
                    if (mTypeApeModel.wordList == null) {
                        Log.e(TAG, "empty Word data!");
                        continue;
                    }

                    drawBackGround();
                    drawWords();
                }
            } finally {
                try {
                    mHolder.unlockCanvasAndPost(mCanvas);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void drawBackGround() {
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        if (mBackgroundBm != null) {
            float scaleX = 1f * mWidth / mBackgroundWidth;
            float scaleY = 1f * mHeight / mBackgroundHeight;
            mBackgroundMatrix.setScale(scaleX, scaleY);
            mCanvas.drawBitmap(mBackgroundBm, mBackgroundMatrix, null);
        } else {
            mCanvas.drawColor(mBackgroundColor);
        }

    }

    private void drawWords() {

        long currentTimeStamp = System.currentTimeMillis();
        mRunningTime = currentTimeStamp - mStartTimeStamp;

        //更新mIndex
        if (mIndex + 1 < mTypeApeModel.wordList.size()) {
            if (mTypeApeModel.wordList.get(mIndex + 1).getStartTime() <= mRunningTime) {
                mIndex++;
            }
        }

        //创建最新的word
        if (mTypeApeModel.wordList.get(mIndex).getBitmap() == null) {

            Word word = mTypeApeModel.wordList.get(mIndex);
            mTextPaint.setTextSize(Math.min(0.3f * mWidth / word.getText().length(), 0.3f * mHeight / word.getText().length()) + (int) (Math.random() * 10 - 5));
            word.createBitmap(mTextPaint);

            if (mIndex > 0) {
                Word preWord = mTypeApeModel.wordList.get(mIndex - 1);
                //计算当前canvas的角度 mAngle
                if (preWord.getAnimationType() == ANIMATION_ROTATION_LEFT) {
                    mAngle -= 90;
                } else if (preWord.getAnimationType() == ANIMATION_ROTATION_RIGHT) {
                    mAngle += 90;
                }
                if (mAngle == 360) mAngle = 0;
                if (mAngle == -90) mAngle = 270;
                //初始化最新的word
                word.initMatrixByPreWord(preWord.getOriginalLTRB(), mAngle);//matrix可以放到之后初始化
            } else {
                mAngle = 0;
                //初始化收个word
                word.initMatrixInCenter(mWidth, mHeight);
            }

            mAnimater.onNewWord(mIndex, mWidth, mHeight);
            mCanvasAnimater.onNewWord(mIndex, mWidth, mHeight);
            if (mListener!=null)
                mListener.onNewWord(mIndex);

            //按照mMaxBitmap数量 回收不再显示的bitmap
            if (mIndex - mMaxBitmap > 0) {
                mTypeApeModel.wordList.get(mIndex - mMaxBitmap).clearBitmap();
            }
        }

        //配置animater
        mAnimater.beforeDrawWords(mRunningTime);
        mCanvasAnimater.beforeDrawWords(mRunningTime);

        mCanvas.save();
        //缩放
        mCanvasAnimater.performZoom(mCanvas);
        mCanvasAnimater.performRotate(mCanvas, mAngle);


        //绘制目前已入场的文字, 数量不超过mMaxBitmap
        for (int i = mIndex; i > mIndex - mMaxBitmap && i >= 0; i--) {
            drawWord(mTypeApeModel.wordList.get(i));
        }

        mAnimater.afterDrawWords();
        mCanvasAnimater.afterDrawWords();

        mCanvas.restore();

    }

    private void drawWord(Word model) {
        if (model == null) {
            Log.e(TAG, "drawText: Word is null!");
            return;
        }

        if (model.getBitmap() != null) {
            mCanvas.drawBitmap(model.getBitmap(), model.updateMatrix(mAnimater, mRunningTime), null);
        } else {
            Log.e(TAG, "drawText: bitmap null");
        }
    }

    public interface ProcessListener {
        void onNewWord(int index);
    }

}
