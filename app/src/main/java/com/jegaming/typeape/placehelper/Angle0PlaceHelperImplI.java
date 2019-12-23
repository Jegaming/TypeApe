package com.jegaming.typeape.placehelper;

import android.graphics.Matrix;

import static com.jegaming.typeape.model.Word.ANIMATION_ROTATION_LEFT;
import static com.jegaming.typeape.model.Word.ANIMATION_ROTATION_RIGHT;
import static com.jegaming.typeape.model.Word.ANIMATION_UP_ALIGN_LEFT;
import static com.jegaming.typeape.model.Word.ANIMATION_UP_ALIGN_RIGHT;
import static com.jegaming.typeape.model.Word.B;
import static com.jegaming.typeape.model.Word.L;
import static com.jegaming.typeape.model.Word.R;
import static com.jegaming.typeape.model.Word.T;


/**
 * Created by Jegaming on 12/5/19.
 */
public class Angle0PlaceHelperImplI extends BasePlaceHelper {

    public Angle0PlaceHelperImplI(float width, float height, int animType, float[] ltrbValues) {
        super(width, height, animType, ltrbValues);
    }

    @Override
    void init(float[] ltrbValues) {
        switch (wordAnimationType) {
            case ANIMATION_UP_ALIGN_LEFT:
                mL = ltrbValues[L];
                mT = ltrbValues[B];
                mR = mL + wordWidth;
                mB = mT + wordHeight;
                break;
            case ANIMATION_UP_ALIGN_RIGHT:
                mR = ltrbValues[R];
                mL = mR - wordWidth;
                mT = ltrbValues[B];
                mB = mT + wordHeight;
                break;
            case ANIMATION_ROTATION_LEFT:
                mL = ltrbValues[L];
                mT = ltrbValues[B];
                mR = mL + wordHeight;
                mB = mT + wordWidth;
                break;
            case ANIMATION_ROTATION_RIGHT:
                mR = ltrbValues[R];
                mL = mR - wordHeight;
                mT = ltrbValues[B];
                mB = mT + wordWidth;
                break;
            default://首个word
                mL = ltrbValues[L];
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
                matrix.postTranslate(mL, mT);
                break;
            case ANIMATION_UP_ALIGN_RIGHT:
                matrix.postTranslate(mL, mT);
                break;
            case ANIMATION_ROTATION_LEFT:
                matrix.postTranslate(mR, mT);
                matrix.postRotate(90, mR, mT);
                break;
            case ANIMATION_ROTATION_RIGHT:
                matrix.postTranslate(mL - wordWidth, mT);
                matrix.postRotate(-90, mL, mT);
                break;
            default:
                matrix.postTranslate(mL, mT);
                break;
        }
    }

}
