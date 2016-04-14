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
    
    public Box getBox(int i,int j){
        return game[i][j];
    }
    
    public void display(){
        System.out.println("\n   0 1 2 3 4 5 6 7");
        for(int i=0;i<8;i++){
            System.out.print(i+"  ");
            for(int j=0;j<8;j++){
                if(game[i][j].getPawn()==0)
                    System.out.print("- ");
                if(game[i][j].getPawn()==1)
                    System.out.print("X ");
                if(game[i][j].getPawn()==2)
                    System.out.print("O ");
            }
            System.out.println();
        }
        
    }
    
    public int[][] save(){
        int[][] tab=new int[8][8];
        for(int i=0;i<8;i++){
           for(int j=0;j<8;j++){
               tab[i][j]=game[i][j].getPawn();
           }   
        }
        return tab;
    }
    
    public void reset(int[][] tab){
        for(int i=0;i<8;i++){
           for(int j=0;j<8;j++){
               game[i][j].setPawn(tab[i][j]);
           }   
        }
    }
    
    public int[] score() {
        int[] score = new int[3];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                score[game[i][j].getPawn()]++;
            }
        }
        return score;
    }
    
}
