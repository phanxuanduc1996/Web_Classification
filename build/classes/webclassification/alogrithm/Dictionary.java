/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webclassification.alogrithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THANH
 */
public class Dictionary {

    public Dictionary(String sFilePath) {
        wordsList = new ArrayList<>();
        readFileToArray(sFilePath);
    }

    private void readFileToArray(String sFilePath) {
        FileReader fr;
        BufferedReader br;
        String sLine = "";
        try {
            // mở file
            fr = new FileReader(sFilePath);
            br = new BufferedReader(fr);

            // đọc file
            while ((sLine = br.readLine()) != null) {
                wordsList.add(sLine);
            }

            // đóng file
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // kiểm tra 1 từ có trong từ điển
    public boolean isContainWord(String sWord) {
        return wordsList.contains(sWord);
    }

    private ArrayList<String> wordsList;
}
