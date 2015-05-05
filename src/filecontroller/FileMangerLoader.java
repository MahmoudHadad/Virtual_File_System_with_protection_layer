/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filecontroller;

import filemanager.FileManager;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed Zaher
 */
public class FileMangerLoader {

    public static FileManager load(String fileName) {
        FileManager manger = null;
        try {
            FileInputStream fileInput
                    = new FileInputStream(fileName);

            ObjectInputStream in = new ObjectInputStream(fileInput);

            manger = (FileManager)in.readObject();

            in.close();
            fileInput.close();

        } catch (IOException i) {
            System.out.println("Not found");
            return null;
             

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileMangerLoader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return manger;
    }
}
