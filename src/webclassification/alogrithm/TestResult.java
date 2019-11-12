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
public class TestResult {

    /**
     * số các ví dụ thuộc lớp ci được phân loại chính xác vào ci
     *
     * @return
     */
    public int getTP() {
        return tp;
    }

    /**
     * số các ví dụ không thuộc ci bị phân loại nhầm vào ci
     *
     * @return
     */
    public int getFP() {
        return fp;
    }

    /**
     * số các ví dụ thuộc lớp ci bị phân loại nhầm
     *
     * @return
     */
    public int getFN() {
        return fn;
    }

    /**
     * số các ví dụ thuộc lớp ci được phân loại chính xác vào ci
     */
    public void incTP() {
        tp++;
    }

    /**
     * số các ví dụ không thuộc ci bị phân loại nhầm vào ci
     */
    public void incFP() {
        fp++;
    }

    /**
     * số các ví dụ thuộc lớp ci bị phân loại nhầm
     */
    public void incFN() {
        fn++;
    }

    private int tp;
    private int fp;
    private int fn;

}
