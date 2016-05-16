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

    public void setGame(Box[][] game) {
        this.game = game;
    }
    public void setBox(int i,int j,int value){//associe une valeur à une case
        game[i][j].setPawn(value);
    }
    
    public void display(){//affiche le plateau,
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
    
    public Board save(){//sauvegarde le plateau dans un plateau qu'elle retourne
        Board tab=new Board();
        for(int i=0;i<8;i++){
           for(int j=0;j<8;j++){
               tab.setBox(i,j,game[i][j].getPawn());
           }   
        }
        return tab;
    }
    
    public void reset(Board tab){//remet le plateau à la valeur indiquée en paramètre
        for(int i=0;i<8;i++){
           for(int j=0;j<8;j++){
               this.setBox(i,j,tab.getBox(i,j).getPawn());
           }   
        }
    }
    
    public int[] finalScore() {//calcul le score de fin de partie
        int[] score = new int[3];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                score[game[i][j].getPawn()]++;
            }
        }
        return score;
    }
    
    public int score(int team) {//calcul les scores actuels
        int[] score = new int[3];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.isCorner(i, j))
                    score[game[i][j].getPawn()]+=5;
                else if(this.isCloseCorner(i, j))
                    score[game[i][j].getPawn()]-=3;
                else    
                    score[game[i][j].getPawn()]++;
            }
        }
        return score[team];
    }
    
    
    public boolean isCorner(int i,int j){//vérifie si la case à ces coordonées est un coin
        if(i==0 || i==7)
            if(j==0 || j==7)
                return true;
        return false;
    }
    
    public boolean isCloseCorner(int i, int j){//vérifie si la case à ces coordonées "touche" une case qui est un coin
        if(i==1|| i==6){
            if(j==0 || j==7 || j==1 || j==6)
                return true;
            }
        else if(i==0|| i==7){
            if(j==0 || j==7 || j==1 || j==6)
                return true;
            }
            return false;
    
    }
}
