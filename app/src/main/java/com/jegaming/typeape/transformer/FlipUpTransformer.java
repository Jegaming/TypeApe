package com.jegaming.typeape.transformer;

import android.graphics.Matrix;

/**
 * Created by Jegaming on 12/19/19.
 */
public class FlipUpTransformer implements Transformer {

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

        float offsetX = getXOffset(w, f);
        float offsetY = getYOffset(h, f);

        mDst = new float[]{
                0f + offsetX, 0f + offsetY,
                w - offsetX, 0f + offsetY,
                0f, h,
                w, h};

        matrix.setPolyToPoly(mSrc, 0, mDst, 0, 4);
    }

    private float getYOffset(int h, float f) {
        if (f < 0.375f) {
            return -1.33f * h * f + 0.5f * h;
        } else if (f < 0.5f) {
            return 1.6f * h * f - 0.6f * h;
        } else if (f < 0.66f) {
            return -1.25f * h * f + 0.83f * h;
        } else if (f < 0.82f) {
            return 0.63f * h * f - 0.41f * h;
        } else if (f < 1) {
            return -0.56f * h * f + 0.56f * h;
        } else {
            return 0;
        }
    }

    private float getXOffset(int w, float f) {
        if (f < 0.375f) {
            return 0.32f * w * f - 0.12f * w;
        } else if (f < 0.5f) {
            return 0.64f * w * f - 0.24f * w;
        } else if (f < 0.66f) {
            return -0.5f * w * f + 0.33f * w;
        } else if (f < 0.82f) {
            return -0.25f * w * f + 0.17f * w;
        } else if (f < 1) {
            return 0.22f * w * f - 0.22f * w;
        } else {
            return 0;
        }
    }

}
