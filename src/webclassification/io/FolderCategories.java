/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webclassification.io;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Duc Phan
 */
public class FolderCategories {

    public FolderCategories(String sFolderPath) {
        fileFolder = new File(sFolderPath);
    }

    // lấy ra 1 hashmap có key là tên category, value là danh sách các file trong category
    public HashMap<String, ArrayList<File>> getFilesInCategories() {
        HashMap<String, ArrayList<File>> mapCategories;
        mapCategories = new HashMap<>();

        if (fileFolder.isDirectory()) {
            for (File category : fileFolder.listFiles()) {
                if (category.isDirectory()) {
                    String sCatName = category.getName();
                    ArrayList listFile = getListFilesInFolder(category);
                    mapCategories.put(sCatName, listFile);
                }
            }
        }

        return mapCategories;
    }

    // lấy ra danh sách file trong thư mục
    private ArrayList<File> getListFilesInFolder(File fileFolder) {
        ArrayList<File> listFiles = new ArrayList<>();
        if (fileFolder.isDirectory()) {
            for (File file : fileFolder.listFiles()) {
                if (file.isFile()) {
                    listFiles.add(file);
                }
            }
        }
        return listFiles;
    }

    private final File fileFolder;
}
