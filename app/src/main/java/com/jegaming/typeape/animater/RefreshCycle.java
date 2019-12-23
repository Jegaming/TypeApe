package com.jegaming.typeape.animater;

/**
 * Created by Jegaming on 12/2/19.
 */
public interface RefreshCycle {

    void onNewWord(int latestIndex, int viewWidth, int viewHeight);

    void beforeDrawWords(long runningTime);

    void afterDrawWords();

}
