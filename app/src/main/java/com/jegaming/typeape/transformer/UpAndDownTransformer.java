package com.jegaming.typeape.transformer;

import android.graphics.Matrix;


/**
 * Created by Jegaming on 10/24/19.
 */
public class UpAndDownTransformer implements Transformer {
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
                0f, 0f + offset,
                w, 0f + offset,
                0f, h + offset,
                w, h + offset};
        matrix.setPolyToPoly(mSrc, 0, mDst, 0, 4);
    }


    private float getOffset(int h, float f) {
        if (f < 0.125f) {
            return 1.6f * h * f;
        } else if (f < 0.375f) {
            return -1.6f * h * f + 0.4f * h;
        } else if (f < 0.5f) {
            return 1.6f * h * f - 0.8f * h;
        } else if (f < 0.625f) {
            return 0.8f * h * f - 0.4f * h;
        } else if (f < 0.875f) {
            return -0.8f * h * f + 0.6f * h;
        } else if (f < 1) {
            return 0.8f * h * f - 0.8f * h;
        } else {
            return 0;
        }
    }


}
