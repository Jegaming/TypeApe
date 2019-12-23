package com.jegaming.typeape.transformer;

import android.graphics.Matrix;

/**
 * Created by Jegaming on 12/19/19.
 */
public class ExpandTransformer implements Transformer {

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

        float offsetX = getOffset(w, f);
        float offsetY = getOffset(h, f);

        mDst = new float[]{
                0f + offsetX, 0f + offsetY,
                w - offsetX, 0f + offsetY,
                0f + offsetX, h - offsetY,
                w - offsetX, h - offsetY};
        matrix.setPolyToPoly(mSrc, 0, mDst, 0, 4);
    }

    private float getOffset(int x, float f) {
        return (1 - f) * 0.2f * x;
    }


}
