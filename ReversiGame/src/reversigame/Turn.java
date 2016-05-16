/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package reversigame;

import static java.lang.Math.random;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    
    public boolean playableBox(int i,int j,int value){//regarde si la case donnée (i,j) peut -etre joué par le joueur value
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
    
    
    public void doTurn(){//effectue le tour d'un joueur
        boolean done=false;
        int [] tab;
        this.display();
        ArrayList <Integer> playable=playableBoxes(cont.getValue());
        if (cont instanceof HumanController){
                                  
            System.out.println("Positions jouables:");
            for(int i = 0; i < playable.size(); i+=2) {   
                System.out.print("("+playable.get(i)+","+playable.get(i+1)+"),");
            }  
            
            System.out.println("Choisissez où jouer");
            
                    tab = chosePlaceTextHuman();
            }
            else{
                try {
                //on attend pour que le joueur puisse observer (en ms)
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Turn.class.getName()).log(Level.SEVERE, null, ex);
                }
                tab= chosePlaceAI();         
        }
            playBox(tab[0],tab[1]);
        
    }

    private int[] chosePlaceTextHuman() {//permet de choisir ou le joueur voudra jouer
        ArrayList <Integer> playable=playableBoxes(cont.getValue());
        Scanner sc = new Scanner(System.in);
        int val1=0;
        int val2=0;
        try{// on s'assure que le texte est bien un entier
            String str = sc.nextLine();
            val1 = Integer.parseInt(str);
            String str2 = sc.nextLine();
            val2 = Integer.parseInt(str2);
        }
        catch(Exception e){
            System.out.println("Veuillez choisir parmis les chois proposés");
            return chosePlaceTextHuman();
        }
        

        
        for(int i=0;i<playable.size();i+=2){// et on vérifie que la case donnée est bien jouable
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
    private int [] chosePlaceAI1(int profondeur) {
        return AIPlay(profondeur,cont.getValue());
    }
    
    
    public void display(){
        System.out.println("\n   0 1 2 3 4 5 6 7");
        int level=3;
        for(int i=0;i<8;i++){
            System.out.print(i+"  ");
            for(int j=0;j<8;j++){
                if(board.getGame()[i][j].getPawn()==0)
                    if (playableBox(i,j,cont.getValue())){                     
                            System.out.print("+ ");
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
    
    public boolean canPlay(){//on regarde si un joueur peut jouer à ce moment
        return (!playableBoxes(cont.getValue()).isEmpty());
    }


    public void playBox(int i, int j){//On place un pion et on retourner ceux que l'on doit retourner
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
     

    public int countBoxDirection(int i, int j,int iInc,int jInc,int value){
        int count=0;
        int iSave=i;
        int jSave=j;
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
            i=iSave;
            j=jSave;
            return count;
    }
    
    
       public int[] AIPlay(int tour,int value){
        //on compte le score maximal que peut faire l'adversaire
   
        int max_val = -100;
        ArrayList<Integer> possibleMoves=playableBoxes(value);
        int iPlay,jPlay,val,iBest=0,jBest=0;
        for(int iMove=0;iMove<possibleMoves.size();iMove+=2){
                iPlay=possibleMoves.get(iMove);
                jPlay=possibleMoves.get(iMove+1);
                Board board2=board.save();
                playBox(iPlay,jPlay); 
                val=min(board2,tour,value,iPlay,jPlay);
                if(val>max_val){
                    max_val=val;
                    iBest=iPlay;
                    jBest=jPlay;
                }
                board.reset(board2);
        }
        int [] tab={iBest,jBest};
        return tab;
       
    }
       
       public int min(Board tab,int tour,int value,int i,int j){
           Board board1=board.save();
           int iPlay=0;
           int jPlay=0;
           int val=tab.score(value);
           ArrayList<Integer> possibleMoves=playableBoxes(reverse(value));
           if (tour==0)
               return val;
           int min_val=100;
           for(int iMove=0;iMove<possibleMoves.size();iMove+=2){
                iPlay=possibleMoves.get(iMove);
                jPlay=possibleMoves.get(iMove+1);
                playBox(i,j);
                Board board2=board.save();
                val=max(board2,--tour,value,iPlay,jPlay);
                if(val<min_val){
                    min_val=val;
                }
                }
                board.reset(board1);
                
                return min_val;
        }
       
       public int max(Board  tab,int tour,int value,int i,int j){
           if (tour<=0)
               return tab.score(value);
           Board board1=board.save();
           int iPlay=0;
           int jPlay=0;
           int val=tab.score(value);
           ArrayList<Integer> possibleMoves=playableBoxes(value);
           if (tour==0)
               return val;
           int min_val=100;
           for(int iMove=0;iMove<possibleMoves.size();iMove+=2){
                iPlay=possibleMoves.get(iMove);
                jPlay=possibleMoves.get(iMove+1);
                playBox(i,j);
                Board tab3=board.save();
                val=min(tab3,--tour,value,iPlay,jPlay);
                if(val<min_val){
                    min_val=val;
                }
                }
                board.reset(tab);
                
                return min_val;
        }
           

       public boolean oobBox(int i, int j){//vérifie que la case est dans les limites
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

    private int[] chosePlaceAI() {//On choisit quel IA doit jouer
        AIController a=((AIController) cont);
        switch(a.getLevel()){
            case 1:
                return chosePlaceAI0();
            case 2:
                return chosePlaceAI1(1);
            case 3:
                return chosePlaceAI1(4);
            default:
                return chosePlaceAI0();
        
            
        }
    }
}

