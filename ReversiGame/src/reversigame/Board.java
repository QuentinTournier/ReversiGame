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
public class Board {
    private Box[][] game;

    public Board() {
        game=new Box[8][8];
        game[3][3]=new Box(1);
        game[4][4]=new Box(1);
        game[4][3]=new Box(2);
        game[3][4]=new Box(2);
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                if (game[i][j]==null)
                    game[i][j]=new Box();
                
            }
        }
    }

    public Box[][] getGame() {
        return game;
    }
    
    public void affiche(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(game[i][j].getPawn()==0)
                    System.out.print("0");
                if(game[i][j].getPawn()==1)
                    System.out.print("1");
                if(game[i][j].getPawn()==2)
                    System.out.print("2");
            }
            System.out.println();
        }
        
    }
    
}
