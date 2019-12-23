package com.jegaming.typeape.transformer;

import android.graphics.Matrix;

/**
 * Created by Jegaming on 12/2/19.
 */
public interface Transformer {

    void reset();

    void doTransform(Matrix matrix, int width, int height, int mL, int mT, float timeFactor);
}
