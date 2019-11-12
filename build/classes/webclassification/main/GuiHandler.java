/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webclassification.main;

import webclassification.alogrithm.*;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author THANH
 */
public class GuiHandler {

    public static GuiHandler getInstance() {
        if (instance == null) {
            instance = new GuiHandler();
        }
        return instance;
    }

    public static GuiHandler getInstance(Gui gui) {
        if (instance == null) {
            instance = new GuiHandler(gui);
        }
        return instance;
    }

    public static GuiHandler instance;

    public GuiHandler() {
        algorithm = new Algorithm();
    }

    public GuiHandler(Gui gui) {
        algorithm = new Algorithm();
        this.gui = gui;
    }

    public void train(String sFolderTrainPath) {
        Thread threadTrain = new Thread() {
            @Override
            public void run() {
                try {
                    algorithm.train(sFolderTrainPath);
                } catch (Exception ex) {

                }
                if (Algorithm.checkTrain) {
                    JOptionPane.showMessageDialog(null, "Đã Train xong!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
        threadTrain.start();
    }

    public void test(String sFolderTestPath) {
        Thread threadTest = new Thread() {
            @Override
            public void run() {
                try {
                    algorithm.test(sFolderTestPath);
                } catch (Exception ex) {

                }
                if (Algorithm.checkTest) {
                    JOptionPane.showMessageDialog(null, "Đã Test xong!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
        threadTest.start();
    }

    public void saveToXml(String sFilePath) {
        if (sFilePath == null || sFilePath.equals("")) {
            return;
        }
        algorithm.saveToXml(sFilePath);
        if (Algorithm.checkFile2) {
            JOptionPane.showMessageDialog(null, "Đã lưu xong xml!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void loadFromXml(String sFilePath) {
        if (sFilePath == null || sFilePath.equals("")) {
            return;
        }
        algorithm.loadFromXml(sFilePath);
        if (Algorithm.checkFile1) {
            JOptionPane.showMessageDialog(null, "Đã load xong xml", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void showPredicDoc(String sFilePath, String sCatName, String sPredicCat) {
        String sTrueFalse = "False";
        int i = 1;
        if (sCatName.equalsIgnoreCase(sPredicCat)) {
            sTrueFalse = "True";
        } else {
            i++;
        }
        gui.showResultToTable(sFilePath, sCatName, sPredicCat, sTrueFalse);
    }

    /**
     * hiển thị kết quả Precision và Recall
     *
     * @param maPre Macro Precision
     * @param maRe Macro Recall
     * @param miPre Micro Precision
     * @param miRe Micro Recall
     */
    public void showPrecisionRecall(double maPre, double maRe, double miPre, double miRe) {
        String sMaPre = String.valueOf(round(maPre));
        String sMaRe = String.valueOf(round(maRe));
        String sMiPre = String.valueOf(round(miPre));
        String sMiRe = String.valueOf(round(miRe));
        gui.showPrecisionRecall(sMaPre, sMaRe, sMiPre, sMiRe);
    }

    // làm tròn số double, lấy 2 số sau dấu phẩy
    private double round(double number) {
        DecimalFormat df = new DecimalFormat("#.###");
        return Double.valueOf(df.format(number));
    }

    private Algorithm algorithm;
    private Gui gui;

    void predicFile(String sFilePath) {
        if (sFilePath.equals("")) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn file!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String sCatName = algorithm.predicFile(sFilePath);
        if (Algorithm.checkFile) {
            JOptionPane.showMessageDialog(null,
                    "File: " + sFilePath
                    + "\nĐược phân loại vào: " + sCatName,
                    "Phân loại", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void showSumTestTrueFalse(int sumTest, int sumTrue, int sumFalse) {
        String sSumTest = String.valueOf(sumTest);
        String sSumTrue = String.valueOf(sumTrue);
        String sSumFalse = String.valueOf(sumFalse);
        gui.showSumTestTrueFalse(sSumTest, sSumTrue, sSumFalse);
    }

    void predicUrl(String sUrl) {
        String sCatName = algorithm.predicUrl(sUrl);
        if (sCatName != null) {
            JOptionPane.showMessageDialog(null,
                    "Url: " + sUrl
                    + "\nĐược phân vào loại : " + sCatName,
                    "Phân loại", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
