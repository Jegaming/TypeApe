package com.jegaming.typeape.animater;

import android.graphics.Canvas;

import com.jegaming.typeape.animater.RefreshCycle;
import com.jegaming.typeape.model.Word;

import java.util.List;

import static com.jegaming.typeape.model.Word.ANIMATION_ROTATION_LEFT;
import static com.jegaming.typeape.model.Word.ANIMATION_ROTATION_RIGHT;

/**
 * Created by Jegaming on 12/2/19.
 */
public class CanvasAnimater implements RefreshCycle {
    private List<Word> mWordList;
    long mRunningTime;
    int mLatestIndex;
    int mViewWidth, mViewHeight;

    //Zoom
    float mCurrentZoomFactor;
    float mZoomFactor;
    float zoomX, zoomY;

    //Rotate
    int mCurrentRefreshAngle;

    public CanvasAnimater(List<Word> wordList) {
        mWordList = wordList;
        mCurrentZoomFactor = 1;
    }

    @Override
    public void onNewWord(int latestIndex, int viewWidth, int viewHeight) {
        mLatestIndex = latestIndex;
        mViewWidth = viewWidth;
        mViewHeight = viewHeight;

        zoomX = viewWidth / 2;
        zoomY = viewHeight / 2;

        //zoom
        mZoomFactor = mWordList.get(mLatestIndex).getZoom(mViewWidth, mViewHeight);

        //rotate
        if (mWordList.get(mLatestIndex).getAnimationType() == ANIMATION_ROTATION_LEFT) {
            mCurrentRefreshAngle = -90;
        } else if (mWordList.get(mLatestIndex).getAnimationType() == ANIMATION_ROTATION_RIGHT) {
            mCurrentRefreshAngle = 90;
        } else {
            mCurrentRefreshAngle = 0;
        }
    }

    @Override
    public void beforeDrawWords(long runningTime) {
        mRunningTime = runningTime;
    }

    public void performZoom(Canvas canvas) {
        mCurrentZoomFactor = mCurrentZoomFactor + (mZoomFactor - mCurrentZoomFactor) * getQuickTimeFactor();
        canvas.scale(mCurrentZoomFactor, mCurrentZoomFactor, zoomX, zoomY);
    }

    public void performRotate(Canvas canvas, int faceAngle) {
        canvas.rotate(faceAngle + mCurrentRefreshAngle * getTimeFactor(), zoomX, zoomY);
    }

    /**
     * 返回当前transform阶段进行的进度，如果进行完了返回1
     *
     * @return
     */
    private float getQuickTimeFactor() {
        Word lastWord = mWordList.get(mLatestIndex);
        long cycleTime = mRunningTime - lastWord.getStartTime();
        if (cycleTime >= 0.5f * lastWord.getAnimateTime()) {
            return 1;
        } else {
            return cycleTime / (0.5f * lastWord.getAnimateTime());
        }
    }

    private float getTimeFactor() {
        Word lastWord = mWordList.get(mLatestIndex);
        long cycleTime = mRunningTime - lastWord.getStartTime();
        if (cycleTime >= 1f * lastWord.getAnimateTime()) {
            return 1;
        } else {
            return cycleTime / (1f * lastWord.getAnimateTime());
        }
    }

    @Override
    public void afterDrawWords() {

    }
}
