/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topkquery;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author nawshad
 */
public class TopKQuery {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {        
         Scanner input = new Scanner(System.in);
         while (input.hasNext()) {
            String line = input.nextLine();
            String[] queryTerms = line.split(" ");
            String fileName = "data.txt";
            int topK = 6;   
            int numSkips = 1;
            int upConst = 1;
            
            TopKAlgorithm topkObj = new  TopKAlgorithm();
            DAAT daat = new DAAT();
            DataPreProcess.dataPreProcess("RealData.txt");
            if(args.length==1){
                topkObj.topK(args[0], queryTerms, topK, numSkips, upConst);
                daat.daatCalcul(args[0], queryTerms, topK);
            }
            
            if(args.length == 3){
                topkObj.topK(args[0], queryTerms, Integer.parseInt(args[2]), numSkips, upConst);
                daat.daatCalcul(args[0], queryTerms, Integer.parseInt(args[2]));
            }
            
            if(args.length == 5){
                topkObj.topK(args[0], queryTerms, Integer.parseInt(args[2]), Integer.parseInt(args[4]), upConst);
                daat.daatCalcul(args[0], queryTerms, Integer.parseInt(args[2]));
            }
            
            if(args.length == 7){
                topkObj.topK(args[0], queryTerms, Integer.parseInt(args[2]), Integer.parseInt(args[4]), Integer.parseInt(args[6]));
                daat.daatCalcul(args[0], queryTerms, Integer.parseInt(args[2]));
            }  
         }
         
        //Analysis.analysis();
    }       
}
    

