package com.jegaming.typeape.util;

import static com.jegaming.typeape.model.Word.ANIMATION_ROTATION_LEFT;
import static com.jegaming.typeape.model.Word.ANIMATION_ROTATION_RIGHT;
import static com.jegaming.typeape.model.Word.ANIMATION_UP_ALIGN_LEFT;
import static com.jegaming.typeape.model.Word.ANIMATION_UP_ALIGN_RIGHT;
import static com.jegaming.typeape.model.Word.TYPE_DPD;
import static com.jegaming.typeape.model.Word.TYPE_EPD;
import static com.jegaming.typeape.model.Word.TYPE_FUP;
import static com.jegaming.typeape.model.Word.TYPE_LAR;
import static com.jegaming.typeape.model.Word.TYPE_LEF;
import static com.jegaming.typeape.model.Word.TYPE_TRA;
import static com.jegaming.typeape.model.Word.TYPE_UAD;
import static com.jegaming.typeape.model.Word.TYPE_ZOM;

/**
 * Created by Jegaming on 12/20/19.
 */
public class WordPropertyParser {

    public static int getWordTransformType(String abbr) {
        switch (abbr) {
            case "UAD":
                return TYPE_UAD;
            case "TRA":
                return TYPE_TRA;
            case "EPD":
                return TYPE_EPD;
            case "ZOM":
                return TYPE_ZOM;
            case "DPD":
                return TYPE_DPD;
            case "LEF":
                return TYPE_LEF;
            case "LAR":
                return TYPE_LAR;
            case "FUP":
                return TYPE_FUP;
            case "RAN":
                return (int) (Math.random() * 7 + 1);
            default:
                return 0;
        }
    }

    public static int getWordAnimationType(String abbr) {
        switch (abbr) {
            case "UL":
                return ANIMATION_UP_ALIGN_LEFT;
            case "UR":
                return ANIMATION_UP_ALIGN_RIGHT;
            case "RL":
                return ANIMATION_ROTATION_LEFT;
            case "RR":
                return ANIMATION_ROTATION_RIGHT;
            default:
                return ANIMATION_UP_ALIGN_LEFT;
        }
    }
}
