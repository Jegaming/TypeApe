package com.jegaming.typeape.transformer;

import android.graphics.Matrix;


/**
 * Created by Jegaming on 10/24/19.
 */
public class TrapezoidTransformer implements Transformer {

    float[] mSrc, mDst;

    public TrapezoidTransformer(int l, int t) {
    }

    public void reset() {
    }

    @Override
    public void doTransform(Matrix matrix, int w, int h, int l, int t, float f) {
        if (f < 2f / 5) {
            return;
        } else {
            f = f - 2f / 5;
        }
        mSrc = new float[]{
                0f, 0f,
                w, 0f,
                0f, h,
                w, h};
        mDst = new float[]{
                0f, 0f,
                w, 0f,
                -getDeltaX(f, w), h,
                w + getDeltaX(f, w), h};
        matrix.setPolyToPoly(mSrc, 0, mDst, 0, 4);
    }

    private float getDeltaX(float f, int w) {
        if (f < 0.125) {
            return 0.8f * w * f;
        } else if (f < 0.375) {
            return -0.8f * w * f + 0.2f * w;
        } else if (f < 0.5) {
            return 0.8f * w * f - 0.4f * w;
        } else if (f < 0.625) {
            return 0.66f * w * f - 0.34f * w;
        } else if (f < 0.875) {
            return -1.33f * w * f + 0.96f * w;
        } else if (f < 1) {
            return 0.66f * w * f - 0.66f * w;
        } else {
            return 0;
        }
    }


}
