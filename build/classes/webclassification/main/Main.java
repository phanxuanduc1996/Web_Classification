/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webclassification.main;

import javax.swing.JFrame;

/**
 *
 * @author THANH
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
               Gui gui = new Gui();
        gui.setVisible(true);
        gui.setResizable(false);
        gui.setTitle("Chương Trình Phân Loại Văn Bản");
        gui.setLocation(100, 50);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        

    }
}
