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
public class Box {

    private int pawn;

    public Box() {
        this(0);
    }

    public Box(int pawn) {
        this.pawn = pawn;
    }
    public void reset(){
        this.pawn=0;
    }
    
    public Boolean isEmpty(){//vérifie  si la case est à 0, c'est à dire si aucun joueur n' y a joué
        return (pawn==0);
    }
    
    public void affect(int value){//met la case à la valeur donnée
        if (this.isEmpty()){
            if (value==1 || value==2)
            {
                 pawn=value;
            }
        }
    }
    
    public void reversi(){//change la valeur du pion en la valeur de l'adversaire
            if (pawn==1)
                pawn=2;
            else if(pawn==2)
                pawn=1;        
    }

    public int getPawn() {
        return pawn;
    }

    public void setPawn(int pawn) {
        this.pawn = pawn;
    }
    
       
    
    
    
    
}
