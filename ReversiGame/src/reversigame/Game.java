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
        c1 = new HumanController(1);
        //c1=new AIController(1,3);//(num de joueur,niveau AI)
        c2=new AIController(2,2);
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
        
        board.display();
        int score1 = board.finalScore()[1];
        int score2 = board.finalScore()[2];
        System.out.println("\nScore: " + score1 + " - " + score2);
        if (score1 == score2)
            System.out.println("Tie game");
        else
            System.out.println("Player " + ((score1>score2)?1:2) + " wins !");
    }

    public Board getBoard() {
        return board;
    }
    
    
    
    
    
}
