/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author Prometheus
 */
class Battle {
    public final static String presentationFilePath = "constantePresentation.txt";
    public final static String PRESENTATION = 
         " ____    ____  ______   ____  ____  _      _        ___ \n" +
         "|    \\  /    ||      | /    ||    || |    | |      /  _]\n" +
         "|  o  )|  o  ||      ||  o  | |  | | |    | |     /  [_ \n" +
         "|     ||     ||_|  |_||     | |  | | |___ | |___ |    _]\n" +
         "|  O  ||  _  |  |  |  |  _  | |  | |     ||     ||   [_ \n" +
         "|     ||  |  |  |  |  |  |  | |  | |     ||     ||     |\n" +
         "|_____||__|__|  |__|  |__|__||____||_____||_____||_____|\n" +
         "       ____    ____  __ __   ____  _        ___           \n" +
         "      |    \\  /    ||  |  | /    || |      /  _]          \n" +
         "      |  _  ||  o  ||  |  ||  o  || |     /  [_           \n" +
         "      |  |  ||     ||  |  ||     || |___ |    _]          \n" +
         "      |  |  ||  _  ||  :  ||  _  ||     ||   [_           \n" +
         "      |  |  ||  |  | \\   / |  |  ||     ||     |          \n" +
         "      |__|__||__|__|  \\_/  |__|__||_____||_____|    \n";
    
    public final static String CHOOSE_GAME_LEVEL_STRING =
         "Appuyez sur <ENTREE> pour continuer... \n\n" + 
         "NIVEAU DE DIFFICULTE : \n" + 
         "1. Debutant (45 munitions) \n" + 
         "2. Intermediaire (35 munitions) \n" + 
         "3. Expert (25 munitions) \n\n" + 
         "Entrez votre choix (1, 2 ou 3): ";
    
    public int GAME_LEVEL = 0;
    
    public int DOTS = 14;
    public int CUR = 0;
    public String BOARD_STR = "                                                                                                                                                         ";
    public int[][] BOARD_FLAG = new int[8][8];
    public String BOARD_MAP =   "        " + 
                                "....    " + 
                                "      . " + 
                                "      . " + 
                                " .    . " + 
                                " .      " + 
                                " .      " + 
                                "   ...  ";

    public String TOP_LINE = "\n\n    0   1   2   3   4   5   6   7 ";
    public String SPLIT_LINE = "  ---------------------------------";
    public final static String MUNITIONS_RESTANTES = "MUNITIONS RESTANTES : ";
    public int [] REMAIN_BULLETS = new int[]{ 0, 45, 35, 25 };
    public int REMAIN_BULLET = 0;
    public int SINK_COUNT = 0;
    public final static String VAISSEAUX_COULES = "VAISSEAUX COULES : ";
    public final static String REQUEST_COORDINATE = "Entrez les coordonnees du prochain tir : ";
    public String COORDINATE_STR = "";
    
    public Scanner input = new Scanner(System.in);
    
    public int ROW = 0;
    public int COL = 0;
    
    public Random rand = new Random();
    
    public Boolean CONTINUOUS = true;
    
    public void Start() {
        this.ShowBattleBoard();
        this.StartGame();
        this.DrawBattleBoard();
        
        while(true) {
            this.Process();
            this.DrawBattleBoard();
            
            if (this.CUR == this.DOTS) {
                System.out.println("\nPartie gagnée\nBRAVO! Vous avez detruit la flotte ennemie!\nMunitions utilisees : " + (this.REMAIN_BULLETS[this.GAME_LEVEL] - this.REMAIN_BULLET) + " / " + this.REMAIN_BULLETS[this.GAME_LEVEL] + "\n");
                break;
            }
            if (this.REMAIN_BULLET == 0) {
                System.out.println("\nPartie perdue\nDESOLE! Vous avez epuise toutes vos munitions!\nNombre de vaisseaux non coules : " + ((int)this.rand.nextDouble() * 4) + "\n");
                break;
            }
        }
        
        this.Continuous();
    }
    
    public void StartGame() {
        System.out.print(this.CHOOSE_GAME_LEVEL_STRING);
        String temp = this.input.nextLine();
        if (temp.length() == 1 && temp.charAt(0) > '0' && temp.charAt(0) < '4') {
            this.GAME_LEVEL = Integer.parseInt(temp);
            this.REMAIN_BULLET = this.REMAIN_BULLETS[this.GAME_LEVEL];
    //        System.out.println("Username is: " + this.GAME_LEVEL);
        } else {
            System.out.println("ERREUR! Niveau invalide... Recommencez!\n");
            this.StartGame();
        }
        
    }
    
    public void DrawBattleBoard() {
        System.out.println(this.TOP_LINE);
        System.out.println(this.SPLIT_LINE);
        
        int i, j;
        for (i = 0; i < 8; i ++) {
            System.out.print(i + " | ");
            for (j = 0; j < 8; j ++) {
                System.out.print(this.BOARD_STR.charAt(i * 8 + j) + " | ");
            }
            System.out.println("\n" + this.SPLIT_LINE);
        }
        
        System.out.println("\n" + this.MUNITIONS_RESTANTES + this.REMAIN_BULLET);
        System.out.println(this.VAISSEAUX_COULES + this.SINK_COUNT);
    }
    
    public void Process() {
        System.out.print("\n" + this.REQUEST_COORDINATE);
        this.COORDINATE_STR = this.input.nextLine();
        this.REMAIN_BULLET -= 1;
        if (
            this.COORDINATE_STR.length() == 3 &&
            this.COORDINATE_STR.charAt(1) == ',' &&
            this.COORDINATE_STR.charAt(0) < '8' &&
            this.COORDINATE_STR.charAt(2) < '8' &&
            this.COORDINATE_STR.charAt(0) >= '0' &&
            this.COORDINATE_STR.charAt(2) >= '0'
        ) {
            this.ROW = this.COORDINATE_STR.charAt(0) - '0';
            this.COL = this.COORDINATE_STR.charAt(2) - '0';
            int idx = this.ROW * 8 + this.COL;
            if (this.BOARD_FLAG[this.ROW][this.COL] == 0) {
                this.BOARD_FLAG[this.ROW][this.COL] = 1;
                if (this.BOARD_MAP.charAt(idx) == '.') {
                    this.BOARD_STR = this.BOARD_STR.substring(0, idx) + 'B' + this.BOARD_STR.substring(idx + 1);
                    System.out.print("\n\n----------> TIR REUSSI ! <----------");
                    this.CUR += 1;
                } else {
                    this.BOARD_STR = this.BOARD_STR.substring(0, idx) + 'X' + this.BOARD_STR.substring(idx + 1);
                    System.out.print("\n\n----------> TIR MANQUE ! <----------");
                }
            } else {
                System.out.print("\n\n----------> TIR REDONDANT ! <----------");
                
            }
            
        } else {
            System.out.print("\nERREUR! Coordonnees de tir invalides... Recommencez!");
            this.Process();
        }
    }
    
    public void Continuous() {
        String temp = "";
        while(true) {
            System.out.print("Voulez-vous jouer encore (oui ou non) : ");
            temp = this.input.nextLine();
            if (temp.toLowerCase().equals("non")) {
                this.CONTINUOUS = false;
                break;
            } else if (temp.toLowerCase().equals("oui")) {
                this.CONTINUOUS = true;
                break;
            } else {
                this.Continuous();
            }
        }
    }
    
    public void ShowBattleBoard() {
        System.out.println(this.PRESENTATION);
    }
    
}
