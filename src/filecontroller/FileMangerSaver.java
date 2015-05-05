/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filecontroller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import filemanager.FileManager;

/**
 *
 * @author Ahmed Zaher
 */
 
public class FileMangerSaver {
    public static void save(FileManager fileManger ){
      try
      {
         FileOutputStream fileOut =
         new FileOutputStream( fileManger.name+".bin");
          
         ObjectOutputStream out = new ObjectOutputStream(fileOut); 
        
         out.writeObject( fileManger);
         
         out.close();
         fileOut.close();
 
      }catch(IOException i)
      {
          System.out.println("Error Saving");
          i.printStackTrace();
 
      }        
    }
}
