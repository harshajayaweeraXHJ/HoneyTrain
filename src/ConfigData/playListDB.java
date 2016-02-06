/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ConfigData;

import java.io.File;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Xsoad
 */
public class playListDB implements Serializable{
    
  
   public int maxIndex = 0;
   public Vector playListFiles;

    public playListDB() {
        this.playListFiles = new Vector(10, 1);
    }
    
    
    
    
    
    
}
