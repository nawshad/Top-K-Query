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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author nawshad
 */
public class DataLoading { 
    List<String> termIDs = new ArrayList<String>();
    int line_no = 0;
    int totalDocCount = 0;
    Term[] term = new Term[1700];
    Set<Integer> uniqueDIDs = new HashSet<Integer>();
        
    
    public  void loadData(String dataFileName) throws FileNotFoundException{
        for(int x = 0; x < term.length; x++){
            term[x] = new Term();
        }
        
        String fileName = "Data/"+dataFileName;
        FileReader fileReader;
        fileReader = new FileReader(fileName);
        
       
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            //System.out.println("List of All Terms: ");            
            while((line = bufferedReader.readLine()) != null) { 
                String[] words = line.split(":|,|\\(|\\)");
                //System.out.print(words[0]+"\n");
                this.termIDs.add(words[0]);
                for(int i=2; i<words.length; i++){
                    if(i%2==0&&words[i].length()>0){ 
                        String key = words[i];
                        int DID = Integer.parseInt(key);
                        this.term[line_no].addToPostingList(DID, Integer.parseInt(words[i+1]));
                        this.uniqueDIDs.add(DID);
                    }
                } 
                this.line_no++;
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
        
        this.totalDocCount = this.uniqueDIDs.size();
    }
}
