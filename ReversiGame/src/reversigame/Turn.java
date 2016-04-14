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
    
    public ArrayList playableBoxes(int value){
        ArrayList<Integer> tab=new ArrayList();
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++)
                if (playableBox(i,j,value)){
                    tab.add(i);
                    tab.add(j);      
                }
                        
        }
        return tab;
    }
    
    
    public boolean playableBox(int i,int j,int value){
        if(board.getGame()[i][j].isEmpty()){
            for(int iInc=-1;iInc<2;iInc++){
                for(int jInc=-1;jInc<2;jInc++){
                    if(playableBoxDirection(i,j,iInc,jInc,value)){
                        return true;
                    }
                }
            }      
        }
        return false;
    }
   
    public boolean playableBoxDirection(int i, int j,int iInc,int jInc,int value){    
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
        ArrayList <Integer> playable=playableBoxes(cont.getValue());
        System.out.println("Positions jouables:");
        for(int i = 0; i < playable.size(); i+=2) {   
            System.out.print("("+playable.get(i)+","+playable.get(i+1)+"),");
        }  
        System.out.println("Choisissez où jouer");
            if(cont instanceof HumanController)
                tab = chosePlaceTextHuman();
            else{
                try {
                    Thread.sleep(3000);     //milliseconds
                } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
                }
                tab= chosePlaceAI1();         
        }
            playBox(tab[0],tab[1]);
        
    }

    private int[] chosePlaceTextHuman() {
        ArrayList <Integer> playable=playableBoxes(cont.getValue());
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
        ArrayList <Integer> playable=playableBoxes(cont.getValue());
        int[] play = new int[2];
        int place = (int)(random()*playable.size()/2)*2;
        play[0] = playable.get(place);
        play[1] = playable.get(place+1);
        
        return play;
    }
    private int[] chosePlaceAI1() {
        ArrayList<Integer> countBox = countBox(cont.getValue());
        int[] play = new int[2];
        play[0] = countBox.get(0);
        play[1] = countBox.get(1);
        float max = countBox.get(2);
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
        int level=3;
        for(int i=0;i<8;i++){
            System.out.print(i+"  ");
            for(int j=0;j<8;j++){
                if(board.getGame()[i][j].getPawn()==0)
                    if (playableBox(i,j,cont.getValue())){
                        if(cont instanceof HumanController)
                            System.out.print("+ ");
                        else
                            System.out.print(countIfHere(i,j,level,cont.getValue(),true)+" ");
                    }                       
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
        return (!playableBoxes(cont.getValue()).isEmpty());
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
                    if(playableBoxDirection(i,j,iInc,jInc,cont.getValue())){
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
    
     public ArrayList countBox (int value){
         int AILevel=5;
         ArrayList<Integer> tab=playableBoxes(value);
         ArrayList<Integer> countBoxes=new ArrayList();
         for(int i=0;i<tab.size();i+=2){
             countBoxes.add(tab.get(i));
             countBoxes.add(tab.get(i+1));
             countBoxes.add(countIfHere(tab.get(i),tab.get(i+1),AILevel,cont.getValue(),true));    
         }
         return countBoxes;
     }
    
    //return the score of the box
    public int count (int i,int j,int value){
        int count=0;
        count+=countPosition(i,j);
        if(board.getGame()[i][j].isEmpty()){
            for(int iInc=-1;iInc<2;iInc++){
                for(int jInc=-1;jInc<2;jInc++){
                    count+=countBoxDirection(i,j,iInc,jInc,value);
                }
            }      
        }
        return count;
    }

    public int countBoxDirection(int i, int j,int iInc,int jInc,int value){
        int count=0;
            if (playableBoxDirection(i,j,iInc,jInc,value)){
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

    private int countPosition(int i, int j) {
        if (isCorner(i,j))
            return 10;
        else if(isCloseCorner(i,j)){
            return -4;
        }
        else
            return 0;
            
    }
    
    public boolean isCloseCorner(int i, int j){
        if(i==1|| i==6){
            if(j==0 || j==7)
                return true;
            }
        else if(i==0|| i==7){
            if(j==1 || j==6)
                return true;
            }
            return false;
    
    }
       public int countIfHere(int i,int j,int tour,int value,boolean isTurn){
        //on compte le score maximal que peut faire l'adversaire
        if(oobBox(i,j))
            return 0;
        value=reverse(value);

        int tab[][]=board.save();
        int iPlay=0;
        int jPlay=0;
        playBox(i,j);
        int count=0;
        int countCurr=0;
        ArrayList<Integer> possibleMoves=playableBoxes(value);
        for(int iMove=0;iMove<possibleMoves.size();iMove+=2){
                iPlay=possibleMoves.get(iMove);
                jPlay=possibleMoves.get(iMove+1);
                count=count(iPlay,jPlay,value);
                /*if(tour >=0)
                    count+=countIfHere(iPlay,jPlay,--tour,value,!isTurn);*/
                if (isTurn){
                    if (count>countCurr)
                    countCurr=count;
                }
                else
                    if (count>-countCurr)
                    countCurr=-count;
        }
        board.reset(tab);
        return count(iPlay,jPlay,value);
    }

       public boolean oobBox(int i, int j){
           if(i<0||i>7||j>7||j<0)
               return true;
           return false;
       }
       
       public int reverse(int value){
           if (value==1)
               return 2;
           else if (value==2)
               return 1;
           else return 0;
       }
}

