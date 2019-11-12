/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webclassification.alogrithm;

/**
 *
 * @author Duc Phan
 */
public class NaiveBayes {
    /**
     * tính bayes
     * @param freqWordInTopic số lần xuất hiện của từ Tj trong Ci
     * @param totalWordsInTopic tổng số lần xuất hiện của tất cả các từ trong Ci
     * @param numberWordsInTotalCategories số các từ trong tập học (không tính trùng nhau)
     * @return xác suất của từ Tj thuộc Ci
     */
    public double computePTjCi(int freqWordInTopic, int totalWordsInTopic, int numberWordsInTotalCategories) {
        double PTjCi = ((freqWordInTopic + 1) * 1.0) / (totalWordsInTopic + numberWordsInTotalCategories);
        return PTjCi;
    }
}
