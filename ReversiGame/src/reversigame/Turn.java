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
}

