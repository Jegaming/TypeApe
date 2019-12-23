package com.jegaming.typeape.transformer;


import android.graphics.Matrix;

import static com.jegaming.typeape.model.Word.TYPE_DPD;
import static com.jegaming.typeape.model.Word.TYPE_EPD;
import static com.jegaming.typeape.model.Word.TYPE_FUP;
import static com.jegaming.typeape.model.Word.TYPE_LAR;
import static com.jegaming.typeape.model.Word.TYPE_LEF;
import static com.jegaming.typeape.model.Word.TYPE_TRA;
import static com.jegaming.typeape.model.Word.TYPE_UAD;
import static com.jegaming.typeape.model.Word.TYPE_ZOM;

/**
 * Created by Jegaming on 12/2/19.
 */
public class TransformHelper {

    static Transformer mTransformer;
    static boolean needReset = true;

    public static void doTransform(int type, Matrix matrix, int width, int height, int mL, int mT, float timeFactor) {
        Class transformClazz;
        switch (type) {
            case TYPE_UAD:
                transformClazz = UpAndDownTransformer.class;
                break;
            case TYPE_TRA:
                transformClazz = TrapezoidTransformer.class;
                break;
            case TYPE_EPD:
                transformClazz = ExpandTransformer.class;
                break;
            case TYPE_ZOM:
                transformClazz = ZoomTransformer.class;
                break;
            case TYPE_DPD:
                transformClazz = DropDownTransformer.class;
                break;
            case TYPE_LEF:
                transformClazz = LeafTransformer.class;
                break;
            case TYPE_LAR:
                transformClazz = LeftAndRightTransformer.class;
                break;
            case TYPE_FUP:
                transformClazz = FlipUpTransformer.class;
                break;
            default:
                return;
        }


        try {
            if (mTransformer == null || !transformClazz.isInstance(mTransformer)) {
                mTransformer = (Transformer) transformClazz.newInstance();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        doTransform(matrix, width, height, 0, 0, timeFactor);

    }

    private static void doTransform(Matrix matrix, int width, int height, int mL, int mT, float timeFactor) {
        if (timeFactor < 1 && needReset) {
            mTransformer.reset();
            needReset = false;
        } else if (timeFactor >= 1) {
            needReset = true;
        }

        mTransformer.doTransform(matrix, width, height, mL, mT, timeFactor);
    }

}
