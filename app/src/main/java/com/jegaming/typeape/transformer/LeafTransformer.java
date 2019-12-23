package com.jegaming.typeape.transformer;

import android.graphics.Matrix;

/**
 * Created by Jegaming on 12/19/19.
 */
public class LeafTransformer implements Transformer {

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

        float offsetX = 0.5f * getXOffset(w, f);
        float offsetY = getYOffset(h, f);

        mDst = new float[]{
                0f + offsetX, 0f + offsetY,
                w - offsetX, 0f - offsetY,
                0f + offsetX, h - offsetY,
                w - offsetX, h + offsetY};
        matrix.setPolyToPoly(mSrc, 0, mDst, 0, 4);
    }

    private float getYOffset(int h, float f) {
        if (f < 0.5f) {
            return -h * f + 0.5f * h;
        } else if (f < 0.625f) {
            return -1.6f * h * f + 0.8f * h;
        } else if (f < 0.75f) {
            return 1.6f * h * f - 1.2f * h;
        } else if (f < 0.875f) {
            return 0.8f * h * f - 0.6f * h;
        } else if (f < 1) {
            return -0.8f * h * f + 0.8f * h;
        } else {
            return 0;
        }
    }

    private float getXOffset(int w, float f) {
        if (f < 0.5f) {
            return -0.8f * w * f + 0.4f * w;
        } else if (f < 0.625f) {
            return 1.6f * w * f - 0.8f * w;
        } else if (f < 0.75f) {
            return -1.6f * w * f + 1.2f * w;
        } else if (f < 0.875f) {
            return 0.8f * w * f - 0.6f * w;
        } else if (f < 1) {
            return -0.8f * w * f + 0.8f * w;
        } else {
            return 0;
        }
    }


}
