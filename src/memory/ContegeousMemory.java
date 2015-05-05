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
public class ContegeousMemory extends Memory {

    int res[]; /*el result :D */
    int ret[]=new int[2];

    public ContegeousMemory(int size) {
        super(size);
    }
    
     
   @Override
    public int[] allocate(int no_blocks) {
 
 
        res = new int[no_blocks];
        int counter = 0, max_counter = 0;/* max_counter for the worst fit*/
 
        int pos1 = 0, pos2 = 0; /* pos1 for the first fit ||pos2 for the worst fit*/
 
        /* */
        for (int i = 0; i < arr.length; i++) {
 
            if (arr[i] == false) {
                ++counter;
                max_counter = counter;
            } else {
                counter = 0;
            }
 
            if (counter > no_blocks) {
                pos2 = i;
            } else if (counter == no_blocks) {
                pos1 = i;
            }
        }
        //System.out.println(pos1 + " " + pos2 + " " + counter + " " + max_counter);
        /*worest fit*/
        if (pos2 > 0) {
            int temp;
            temp = Math.abs(pos2+1 - max_counter);
 
            for (int i = temp, j = 0; i < temp + no_blocks; ++i) { 
                arr[i]=true;
                res[j++] = i;
            } 
            ret[0] = res[0];
            ret[1] = res.length;
            free -= res.length;
            return ret;
        } else if (pos1 > 0) {       /*First fit*/
 
            int temp = pos1 - no_blocks;
            for (int i = temp, j = 0; i < temp + no_blocks; ++i) {
                res[j++] = i;
            }
            ret[0] = res[0];
            ret[1] = res.length;
            free -= res.length;
            return ret;
        }
 
        /*there is no free space*/
        return null;
    }
 
 
    @Override
    public void deallocate(int[] pos) { 
        for (int i = pos[0]; i < (pos[0] + pos[1]); i++) { 
            arr[i] = false;
            
        } 
        free+= pos[1];
    }
}
