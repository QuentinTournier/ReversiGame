/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package reversigame;

import java.util.ArrayList;
import java.util.Scanner;

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
        int level= choseLevelAI();//permet de choisir parmis les 3 niveaux proposés
        c2=new AIController(2,level);//(num de joueur,niveau AI)
    }
    
    public void doGame(){
        do{//la boucle de jeu, joue jusqu'à ce que plus personne ne puisse mettre un pio,
            turn1= new Turn(c1,board);
            if(turn1.canPlay())        
                turn1.doTurn();
            turn2= new Turn(c2,board);
            if(turn2.canPlay())        
                turn2.doTurn();
        
        }while(turn1.canPlay() || turn2.canPlay());
        
        board.display();//affiche le plateau final
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

    private int choseLevelAI() {
        System.out.println("choisissez le niveau de l'AI \n 1-Facile \n 2-Moyen \n 3-Difficile");
        int level;
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        try{
            level=Integer.parseInt(str);
        }
        catch(Exception e){
            System.out.println("choisissez parmis les niveaux possibles");
            return choseLevelAI();
        }
        if (level<1 || level>3)
            return choseLevelAI();
        return level;
    }
    
    
    
    
    
}
