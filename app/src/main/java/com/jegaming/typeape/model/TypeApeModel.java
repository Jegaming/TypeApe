package com.jegaming.typeape.model;

import java.util.List;

/**
 * Created by Jegaming on 10/22/19.
 */
public class TypeApeModel {

    /**
     * 全部的文本集合
     */
    public List<Word> wordList;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" WordList:[");
        if (wordList!=null){
            for (Word wm : wordList){
                sb.append(wm+",");
            }
        }else {
            sb.append("null");
        }
        sb.append("]");

        return sb.toString();
    }
}
