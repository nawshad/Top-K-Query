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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import static topkquery.MapSort.DESC;

/**
 *
 * @author nawshad
 */
public class DAAT {
    int docCount = 0;
    double processingTime;
    public void daatCalcul(String dataFileName, String[] queryTerms, int topk) throws FileNotFoundException{
        
        DataLoading dl = new DataLoading();
        dl.loadData(dataFileName);
        
        long startTime   = System.currentTimeMillis();
        HashMap<Integer, Integer> heap = new HashMap();
        for(int curDoc=0; curDoc<dl.uniqueDIDs.size(); curDoc++){
            int docScore = 0;
            for(String queryterm: queryTerms){
                int termObjectIndex = dl.termIDs.indexOf(queryterm);
                if(dl.term[termObjectIndex].getPostingList().containsKey(dl.uniqueDIDs.toArray()[curDoc])){    
                    this.docCount++;
                    docScore += (int)dl.term[termObjectIndex].getPostingList().get(dl.uniqueDIDs.toArray()[curDoc]);
                }  
            }
            heap.put((int)dl.uniqueDIDs.toArray()[curDoc], docScore);
        }
        long endTime   = System.currentTimeMillis();
        
        int j = 0; 
        System.out.println("Top K Documents using Naive DAAT Algorithm (DocID=>DocScore):");
        Map<Integer, Integer> sortedTopKResult = MapSort.sortByComparator(heap, DESC);
        for (Map.Entry entry : sortedTopKResult.entrySet()){
            if (j++ < topk){
                System.out.println(entry.getKey()+"=>"+entry.getValue());
            }
        }
        
        this.processingTime = (endTime-startTime);
        System.out.println("Length of Unique DID Set:"+dl.uniqueDIDs.size());
        System.out.println("Total document counts: "+dl.totalDocCount);
        System.out.println("Full evaluation process invoked: "+docCount);
        //System.out.println("Saving: "+(dl.totalDocCount-docCount));  
        //System.out.println("Heap Size: "+(heap.size()-1));       
        System.out.println("Time needed: "+this.processingTime+" millisecs");
        
    }
}
