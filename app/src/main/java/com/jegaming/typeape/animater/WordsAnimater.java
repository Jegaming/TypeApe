package com.jegaming.typeape.animater;

import android.graphics.Matrix;

import com.jegaming.typeape.model.Word;

import java.util.List;

import static com.jegaming.typeape.model.Word.B;
import static com.jegaming.typeape.model.Word.L;
import static com.jegaming.typeape.model.Word.R;
import static com.jegaming.typeape.model.Word.T;


/**
 * Word1                                       Word2                                     lastWord   mRunningTime
 * mStartTime                                  mStartTime                                mStartTime   ·
 * ·           word1                              ·            word2                          ·
 * ╭-------- mAnimateTime-----╮                   ╭------- mAnimateTime -------╮
 * |----------------------------------------------|-------------------------------------------|--------
 * └--------- mTransformTime ---------┘           └--------- mTransformTime ---------┘
 */

public class WordsAnimater implements RefreshCycle {

    private List<Word> mWordList;
    private float mTimeFactor;
    int mLatestIndex;

    //上移相关
    //Vertical
    float mVerticalOffset, mVerticalTargetOffset;

    //水平移动
    float mHorizontalOffset, mHorizontalTargetOffset;

    public WordsAnimater(List<Word> wordList) {
        this.mWordList = wordList;
    }

    @Override
    public void onNewWord(int latestIndex, int viewWidth, int viewHeight) {
        mLatestIndex = latestIndex;

        if (latestIndex > 0) {
            mVerticalOffset += mVerticalTargetOffset;

            mVerticalTargetOffset = 0.5f * (mWordList.get(mLatestIndex).getOriginalLTRB()[T] - mWordList.get(mLatestIndex - 1).getOriginalLTRB()[T]
                    + mWordList.get(mLatestIndex).getOriginalLTRB()[B] - mWordList.get(mLatestIndex - 1).getOriginalLTRB()[B]);


            mHorizontalOffset += mHorizontalTargetOffset;
            mHorizontalTargetOffset = 0.5f * (mWordList.get(mLatestIndex).getOriginalLTRB()[L] - mWordList.get(mLatestIndex - 1).getOriginalLTRB()[L]
                    + mWordList.get(mLatestIndex).getOriginalLTRB()[R] - mWordList.get(mLatestIndex - 1).getOriginalLTRB()[R]);

        }

    }

    @Override
    public void beforeDrawWords(long runningTime) {
        computeTimeFactor(runningTime);
    }

    private void computeTimeFactor(long runningTime) {
        Word lastWord = mWordList.get(mLatestIndex);
        long cycleTime = runningTime - lastWord.getStartTime();
        if (cycleTime >= 1f * lastWord.getAnimateTime()) {
            mTimeFactor = 1;
        } else {
            mTimeFactor = cycleTime / (1f * lastWord.getAnimateTime());
        }
    }

    public void doAnimate(Matrix matrix) {
        animteMoveUp(matrix);
        animateMoveHorizontal(matrix);
    }


    private void animteMoveUp(Matrix matrix) {
        matrix.postTranslate(0, -(mVerticalOffset + mVerticalTargetOffset * mTimeFactor));
    }


    private void animateMoveHorizontal(Matrix matrix) {
        matrix.postTranslate(-(mHorizontalOffset + mHorizontalTargetOffset * mTimeFactor), 0);
    }


    @Override
    public void afterDrawWords() {

    }

}


