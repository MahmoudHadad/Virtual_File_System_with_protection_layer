/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filemanager;

import java.util.ArrayList;
import java.util.Arrays;
import static javafx.scene.input.KeyCode.T;
import memory.Memory;

/**
 *
 * @author Ahmed Abdelrheem
 */
public class FileManager   {
   public Directory root;
   public String name;
   private Memory memory;
   
   public FileManager(String name , Memory memory ){  
       root = new Directory();
       root.dirName = "root";
       this.name = name;
       this.memory = memory;
   }
   public boolean createFile(String path ,int size){ 
        String arr [] = path.split("/");
        ArrayList<String>pathList = new ArrayList<String>();
        for(int i = 0;i < arr.length;i++){
            pathList.add(arr[i]);
        }
        int [] fileLocation = memory.allocate(size);
        if(fileLocation == null ){
            System.out.println("Not Enough Memory");
            return false;
        }
        if( root.addFile(pathList , size , fileLocation ) == false ){
            memory.deallocate(fileLocation);
            return false;
        }
        else
            return true;
        
    }
    public boolean createDirectory(String path ){
        String arr [] = path.split("/");
        ArrayList<String>pathList = new ArrayList<String>();
        for(int i = 0;i < arr.length;i++){
            pathList.add(arr[i]);
        }
        return root.addDirectory(pathList);
        
    }
    public boolean deleteFile(String path){
        String arr [] = path.split("/");
        ArrayList<String>pathList = new ArrayList<String>();
        for(int i = 0;i < arr.length;i++){
            pathList.add(arr[i]);
        }
        if( root.removeFile(pathList , memory) == true ){
            return true;
        }  
        
        System.out.println("Path not found");
        return false;
    }
    public boolean deleteDirectory(String path){
        String arr [] = path.split("/");
        ArrayList<String>pathList = new ArrayList<String>();
        for(int i = 0;i < arr.length;i++){
            pathList.add(arr[i]);
        }
        return root.removeDirectory(pathList , memory );  
    }
 
    public void displayDiskStatus(){
        System.out.print("Empty Space : ");
        System.out.println(memory.getFreeSize() + "Blocks");
        
        System.out.print("Allocated Space : ");
        System.out.println(memory.getAllocatedSize() + "Blocks");
        
        System.out.println("Empty Blocks : ");
        ArrayList <Integer> freeBlocks = memory.getFreeBlocks();
        for(int i : freeBlocks)
            System.out.println(i);
        
        System.out.println("Allocated Blocks : ");
        ArrayList <Integer> allocatedBlocks = memory.getAllocatedBlocks();
        for(int i : allocatedBlocks)
            System.out.println(i);
    }
    public void displayDiskStructure(){
        root.displayStructure(0);  //zero to handle the view structure
    }

    public void saveStatus(){
        
    }
    public void loadStatus(){
        
    }

    ////////////////////////////////////////////////////////////////////////
    public Directory getDirectory(String []path, Directory r)
	{
		boolean notFount = false;
		Directory d = r;
		for(int i = 1 ; i< path.length -1 ; i++)
		{
			notFount = true;
			for(Directory dir : d.subDirectories)
			{
				if(dir.dirName.equals(path[i]))
				{
					notFount = false;
					d = dir;
					break;
				}
			}
			if(notFount)
				return null;
		}
	
		
		return d;
	}
    ////////////////////////////////////////////////////////////////
    
}
