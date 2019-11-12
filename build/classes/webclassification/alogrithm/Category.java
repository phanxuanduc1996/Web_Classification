/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webclassification.alogrithm;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author THANH
 */
public class Category {

    public Category(String sName) {
        this.sName = sName;
        mapWordsCat = new HashMap<>();
    }

    /**
     *
     * @return tên của category
     */
    public String getName() {
        return sName;
    }

    /**
     * lấy ra tổng số văn bản trong category
     *
     * @return tổng số văn bản
     */
    public int getSumDocs() {
        return sumDocs;
    }

    public void resetSumDocs(int sumDocs) {
        this.sumDocs = sumDocs;
    }

    /**
     * lấy tổng số các từ trong category, tính cả trùng
     *
     * @return tổng số từ
     */
    public int getSumWords() {
        return sumWords;
    }

    /**
     * lấy ra số lượng của 1 từ trong category
     *
     * @param word từ cần lấy ra số lượng
     * @return số lượng
     */
    public int getFreqWord(String word) {
        int freq = 0;
        if (mapWordsCat.containsKey(word)) {
            freq = mapWordsCat.get(word);
        }
        return freq;
    }

    // thêm 1 HashMap từ 1 document
    public void addHashMapAsDocument(HashMap<String, Integer> mapWordsDoc) {
        for (Map.Entry<String, Integer> entry : mapWordsDoc.entrySet()) {
            String word = entry.getKey();
            int freq = mapWordsDoc.get(word);
            sumWords += freq;
            if (mapWordsCat.containsKey(word)) {
                freq += mapWordsCat.get(word);
                mapWordsCat.replace(word, freq);
            } else {
                mapWordsCat.put(word, freq);
            }
        }
        sumDocs++;
    }

    public HashMap<String, Integer> getHashMapWords() {
        return mapWordsCat;
    }

    private final String sName;
    private final HashMap<String, Integer> mapWordsCat;
    private int sumWords;
    private int sumDocs;
}
