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
public class AIController implements Controller {
    private int value;
    
        public AIController(int value) {
        this.value = value;
    }
    
     public int getValue(){
         return value;
     }
    
}
