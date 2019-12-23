package com.jegaming.typeape.placehelper;

import android.graphics.Matrix;
import android.util.Log;

import static com.jegaming.typeape.model.Word.ANIMATION_ROTATION_LEFT;
import static com.jegaming.typeape.model.Word.ANIMATION_ROTATION_RIGHT;
import static com.jegaming.typeape.model.Word.ANIMATION_UP_ALIGN_LEFT;
import static com.jegaming.typeape.model.Word.ANIMATION_UP_ALIGN_RIGHT;
import static com.jegaming.typeape.model.Word.L;
import static com.jegaming.typeape.model.Word.R;
import static com.jegaming.typeape.model.Word.T;
import static com.jegaming.typeape.view.TypeApeView.TAG;


/**
 * Created by Jegaming on 12/5/19.
 */
public class Angle180PlaceHelperImplI extends BasePlaceHelper {

    public Angle180PlaceHelperImplI(float width, float height, int animType, float[] ltrbValues) {
        super(width, height, animType, ltrbValues);
    }

    @Override
    void init(float[] ltrbValues) {
        switch (wordAnimationType) {
            case ANIMATION_UP_ALIGN_LEFT:
                mR = ltrbValues[R];
                mB = ltrbValues[T];
                mL = mR - wordWidth;
                mT = mB - wordHeight;
                break;
            case ANIMATION_UP_ALIGN_RIGHT:
                mL = ltrbValues[L];
                mB = ltrbValues[T];
                mR = mL + wordWidth;
                mT = mB - wordHeight;
                break;
            case ANIMATION_ROTATION_LEFT:
                mR = ltrbValues[R];
                mB = ltrbValues[T];
                mL = mR - wordHeight;
                mT = mB - wordWidth;
                break;
            case ANIMATION_ROTATION_RIGHT:
                mL = ltrbValues[L];
                mB = ltrbValues[T];
                mR = mL + wordHeight;
                mT = mB - wordWidth;
                break;
        }
    }

    @Override
    public void putInPlace(Matrix matrix) {
        switch (wordAnimationType) {
            case ANIMATION_UP_ALIGN_LEFT:
                matrix.postTranslate(mR, mB);
                matrix.postRotate(180, mR, mB);
                break;
            case ANIMATION_UP_ALIGN_RIGHT:
                matrix.postTranslate(mR, mB);
                matrix.postRotate(180, mR, mB);
                break;
            case ANIMATION_ROTATION_LEFT:
                matrix.postTranslate(mL, mB);
                matrix.postRotate(-90, mL, mB);
                break;
            case ANIMATION_ROTATION_RIGHT:
                matrix.postTranslate(mR, mT);
                matrix.postRotate(90, mR, mT);
                break;
            default:
                Log.e(TAG, "initMatrix: invalid Animation type");
                break;
        }
    }
}
