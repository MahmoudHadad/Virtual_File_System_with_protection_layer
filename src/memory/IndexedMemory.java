/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memory;

/**
 *
 * @author lenovo
 */
public class IndexedMemory extends Memory {

    public int pos[];
    public IndexedMemory(int size) {
        super(size);
    }
    @Override
    public int[] allocate(int no_blocks) {
        pos = new int[no_blocks+1];
        int counter = 0;
 
        for (int i = 0; i < arr.length; i++) {
 
            if (arr[i] == false) {
                pos[counter++] = i;
            }
            if (counter == (no_blocks+1)) {
                break;
            }
        }
 
        if (counter == (no_blocks+1)) {
            free-=no_blocks+1;
            System.out.println(counter +" "+pos.length);
            for(int i=0;i<pos.length;++i)
                arr[pos[i]]=true;
            return pos;
        }
        return null;
    }

    @Override
    public void deallocate(int[] res) {
        for (int i = 0; i < res.length; ++i) {
            arr[res[i]]=false;
        }
        free+=res.length;
         
    }
}
