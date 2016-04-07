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
    private Turn turn1;
    private Turn turn2;

    public Game() {
        board= new Board();
        c1=new HumanController(1);
        c2=new AIController(2);
    }
    
    public void doGame(){
        do{
            turn1= new Turn(c1,board);
            if(turn1.canPlay())        
                turn1.doTurn();
            turn2= new Turn(c2,board);
            if(turn2.canPlay())        
                turn2.doTurn();
        
        }while(turn1.canPlay() || turn2.canPlay());
    }

    public Board getBoard() {
        return board;
    }
    
    
    
    
    
}
