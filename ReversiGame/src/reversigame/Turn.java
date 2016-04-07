/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package reversigame;

import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author p1306434
 */
public class Turn {
    private Controller cont;
    private Board board;

    public Turn(Controller cont, Board board) {
        this.cont = cont;
        this.board = board;
    }
    
    public ArrayList playableBoxes(){
        ArrayList<Integer> tab=new ArrayList();
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++)
                if (playableBox(i,j)){
                    tab.add(i);
                    tab.add(j);      
                }
                        
        }
        return tab;
    }
    
    
    public boolean playableBox(int i,int j){
        if(board.getGame()[i][j].isEmpty()){
            for(int iInc=-1;iInc<2;iInc++){
                for(int jInc=-1;jInc<2;jInc++){
                    if(playableBoxDirection(i,j,iInc,jInc)){
                        return true;
                    }
                }
            }      
        }
        return false;
    }
   
    public boolean playableBoxDirection(int i, int j,int iInc,int jInc){
        int value=cont.getValue();
        if (jInc==0 && iInc==0)
            return false;
        i+=iInc;
        j+=jInc;
        if(i<0 || i>7 ||j<0 || j>7){
            return false;
        }
        else{
            if(board.getGame()[i][j].getPawn()==0 || board.getGame()[i][j].getPawn()==value)
                return false;
            else{
                i+=iInc;
                j+=jInc;
                while(!(i<0 || i>7 ||j<0 || j>7)){
                    int pawn=board.getGame()[i][j].getPawn();
                    if (pawn==0)
                        return false;
                    else if (pawn== value)
                        return true;
                    else {
                        i+=iInc;
                        j+=jInc;
                    }   
                }
                return false; 
            }
        }
    }
    
    
    public void doTurn(){
        boolean done=false;
        int [] tab;
        this.display();
        ArrayList <Integer> playable=playableBoxes();
        System.out.println("Positions jouables:");
        for(int i = 0; i < playable.size(); i+=2) {   
            System.out.print("("+playable.get(i)+","+playable.get(i+1)+"),");
        }  
        System.out.println("Choisissez où jouer");
            if(cont instanceof HumanController)
                tab = chosePlaceTextHuman();
            else{
                tab= chosePlaceAI1();         
        }
            playBox(tab[0],tab[1]);
        
    }

    private int[] chosePlaceTextHuman() {
        ArrayList <Integer> playable=playableBoxes();
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        int val1 = Integer.parseInt(str);
        String str2 = sc.nextLine();
        int val2 = Integer.parseInt(str2);
        
        for(int i=0;i<playable.size();i+=2){
            if(val1==playable.get(i)){
                if (val2==playable.get(i+1)){
                    int tab[]={val1,val2};
                    return tab;
                }
            }
        }
        System.out.println("mauvais choix");
        return chosePlaceTextHuman();
    }
    
    
    // AI0 choses a random position among those available.
    private int[] chosePlaceAI0() {
        ArrayList <Integer> playable=playableBoxes();
        int[] play = new int[2];
        int place = (int)(random()*playable.size()/2)*2;
        play[0] = playable.get(place);
        play[1] = playable.get(place+1);
        
        return play;
    }
    
    
    
    // AI1 choses the position which captures the most pawns.
    private int[] chosePlaceAI1() {
        ArrayList<Integer> countBox = countBox();
        int[] play = new int[2];
        int max = -1;
        for (int i = 0; i < countBox.size(); i+=3) {
            if (countBox.get(i+2) > max) {
                play[0] = countBox.get(i);
                play[1] = countBox.get(i+1);
                max = countBox.get(i+2);
            }
        }
        return play;
    }
    
    
    public void display(){
        System.out.println("\n   0 1 2 3 4 5 6 7");
        for(int i=0;i<8;i++){
            System.out.print(i+"  ");
            for(int j=0;j<8;j++){
                if(board.getGame()[i][j].getPawn()==0)
                    if (playableBox(i,j))
                        System.out.print("+ ");
                    else
                        System.out.print("- ");
                if(board.getGame()[i][j].getPawn()==1)
                    System.out.print("X ");
                if(board.getGame()[i][j].getPawn()==2)
                    System.out.print("O ");
            }
            System.out.println();
        }
        
    }
    
    public boolean canPlay(){
        return (!playableBoxes().isEmpty());
    }


    public void playBox(int i, int j){
        int iBoucle;
        int jBoucle;
        ArrayList <Integer> direction=playableBoxAllDirection(i,j);
        board.getGame()[i][j].affect(cont.getValue());
        for (int nb=0;nb<direction.size();nb+=2){
            iBoucle=i+direction.get(nb);
            jBoucle=j+direction.get(nb+1);        
            do{
                board.getGame()[iBoucle][jBoucle].reversi();
                iBoucle+=direction.get(nb);
                jBoucle+=direction.get(nb+1);
            }while(board.getGame()[iBoucle][jBoucle].getPawn()!=cont.getValue()&& board.getGame()[iBoucle][jBoucle].getPawn()!=0);
        }
    }
    
    public ArrayList<Integer> playableBoxAllDirection(int i,int j){
        ArrayList<Integer> tab=new ArrayList();
        if(board.getGame()[i][j].isEmpty()){
            for(int iInc=-1;iInc<2;iInc++){
                for(int jInc=-1;jInc<2;jInc++){
                    if(playableBoxDirection(i,j,iInc,jInc)){
                        tab.add(iInc);
                        tab.add(jInc);
                    }
                }
            }      
        }
        return tab;
    }
    
    public boolean isCorner(int i,int j){
        if(i==0 || i==7)
            if(j==0 || j==7)
                return true;
        return false;
    }
    
     public ArrayList countBox (){
         ArrayList<Integer> tab=playableBoxes();
         ArrayList<Integer> countBoxes=new ArrayList();
         for(int i=0;i<tab.size();i+=2){
             countBoxes.add(tab.get(i));
             countBoxes.add(tab.get(i+1));
             countBoxes.add(count(tab.get(i),tab.get(i+1)));    
         }
         return countBoxes;
     }
    
    public int count (int i,int j){
        int count=0;
        ArrayList<Integer> countTab=new ArrayList();
        if(board.getGame()[i][j].isEmpty()){
            for(int iInc=-1;iInc<2;iInc++){
                for(int jInc=-1;jInc<2;jInc++){
                    count+=countBoxDirection(i,j,iInc,jInc);
                }
            }      
        }
        return count;
    }

    public int countBoxDirection(int i, int j,int iInc,int jInc){
        int value=cont.getValue();
        int count=0;
            if (playableBoxDirection(i,j,iInc,jInc)){
                i+=iInc;
                j+=jInc;
                while(!(i<0 || i>7 ||j<0 || j>7)){
                    int pawn=board.getGame()[i][j].getPawn();
                    if (pawn==0)
                        return count;
                    else if (pawn== value)
                        return count;
                    else {
                        i+=iInc;
                        j+=jInc;
                        count ++;
                    }   
                }
            }
            return count;
    }

}

