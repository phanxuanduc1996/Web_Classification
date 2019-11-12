/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webclassification.alogrithm;

import webclassification.io.*;
import java.util.HashMap;
import vn.hus.nlp.tokenizer.VietTokenizer;

/**
 *
 * @author THANH
 */
public class WordSeperater {

    public WordSeperater() {
        viDic = new Dictionary("data/words_vi.txt");
        viStopWords = new Dictionary("data/stopwords_vi.txt");
        tokenizer = new VietTokenizer();
    }

    /**
     * tách các từ trong file thành HashMap có key là từ, value là tần suất
     * trong String
     *
     * @param sFilePath đường dẫn tới file
     * @return HashMap có key là từ, value là tần suất trong String
     */
    public HashMap<String, Integer> tokenizeFile(String sFilePath) {
        String sFileContent = new FileHandler(sFilePath).read();
        return tokenizeString(sFileContent);
    }

    /**
     * tách từ trong string thành HashMap có key là từ, value là tần suất trong
     * String
     *
     * @param sContent nội dung cần tách từ
     * @return HashMap có key là từ, value là tần suất trong String
     */
    public HashMap<String, Integer> tokenizeString(String sContent) {
        HashMap<String, Integer> mapWords = new HashMap<>();
        String[] tokens = tokenizer.tokenize(sContent.toLowerCase());
        String[] words = tokens[0].split(" ");
        for (String word : words) {
            if (viDic.isContainWord(word)) {  // Kiểm tra xem từ có thuộc bộ từ điển tiếng việt không
                if (!viStopWords.isContainWord(word)) { // kiểm tra có thuộc từ điển từ dừng ko
                    if (mapWords.containsKey(word)) {
                        mapWords.replace(word, mapWords.get(word) + 1);
                    } else {
                        mapWords.put(word, 1);
                    }
                }
            }
        }
        return mapWords;
    }

    private final VietTokenizer tokenizer;
    private final Dictionary viDic;
    private final Dictionary viStopWords;

}
