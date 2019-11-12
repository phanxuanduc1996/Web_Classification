/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webclassification.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import webclassification.alogrithm.Algorithm;

/**
 *
 * @author Duc Phan
 */
public class FileHandler {

    public FileHandler(String sFilePath) {
        file = new File(sFilePath);
    }
    
    public FileHandler(File file) {
        this.file = file;
    }

    // đọc file ra String
    public String read() {
        String sContent = "";
        try {
            try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {
                @SuppressWarnings("UnusedAssignment")
                String line = "";
                while ((line = br.readLine()) != null) {
                    sContent += line;
                    sContent += '\n';
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "File không hợp lệ!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            Algorithm.checkFile = false;
            return null;
        } 
        Algorithm.checkFile = true;
        return sContent.toLowerCase();
    }

    private final File file;
}
