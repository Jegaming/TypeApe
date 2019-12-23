package com.jegaming.typeape.transformer;

import android.graphics.Matrix;


/**
 * Created by Jegaming on 10/24/19.
 */
public class LeftAndRightTransformer implements Transformer {
    float[] mSrc, mDst;

    @Override
    public void reset() {

    }

    @Override
    public void doTransform(Matrix matrix, int w, int h, int l, int t, float f) {
        mSrc = new float[]{
                0f, 0f,
                w, 0f,
                0f, h,
                w, h};
        float offset = getOffset(h, f);
        mDst = new float[]{
                0f - offset, 0f,
                w - offset, 0f,
                0f - offset, h,
                w - offset, h};
        matrix.setPolyToPoly(mSrc, 0, mDst, 0, 4);
    }


    private float getOffset(int w, float f) {
        if (f < 0.125f) {
            return 1.2f * w * f;
        } else if (f < 0.375f) {
            return -1.2f * w * f + 0.3f * w;
        } else if (f < 0.5f) {
            return 1.2f * w * f - 0.6f * w;
        } else if (f < 0.625f) {
            return 0.8f * w * f - 0.4f * w;
        } else if (f < 0.875f) {
            return -0.2f * w * f + 0.23f * w;
        } else if (f < 1) {
            return -0.4f * w * f + 0.4f * w;
        } else {
            return 0;
        }
    }


}
