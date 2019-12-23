package com.jegaming.typeape.placehelper;

import android.graphics.Matrix;
import android.util.Log;

import static com.jegaming.typeape.model.Word.ANIMATION_ROTATION_LEFT;
import static com.jegaming.typeape.model.Word.ANIMATION_ROTATION_RIGHT;
import static com.jegaming.typeape.model.Word.ANIMATION_UP_ALIGN_LEFT;
import static com.jegaming.typeape.model.Word.ANIMATION_UP_ALIGN_RIGHT;
import static com.jegaming.typeape.model.Word.B;
import static com.jegaming.typeape.model.Word.L;
import static com.jegaming.typeape.model.Word.T;
import static com.jegaming.typeape.view.TypeApeView.TAG;


/**
 * Created by Jegaming on 12/5/19.
 */
public class Angle270PlaceHelperImplI extends BasePlaceHelper {

    public Angle270PlaceHelperImplI(float width, float height, int animType, float[] ltrbValues) {
        super(width, height, animType, ltrbValues);
    }

    @Override
    void init(float[] ltrbValues) {
        switch (wordAnimationType) {
            case ANIMATION_UP_ALIGN_LEFT:
                mR = ltrbValues[L];
                mT = ltrbValues[T];
                mL = mR - wordHeight;
                mB = mT + wordWidth;
                break;
            case ANIMATION_UP_ALIGN_RIGHT:
                mR = ltrbValues[L];
                mB = ltrbValues[B];
                mL = mR - wordHeight;
                mT = mB - wordWidth;
                break;
            case ANIMATION_ROTATION_LEFT:
                mR = ltrbValues[L];
                mT = ltrbValues[T];
                mL = mR - wordWidth;
                mB = mT + wordHeight;
                break;
            case ANIMATION_ROTATION_RIGHT:
                mR = ltrbValues[L];
                mB = ltrbValues[B];
                mT = mB - wordHeight;
                mL = mR - wordWidth;
                break;
        }
    }

    @Override
    public void putInPlace(Matrix matrix) {
        switch (wordAnimationType) {
            case ANIMATION_UP_ALIGN_LEFT:
                matrix.postTranslate(mR, mT);
                matrix.postRotate(90, mR, mT);
                break;
            case ANIMATION_UP_ALIGN_RIGHT:
                matrix.postTranslate(mR, mT);
                matrix.postRotate(90, mR, mT);
                break;
            case ANIMATION_ROTATION_LEFT:
                matrix.postTranslate(mR, mB);
                matrix.postRotate(180, mR, mB);
                break;
            case ANIMATION_ROTATION_RIGHT:
                matrix.postTranslate(mL, mT);
                break;
            default:
                Log.e(TAG, "initMatrix: invalid Animation type");
                break;
        }
    }
}
