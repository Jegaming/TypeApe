package com.jegaming.typeape.transformer;

import android.graphics.Matrix;

/**
 * Created by Jegaming on 12/19/19.
 */
public class DropDownTransformer implements Transformer {

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
                0f, 0f - offset,
                w, 0f - offset,
                0f, h - offset,
                w, h - offset};
        matrix.setPolyToPoly(mSrc, 0, mDst, 0, 4);
    }

    private float getOffset(int h, float f) {
        return (1 - f) * 0.2f * h;

    }


}
