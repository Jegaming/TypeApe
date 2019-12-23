package com.jegaming.typeape.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import com.jegaming.typeape.animater.WordsAnimater;
import com.jegaming.typeape.placehelper.Angle0PlaceHelperImplI;
import com.jegaming.typeape.placehelper.Angle180PlaceHelperImplI;
import com.jegaming.typeape.placehelper.Angle270PlaceHelperImplI;
import com.jegaming.typeape.placehelper.Angle90PlaceHelperImplI;
import com.jegaming.typeape.placehelper.IWordPlaceHelper;
import com.jegaming.typeape.transformer.TransformHelper;
import com.jegaming.typeape.view.LTRBObject;

import java.util.Arrays;

import static com.jegaming.typeape.view.TypeApeView.TAG;

/**
 * Created by Jegaming on 10/22/19.
 */
public class Word implements LTRBObject {


    /**
     * 本Word入场时，整体画面Animator的动作
     */
    public static final int ANIMATION_UP_ALIGN_LEFT = 1;   //向上平移,左对齐
    public static final int ANIMATION_UP_ALIGN_RIGHT = 2;   //向上平移，右对齐
    public static final int ANIMATION_ROTATION_LEFT = 3;    //左旋90度（逆时针）
    public static final int ANIMATION_ROTATION_RIGHT = 4;   //右旋90度（顺时针）

    /**
     * 本Word入场变形的动作
     */
    public static final int TYPE_UAD = 1;
    public static final int TYPE_TRA = 2;
    public static final int TYPE_EPD = 3;
    public static final int TYPE_ZOM = 4;
    public static final int TYPE_DPD = 5;
    public static final int TYPE_LEF = 6;
    public static final int TYPE_LAR = 7;
    public static final int TYPE_FUP = 8;

    /**
     * values[4] 对应的index 用来表示位置信息
     */
    public static int L = 0;
    public static int T = 1;
    public static int R = 2;
    public static int B = 3;

    /**
     * mStartTimeStamp 本句开始的时间点
     * mTransformTime 本句变形动作的时间
     * mAnimateTime 本句入场动画的时间 占据变形动作时间的前40%
     * mTransformType 本句变形动作的类型
     * mAnimationType 本句入场动画的类型
     */
    private final long mStartTimeStamp;
    private final long mTransformTime;//从 animate和transform开始 到 transform结束 的时长
    private final long mAnimateTime;
    private final int mTransformType;
    private final int mAnimationType;

    /**
     * mText 本句的文本
     * mTextSize 字体的大小
     * mTextColor 字体的颜色
     */
    private final String mText;
    private final int mTextSize;
    private final int mTextColor; //文本颜色

    /**
     * 图形相关变量
     */
    private Bitmap mBitmap;
    private Matrix mMatrix; //负责操作bitmap的矩阵
    private int mWidth, mHeight;
    private IWordPlaceHelper mWordPlaceHelper; //记录位置信息


    public Word(long mStartTimeStamp, long transformTime, String text, int textSize, int textColor, int animationType, int transformType) {
        this.mStartTimeStamp = mStartTimeStamp;
        this.mTransformTime = transformTime;
        this.mAnimateTime = mTransformTime * 2 / 5;
        this.mText = text;
        this.mAnimationType = animationType;
        this.mTransformType = transformType;
        this.mTextSize = textSize;
        this.mTextColor = textColor;
    }

    @Override
    public String toString() {
        return "mStartTimeStamp:" + mStartTimeStamp
                + " Text:" + (mText == null ? "null" : mText) + "origin ltrb:" + Arrays.toString(getOriginalLTRB());
    }

    public boolean createBitmap(Paint textPaint) {
        if (mText == null)
            return false;

        //字体大小
        if (mTextSize > 0) {
            textPaint.setTextSize(mTextSize);
        }

        textPaint.setColor(mTextColor);
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        mWidth = (int) textPaint.measureText(mText);
        mHeight = fontMetricsInt.descent - fontMetricsInt.ascent;

        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
//        canvas.drawColor(Color.GRAY);
        canvas.drawText(mText, 0, fontMetricsInt.leading - fontMetricsInt.ascent, textPaint);

        Log.d(TAG, "createBitmap: word:" + this.hashCode() + " bitmap=" + mBitmap.getByteCount() / 1024 + "kb");
        return true;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void clearBitmap() {
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }

    /**
     * 传入前一个Word的 l、t、r、b, 从而确定当前Word位置
     *
     * @return
     */
    public void initMatrixByPreWord(float[] ltrbValues, int angle) {
        if (angle == 0) {
            mWordPlaceHelper = new Angle0PlaceHelperImplI(mWidth, mHeight, mAnimationType, ltrbValues);
        } else if (angle == 90) {
            mWordPlaceHelper = new Angle90PlaceHelperImplI(mWidth, mHeight, mAnimationType, ltrbValues);
        } else if (angle == 180) {
            mWordPlaceHelper = new Angle180PlaceHelperImplI(mWidth, mHeight, mAnimationType, ltrbValues);
        } else if (angle == 270) {
            mWordPlaceHelper = new Angle270PlaceHelperImplI(mWidth, mHeight, mAnimationType, ltrbValues);
        } else {
            Log.e(TAG, "initMatrixByPreWord: angle error");
            return;
        }

        if (mMatrix == null) {
            mMatrix = new Matrix();
        }
        Log.d(TAG, "initMatrix ByPreWord: mL = " + getL() + " mT = " + getT());

        return;
    }

    /**
     * 作为第一个Word，在屏幕中间绘制
     *
     * @return
     */
    public void initMatrixInCenter(int viewWidth, int viewHeight) {
        if (mMatrix == null) {
            mMatrix = new Matrix();
        }
        mWordPlaceHelper = new Angle0PlaceHelperImplI(mWidth, mHeight, -1, new float[]{viewWidth / 2 - mWidth / 2, viewHeight / 2 - mHeight / 2});

        Log.d(TAG, "initMatrix InCenter: mL = " + getL() + " mT = " + getT());

        if (mMatrix == null) {
            mMatrix = new Matrix();
        }
        return;
    }

    private boolean isTransformOver = false;

    public Matrix updateMatrix(WordsAnimater animater, long runningTime) {
        mMatrix.reset();
        //do transform
        if (runningTime < mStartTimeStamp + mTransformTime) {
            //正在入场
            isTransformOver = false;
            doTransform((runningTime - mStartTimeStamp) / (1f * mTransformTime));
        } else if (!isTransformOver) {
            //入场完毕
            isTransformOver = true;
            doTransform(1);
        }

        //put in place
        mWordPlaceHelper.putInPlace(mMatrix);

        //animate move all words to center
        animater.doAnimate(mMatrix);

        return mMatrix;
    }

    private void doTransform(float timeFactor) {
        TransformHelper.doTransform(mTransformType, mMatrix, mWidth, mHeight, (int) getL(), (int) getT(), timeFactor);
    }

    public float getZoom(int viewWidth, int viewHeight) {
        if (mBitmap == null) {
            Log.e(TAG, "getZoom: Bitmap has not been initialized");
            return 1;
        }
        return (viewWidth * 2 / 3f) / mBitmap.getWidth();
    }

    public float[] getOriginalLTRB() {
        float[] ltrbValues = new float[4];
        if (mWordPlaceHelper == null) {
            Log.e(TAG, "getOriginalLTRB: has not been initialized!");
            return ltrbValues;
        }
        ltrbValues[L] = getL();
        ltrbValues[T] = getT();
        ltrbValues[R] = getR();
        ltrbValues[B] = getB();
        return ltrbValues;
    }

    public String getText(){
        return mText;
    }

    public long getStartTime() {
        return mStartTimeStamp;
    }

    public long getAnimateTime() {
        return mAnimateTime;
    }

    public long getTransformTime() {
        return mTransformTime;
    }

    public int getAnimationType() {
        return mAnimationType;
    }

    public float getHeight() {
        return mHeight;
    }

    public float getWidth() {
        return mWidth;
    }

    @Override
    public float getL() {
        return mWordPlaceHelper == null ? 0 : mWordPlaceHelper.getL();
    }

    @Override
    public float getT() {
        return mWordPlaceHelper == null ? 0 : mWordPlaceHelper.getT();
    }

    @Override
    public float getR() {
        return mWordPlaceHelper == null ? 0 : mWordPlaceHelper.getR();
    }

    @Override
    public float getB() {
        return mWordPlaceHelper == null ? 0 : mWordPlaceHelper.getB();
    }


    public static class Builder {

        /**
         * mStartTimeStamp 本句开始的时间点
         * mTransformTime 本句变形动作的时间
         * mTransformType 本句变形动作的类型
         * mAnimationType 本句入场动画的类型
         * mText 本句的文本
         * mTextSize 字体的大小
         * mTextColor 字体的颜色
         */
        private long startTimeStamp;
        private long transformTime;//从 animate和transform开始 到 transform结束 的时长，默认400ms或者默认百分比.创建时进行验证.
        private int animationType;
        private int transformType;// = (int) (Math.random()*7+1);
        private String text;
        private int textSize;
        private int textColor; //文本颜色

        public Builder(String text, long startTimeStamp) {
            this.text = text;
            this.startTimeStamp = startTimeStamp;
            this.transformTime = 200;
            this.animationType = 1;
            this.transformType = 0;
            this.textSize = -1;
            this.textColor = Color.BLACK;
        }

        public Builder transformTime(long transformTime) {
            this.transformTime = transformTime;
            return this;
        }

        public Builder animationType(int animationType) {
            this.animationType = animationType;
            return this;
        }

        public Builder transformType(int transformType) {
            this.transformType = transformType;
            return this;
        }

        public Builder textSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Word build() {
            return new Word(startTimeStamp, transformTime, text, textSize, textColor, animationType, transformType);
        }
    }
}
