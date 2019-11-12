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
public class PrecisionRecall {

    public void compute(HashMap<String, TestResult> mapTestResult) {
        int n = mapTestResult.size();
        FN = new int[n];
        TP = new int[n];
        FP = new int[n];

        int i = 0;
        for (Map.Entry<String, TestResult> entry : mapTestResult.entrySet()) {
            TestResult rs = mapTestResult.get(entry.getKey());
            TP[i] = rs.getTP();
            FP[i] = rs.getFP();
            FN[i] = rs.getFN();
            i++;
        }
    }

    public double getMicroPrecision() {
        int TS = 0;
        int MS = 0;
        for (int i = 0; i < TP.length; i++) {
            TS += TP[i];
            MS += TP[i] + FP[i];
        }
        return ((1.0 * TS) / MS);
    }

    public double getMicroRecall() {
        int TS = 0;
        int MS = 0;
        for (int i = 0; i < TP.length; i++) {
            TS += TP[i];
            MS += TP[i] + FN[i];
        }
        return ((1.0 * TS) / MS);
    }

    public double getMacroPrecision() {
        double TS = 0f;
        for (int i = 0; i < TP.length; i++) {
            TS += (1.0 * TP[i]) / (TP[i] + FP[i]);
        }
        return (1.0 * TS / TP.length);
    }

    public double getMacroRecall() {
        double TS = 0f;
        for (int i = 0; i < TP.length; i++) {
            TS += (1.0 * TP[i]) / (TP[i] + FN[i]);
        }
        return (1.0 * TS / TP.length);
    }

    private int[] FN;
    private int[] TP;
    private int[] FP;
}
