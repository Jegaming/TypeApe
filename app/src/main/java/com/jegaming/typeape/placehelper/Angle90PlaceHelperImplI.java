package com.jegaming.typeape.placehelper;

import android.graphics.Matrix;
import android.util.Log;

import static com.jegaming.typeape.model.Word.ANIMATION_ROTATION_LEFT;
import static com.jegaming.typeape.model.Word.ANIMATION_ROTATION_RIGHT;
import static com.jegaming.typeape.model.Word.ANIMATION_UP_ALIGN_LEFT;
import static com.jegaming.typeape.model.Word.ANIMATION_UP_ALIGN_RIGHT;
import static com.jegaming.typeape.model.Word.B;
import static com.jegaming.typeape.model.Word.R;
import static com.jegaming.typeape.model.Word.T;
import static com.jegaming.typeape.view.TypeApeView.TAG;


/**
 * Created by Jegaming on 12/5/19.
 */
public class Angle90PlaceHelperImplI extends BasePlaceHelper {

    public Angle90PlaceHelperImplI(float width, float height, int animType, float[] ltrbValues) {
        super(width, height, animType, ltrbValues);
    }

    @Override
    void init(float[] ltrbValues) {
        switch (wordAnimationType) {
            case ANIMATION_UP_ALIGN_LEFT:
                mL = ltrbValues[R];
                mB = ltrbValues[B];
                mT = mB - wordWidth;
                mR = mL + wordHeight;
                break;
            case ANIMATION_UP_ALIGN_RIGHT:
                mT = ltrbValues[T];
                mL = ltrbValues[R];
                mR = mL + wordHeight;
                mB = mT + wordWidth;
                break;
            case ANIMATION_ROTATION_LEFT:
                mL = ltrbValues[R];
                mB = ltrbValues[B];
                mR = mL + wordWidth;
                mT = mB - wordHeight;
                break;
            case ANIMATION_ROTATION_RIGHT:
                mL = ltrbValues[R];
                mT = ltrbValues[T];
                mR = mL + wordWidth;
                mB = mT + wordHeight;
                break;
        }
    }

    @Override
    public void putInPlace(Matrix matrix) {
        switch (wordAnimationType) {
            case ANIMATION_UP_ALIGN_LEFT:
                matrix.postTranslate(mL, mB);
                matrix.postRotate(-90, mL, mB);
                break;
            case ANIMATION_UP_ALIGN_RIGHT:
                matrix.postTranslate(mL, mB);
                matrix.postRotate(-90, mL, mB);
                break;
            case ANIMATION_ROTATION_LEFT:
                matrix.postTranslate(mL, mT);
                break;
            case ANIMATION_ROTATION_RIGHT:
                matrix.postTranslate(mR, mB);
                matrix.postRotate(180, mR, mB);
                break;
            default:
                Log.e(TAG, "initMatrix: invalid Animation type");
                break;
        }
    }
}
