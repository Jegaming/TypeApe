package com.jegaming.typeape.placehelper;

/**
 * Created by Jegaming on 12/6/19.
 */
public abstract class BasePlaceHelper implements IWordPlaceHelper {
    protected float mL, mT, mR, mB;//放到指定的位置上之后的ltrb
    protected int wordAnimationType;
    protected float wordWidth, wordHeight;

    public BasePlaceHelper(float width, float height, int animType, float[] ltrbValues) {
        this.wordWidth = width;
        this.wordHeight = height;
        this.wordAnimationType = animType;
        init(ltrbValues);
    }

    /**
     * 计算mL, mT, mR, mB
     *
     * @param ltrbValues
     */
    abstract void init(float[] ltrbValues);

    @Override
    public final float getL() {
        return mL;
    }

    @Override
    public final float getT() {
        return mT;
    }

    @Override
    public final float getR() {
        return mR;
    }

    @Override
    public final float getB() {
        return mB;
    }
}
