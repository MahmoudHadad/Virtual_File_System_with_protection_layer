/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filemanager;

import java.util.ArrayList;
import memory.Memory;

/**
 *
 * @author Ahmed Abdelrheem
 */
public class Directory implements java.io.Serializable{
    public String dirName;
    public ArrayList<File> files;
    public ArrayList<Directory>subDirectories;

    public Directory() {
        files = new ArrayList<File>();
        subDirectories = new ArrayList<Directory>();
    }
    
    
    private boolean haveDir(String name){
        for(int i = 0;i < subDirectories.size();i++){
            if(subDirectories.get(i).dirName .equals(name) ){
                return true;
            }
        }
        return false;
   }
    private boolean haveFile(String name){
        for(int i = 0;i < files.size();i++){
            if(files.get(i).fileName .equals(name) ){
                return true;
            }
        }
        return false;
    }
    
    public boolean addFile(ArrayList<String> pathList , int fileSize , int [] fileLocation){
        if(!(pathList.get(0).equals(dirName))){ 
            System.out.println("Path not found");
            return false;
        }
            
        pathList.remove(0);
        if(pathList.size() == 1){
            if(!haveFile(pathList.get(0))){
                File f = new File() ;
                f.fileName = pathList.get(0);
                f.size = fileSize;
                f.location = fileLocation;
                files.add(f); 
                return true; 
            }
            //name exist
            System.out.println("Name exist");
            return false;
        }
        for(int i = 0 ; i < subDirectories.size() ; i ++ ){
            if( subDirectories.get(i).dirName.equals(pathList.get(0)))
                return subDirectories.get(i).addFile(pathList , fileSize , fileLocation );
        } 
        return false;
    }
    
    
    public boolean addDirectory(ArrayList<String> pathList){
        if(!(pathList.get(0).equals(dirName)))
            return false;
        
        pathList.remove(0); 
        if(pathList.size() == 1){
            if(!haveDir(pathList.get(0))){
                Directory d = new Directory();
                d.dirName = pathList.get(0);
                
                subDirectories.add(d);
                return true;
                //construor
            }else
                return false;
        }
 
        for(int i = 0 ; i < subDirectories.size() ; i ++ ){
            if( subDirectories.get(i).dirName.equals(pathList.get(0)))
            return subDirectories.get(i).addDirectory(pathList);
        }
        return false;
    }
    public boolean removeFile(ArrayList<String> pathList , Memory memory) {
        
        if (!(pathList.get(0).equals(dirName))) {
            return false;
        }
        pathList.remove(0);
        if (pathList.size() == 1) { 
            for (int i = 0; i < files.size(); i++) {
                if (files.get(i).fileName.equals(pathList.get(0))) {
                    memory.deallocate( files.get(i).location );
                    files.remove(i); 
                    return true;
                } 
            }
            
            return false;
        }
        for (int i = 0; i < subDirectories.size(); i++) {
            if (subDirectories.get(i).dirName.equals(pathList.get(0))) {
                return subDirectories.get(i).removeFile(pathList , memory);
            }
        }
        return false;
    }
 
    public boolean removeDirectory(ArrayList<String> pathList , Memory memory) {
        
        if (!(pathList.get(0).equals(dirName))) {
            return false;
        }
        pathList.remove(0);
        if (pathList.size() == 1) { 
            for (int i = 0; i < subDirectories.size(); i++) {
                if (subDirectories.get(i).dirName.equals(pathList.get(0))) {
                    subDirectories.get(i).delete(memory);
                    subDirectories.remove(i);
                    return true;
                }
            }
            
            return false;
        }
        
        for (int i = 0; i < subDirectories.size(); i++) {
            if (subDirectories.get(i).dirName.equals(pathList.get(0))) {
                return subDirectories.get(i).removeDirectory(pathList , memory);
            }
        }
        return false;
    }
 
 
    public void displayStructure(int numOfSpaces){
        for(int i = 0 ; i < numOfSpaces ; i ++ )
            System.out.print(" ");
        System.out.println(dirName);
        for(int i = 0;i < subDirectories.size();i++  ){ 
             
            subDirectories.get(i).displayStructure(numOfSpaces+1);
        }
        for(int i = 0;i < files.size();i++ ){
            for(int j = 0 ; j <= numOfSpaces ; j ++ )
                System.out.print(" ");           
            System.out.println(files.get(i).fileName);
        }
    }
    private void delete(Memory memory){
       
        for(File f : files)
            memory.deallocate(f.location);
         files.clear();
        for(int i = 0 ; i < subDirectories.size() ; i ++ )
            subDirectories.get(i).delete(memory);
        subDirectories.clear();
    }
    
}
