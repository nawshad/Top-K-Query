/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topkquery;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author nawshad
 */
public class DataPreProcess {
    public static void dataPreProcess(String dataFileName) throws FileNotFoundException, IOException{
        String fileName = "Data/"+dataFileName;
        FileReader fileReader;
        fileReader = new FileReader(fileName);
        
        String line = "";
        // This will reference one line at a time
        List<String> lines = new ArrayList<>();
        
        try ( // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader)) { 
             while((line = bufferedReader.readLine()) != null) {
                    String[] splitted = line.split("\\\\n");
                    boolean addAll;
                    addAll = lines.addAll(Arrays.asList(splitted));
                }
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
        }
        
        FileWriter fos = new FileWriter("Data/DataFile.txt");
        PrintWriter dos = new PrintWriter(fos);
       
        Set<String> uniqueTerms = new HashSet<String>();  
        for(int i=0; i<lines.size(); i++){
            String[] splitted = lines.get(i).split(":|,|\\(|\\)|;|\\.| ");
            for(String terms: splitted){
                if(terms.length()>0)uniqueTerms.add(terms);
            }
        }
        String prevTerm = "";
        //Creating inverted index and print.
        for(String uTerm: uniqueTerms){
            int did = 0;
            String concatString = "";
            for(int i=0; i<lines.size(); i++){
                int count = 0;
                String[] splitted = lines.get(i).split(":|,|\\(|\\)|;|\\.| ");
                for(String term: splitted){
                    if(uTerm.equals(term)){    
                         count++;     
                    }                   
                }
                if(count>0)concatString+= "("+i+1+","+count+"),";
            } 
            dos.println(uTerm+":"+concatString);
          }
          dos.close();
          fos.close();
        }
    }
    

