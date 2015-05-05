/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memory;

import java.util.ArrayList;

/**
 *
 * @author lenovo
 */
public abstract class Memory implements java.io.Serializable{

    protected boolean[] arr; 
    protected int free;

    public Memory() {
    }

    public Memory(int size) {
        arr = new boolean[size];
        for (int i = 0; i < size; i++) {
            arr[i] = false;
        }
        free = size;
    }

    public abstract int[] allocate(int no_blocks); 
    public abstract void deallocate(int[] no_blocks);
    public int getFreeSize(){
        return free;
    }
    public int getAllocatedSize(){
         return (arr.length-free );
    }
    public ArrayList<Integer> getFreeBlocks(){
        ArrayList<Integer> blocks = new ArrayList();
        for (int i = 0; i < arr.length; i++) 
            if (arr[i] == false) 
                 blocks.add(i);
        return blocks;
    }
    public ArrayList<Integer> getAllocatedBlocks(){
        ArrayList<Integer> blocks = new ArrayList();
        for (int i = 0; i < arr.length; i++) 
            if (arr[i] == true) 
                 blocks.add(i);
        return blocks;
    }
 
}
