/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webclassification.alogrithm;

import webclassification.io.*;
import webclassification.main.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Duc Phan
 */
public class Algorithm {

    public static boolean checkTrain = false;
    public static boolean checkTest = false;
    public static boolean checkLoad = false;
    public static boolean checkFile = false;
    public static boolean checkFile1 = false;
    public static boolean checkFile2 = false;

    public Algorithm() {
        wordSeperater = new WordSeperater();
        bayes = new NaiveBayes();
        pr = new PrecisionRecall();
    }

    /**
     * train
     *
     * @param sFolderPath đường dẫn tới thư mục chứa các thư mục con tương ứng
     * với các category
     * @throws java.lang.Exception
     */
    public void train(String sFolderPath) throws Exception {

        try {
            mapSeperatedWords = new HashMap<>();
            listCategory = new ArrayList<>();

            HashMap<String, ArrayList<File>> mapFilesInCategories;
            FolderCategories folderCategories = new FolderCategories(sFolderPath);
            mapFilesInCategories = folderCategories.getFilesInCategories();

            for (Map.Entry<String, ArrayList<File>> entry : mapFilesInCategories.entrySet()) {
                String sCatName = entry.getKey();
                System.out.println("Cat: " + sCatName);
                Category cat = new Category(sCatName);
                listCategory.add(cat);

                for (File file : entry.getValue()) {
                    String sFileContent = new FileHandler(file).read();
                    HashMap<String, Integer> mapWords;
                    //System.out.println("File: " + file.getAbsolutePath());
                    mapWords = wordSeperater.tokenizeString(sFileContent);
                    cat.addHashMapAsDocument(mapWords);
                }
            }

            for (int i = 0; i < listCategory.size(); i++) {
                HashMap<String, Integer> mapWordsInCat;
                mapWordsInCat = listCategory.get(i).getHashMapWords();
                putToSeperatedWords(mapWordsInCat);
            }

           
        } catch (Exception e) {
            checkTrain = false;
            JOptionPane.showMessageDialog(null, "Bạn chọn sai tập Train!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
         checkTrain = true;

    }

    /**
     * test
     *
     * @param sFolderPath đường dẫn tới thư mục chứa các thư mục con tương ứng
     * với các category
     * @throws java.lang.Exception
     */
    @SuppressWarnings("UnusedAssignment")
    public void test(String sFolderPath) throws Exception {

        try {

            HashMap<String, ArrayList<File>> mapFilesInCategories;
            FolderCategories folderCategories = new FolderCategories(sFolderPath);
            mapFilesInCategories = folderCategories.getFilesInCategories();

            mapTestResult = new HashMap<>();
            for (Category cat : listCategory) {
                TestResult testResult = new TestResult();
                mapTestResult.put(cat.getName(), testResult);
            }

            int sumTest = 0;
            int sumTrue = 0;
            int sumFalse = 0;

            for (Map.Entry<String, ArrayList<File>> entry : mapFilesInCategories.entrySet()) {
                String sCatName = entry.getKey();
                TestResult testResult;
                if (mapTestResult.containsKey(sCatName)) {
                    testResult = mapTestResult.get(sCatName);
                } else {
                    testResult = new TestResult();
                    mapTestResult.put(sCatName, testResult);
                }

                for (File file : entry.getValue()) {
                    String sFileContent = new FileHandler(file).read();
                    HashMap<String, Integer> mapWords;
                    mapWords = wordSeperater.tokenizeString(sFileContent);
                    String sPredicCat = predictDoc(mapWords);

                    GuiHandler.getInstance().showPredicDoc(file.getAbsolutePath(), sCatName, sPredicCat);

                    sumTest++;
                    if (sCatName.equalsIgnoreCase(sPredicCat)) {
                        sumTrue++;
                        mapTestResult.get(sCatName).incTP();
                    } else {
                        sumFalse++;
                        mapTestResult.get(sCatName).incFN();
                        mapTestResult.get(sPredicCat).incFP();
                    }
                }
            }

            // hiển thị số lượng tập test, số lượng đúng, số lượng sai
            GuiHandler.getInstance().showSumTestTrueFalse(sumTest, sumTrue, sumFalse);

            // tính precision và recall
            pr.compute(mapTestResult);
            GuiHandler.getInstance().showPrecisionRecall(
                    pr.getMacroPrecision(),
                    pr.getMacroRecall(),
                    pr.getMicroPrecision(),
                    pr.getMicroRecall());
        } catch (Exception e) {
            checkTest = false;
            JOptionPane.showMessageDialog(null, "Đường dẫn sai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        checkTest = true;

    }

    public String predicFile(String sFilePath) {
        String sFileContent = new FileHandler(sFilePath).read();
        HashMap<String, Integer> mapWords;
        mapWords = wordSeperater.tokenizeString(sFileContent);
        return predictDoc(mapWords);
    }

    public String predicUrl(String sUrl) {
        HtmlParser htmlParser = new HtmlParser();
        String sContent = htmlParser.getHtml(sUrl);
        if (sContent == null) {
            return sContent;
        }
        HashMap<String, Integer> mapWords;
        mapWords = wordSeperater.tokenizeString(sContent);
        return predictDoc(mapWords);
    }

    /**
     * lưu dữ liệu đã học ra xml
     *
     * @param sFilePath đường dẫn tới file xml
     */
    public void saveToXml(String sFilePath) {
        XmlHandler xmlHandler = new XmlHandler(sFilePath);
        xmlHandler.write(listCategory);
    }

    /**
     * tải dư liệu đã học từ xml
     *
     * @param sFilePath đường dẫn tới file xml
     */
    public void loadFromXml(String sFilePath) {
        XmlHandler xmlHandler = new XmlHandler(sFilePath);
        listCategory = xmlHandler.getData();
        
        mapSeperatedWords = new HashMap<>();
        for (int i = 0; i < listCategory.size(); i++) {
            putToSeperatedWords(listCategory.get(i).getHashMapWords());
        }
        checkLoad = true;
        checkTrain = true;
    }

    private void putToSeperatedWords(HashMap<String, Integer> mapWords) {
        if (mapSeperatedWords == null) {
            mapSeperatedWords = new HashMap<>();
        }
        mapSeperatedWords.putAll(mapWords);
    }

    /**
     * dự đoán xem văn bản thuộc nhóm nào
     *
     * @param mapWords hashmap các từ trong văn bản
     * @return loại văn bản
     */
    private String predictDoc(HashMap<String, Integer> mapWords) {
        double p = -Double.MAX_VALUE;
        String sPredicCat = "";
        for (Category cat : listCategory) {
            double pDjCi = 0;
            for (Map.Entry<String, Integer> entryWord : mapWords.entrySet()) {
                int freqWord = cat.getFreqWord(entryWord.getKey());
                int sumWords = cat.getSumWords();
                int t = mapSeperatedWords.size();
                pDjCi += Math.log10(bayes.computePTjCi(freqWord, sumWords, t));
            }
            pDjCi += Math.log10(getProbabilityCi(cat.getName()));
            if (pDjCi > p) {
                p = pDjCi;
                sPredicCat = cat.getName();
            }
        }
        return sPredicCat;
    }

    private double getProbabilityCi(String sCatName) {
        int sumDocsInCat = 0;
        int totalDocsTrained = 0;
        for (int i = 0; i < listCategory.size(); i++) {
            Category cat = listCategory.get(i);
            if (cat.getName().equalsIgnoreCase(sCatName)) {
                sumDocsInCat = cat.getSumDocs();
            }
            totalDocsTrained += cat.getSumDocs();
        }
        return (1.0 * sumDocsInCat) / totalDocsTrained;
    }

    private final WordSeperater wordSeperater;
    private ArrayList<Category> listCategory;
    private HashMap<String, Integer> mapSeperatedWords;
    private HashMap<String, TestResult> mapTestResult;
    private final NaiveBayes bayes;
    private final PrecisionRecall pr;
}
