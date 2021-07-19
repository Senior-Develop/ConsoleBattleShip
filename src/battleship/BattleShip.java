/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.io.IOException;

/**
 *
 * @author Prometheus
 */
public class BattleShip {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Battle battle = new Battle();
        while(battle.CONTINUOUS) {
            battle.Start();
        }
    }
}
