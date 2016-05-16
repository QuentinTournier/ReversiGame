/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package reversigame;

/**
 *
 * @author p1306434
 */
//Cette classe permet de gérer les intelligences artificielles, value sera leur numéro de joueur et level leur niveau
public class AIController implements Controller {
    private int value;
    private int level;
    
        public AIController(int value) {
        this.value = value;
    }
    
     public int getValue(){
         return value;
     }

    public AIController(int value, int level) {
        this.value = value;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
    
     
     
    
}
