package com.jegaming.typeape.util;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;

import com.jegaming.typeape.model.TypeApeModel;
import com.jegaming.typeape.model.Word;

import java.util.ArrayList;
import java.util.List;

import static com.jegaming.typeape.view.TypeApeView.TAG;

/**
 * Created by Jegaming on 10/23/19.
 */
public class Utils {

    public static Word createWordModel(String oneLine) {
        try {
            if (oneLine.indexOf("]") != 10) {
                Log.e(TAG, "createWordModel() error: your [time] format incorrect!");
                return null;
            }

            //time
            String timeStr = oneLine.substring(1, 10);
            long startTime = 0;
            int minute = Integer.parseInt(timeStr.substring(0, 2));
            int second = Integer.parseInt(timeStr.substring(3, 5));
            int million = Integer.parseInt(timeStr.substring(6, 9));
            startTime = minute * 60 * 1000 + second * 1000 + million;

            int lIndexProp = oneLine.indexOf("{");
            int rIndexProp = oneLine.indexOf("}");
            if (rIndexProp < lIndexProp) {
                Log.e(TAG, "createWordModel() error: your {properities} format incorrect!");
                return null;
            }

            //properties
            String prop = oneLine.substring(lIndexProp + 1, rIndexProp);
            prop = prop.replace(" ", "");
            if (TextUtils.isEmpty(prop))
                prop = ",,,,";

            //text
            String text = oneLine.substring(rIndexProp + 1, oneLine.length());

            Word.Builder builder = new Word.Builder(text, startTime);

            //transformTime
            int transformTimeIndex = prop.indexOf(",", 0);
            if (transformTimeIndex > 0) {
                long transformTime = Long.parseLong(prop.substring(0, transformTimeIndex));
                builder.transformTime(transformTime);
            }

            //transformType
            int transformTypeIndex = prop.indexOf(",", transformTimeIndex + 1);
            String transformType = prop.substring(transformTimeIndex + 1, transformTypeIndex);
            builder.transformType(WordPropertyParser.getWordTransformType(transformType));

            //transformType
            int animateTypeIndex = prop.indexOf(",", transformTypeIndex + 1);
            String animateType = prop.substring(transformTypeIndex + 1, animateTypeIndex);
            builder.animationType(WordPropertyParser.getWordAnimationType(animateType));

            //textSize
            int textSizeIndex = prop.indexOf(",", animateTypeIndex + 1);
            if (textSizeIndex > animateTypeIndex + 1) {
                int textSize = Integer.parseInt(prop.substring(animateTypeIndex + 1, textSizeIndex));
                builder.textSize(textSize);
            }

            //textColor
            int textColorIndex = prop.length();
            String textColor = prop.substring(textSizeIndex + 1, textColorIndex);
            if (!TextUtils.isEmpty(textColor)) {
                builder.textColor(Color.parseColor(textColor));
            }

            return builder.build();

        } catch (NumberFormatException e) {
            Log.e(TAG, "createWordModel() error: your input number format incorrect!");
            return null;
        } catch (StringIndexOutOfBoundsException e) {
            Log.e(TAG, "createWordModel() error: your input string format incorrect!");
            return null;
        } catch (ArithmeticException e) {
            Log.e(TAG, "createWordModel() error: your word color format incorrect!");
            return null;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "createWordModel() error: color illegal");
            return null;
        }
    }

    public static TypeApeModel createTypeApeModel(String sourceStr) {
        long startTime = System.currentTimeMillis();

        List<Word> wordList = new ArrayList<>();
        String[] lines = sourceStr.split("\n");
        for (int i = 0; i < lines.length; i++) {
            Word word = createWordModel(lines[i]);
            if (word != null) {
                wordList.add(word);
            } else {
                Log.e(TAG, "createTypeApeModel() error: data format error");
                return null;
            }
            //检验是否有时间问题
            if (wordList.size() > 1) {
                Word penult = wordList.get(wordList.size() - 2);
                Word last = wordList.get(wordList.size() - 1);
                if (last.getStartTime() - penult.getStartTime() < 200) {
                    Log.e(TAG, "createTypeApeModel() error: a word's startTime must larger than prior one's ,at least 200ms");
                }
                if (penult.getStartTime() + penult.getTransformTime() > last.getStartTime()) {
                    Log.e(TAG, "createTypeApeModel() error: a word's transformTime must end before next one start");
                }
            }
        }
        Log.d(TAG, "createTypeApeModel(): string parsed in " + (System.currentTimeMillis() - startTime) + "ms");
        TypeApeModel typeApeModel = new TypeApeModel();
        typeApeModel.wordList = wordList;
        return typeApeModel;
    }

}
