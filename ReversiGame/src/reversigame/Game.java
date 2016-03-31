/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package reversigame;

import java.util.ArrayList;

/**
 *
 * @author p1306434
 */
public class Game {
    private Controller c1;
    private Controller c2;
    private Board board;
    private Boolean turn;

    public Game() {
        board= new Board();
        c1=new HumanController(1);
        c2=new HumanController(2);
    }
    
    public void doGame(){
        Turn turn= new Turn(c1,board);
        ArrayList<Integer> playable=turn.playableBoxes();
        for(int i = 0; i < playable.size(); i+=2) {   
            System.out.print("("+playable.get(i)+","+playable.get(i+1)+")");
        }  
        board.affiche();
    }
    
    
    
    
    
}
