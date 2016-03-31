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
        this.display();
        ArrayList <Integer> playable=playableBoxes();
        System.out.println("Positions jouables:");
        for(int i = 0; i < playable.size(); i+=2) {   
            System.out.print("("+playable.get(i)+","+playable.get(i+1)+"),");
        }  
        System.out.println("Choisissez où jouer");
        while (!done){
            if(cont instanceof HumanController)
                done= chosePlaceTextHuman();
            else{
                done= chosePlaceTextHuman();
            }
            if(!done){
                System.out.println("mauvais choix");
            }
                  
        }     
    }

    private boolean chosePlaceTextHuman() {
        ArrayList <Integer> playable=playableBoxes();
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        int val1 = Integer.parseInt(str);
        String str2 = sc.nextLine();
        int val2 = Integer.parseInt(str2);
        
        for(int i=0;i<playable.size();i+=2){
            if(val1==playable.get(i)){
                if (val2==playable.get(i+1))
                    return true;
            }
        }
        return false;
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



}

