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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import static topkquery.MapSort.ASC;
import static topkquery.MapSort.DESC;

/**
 *
 * @author nawshad
 */
public class TopKAlgorithm {
    int docCount = 0;
    double processingTime = 0;
    public void topK(String dataFileName, String[] queryTerms, int topk, int numberSkips, int Const) throws FileNotFoundException{         
        HashMap<Integer, Integer> heap = new HashMap();
        //int docCount = 0;
        int curDoc = 0;
        int threshold = 0;
        int iter = 0;
        int pivotTerm = 0;

        DataLoading dl = new DataLoading();
        dl.loadData(dataFileName);
        
        //System.out.println("Number of Terms:"+dl.termIDs.size());
        //System.out.println("Number of Documents:"+dl.uniqueDIDs.size());

        List<Integer> li = new ArrayList<>();
        li.add(0);
        
        long startTime = System.currentTimeMillis();
        while(true){
            //Sort terms bases in theor DIDs.
            Map<Integer, Integer> unsortMap = new HashMap();
            for(String terms : queryTerms){
                int termObjectIndex = dl.termIDs.indexOf(terms);
                int getCurrentPointer = dl.term[termObjectIndex].getCurrentPointer();
                int getCurrentPostingSize = dl.term[termObjectIndex].getPostingList().size();     
                if(getCurrentPointer<getCurrentPostingSize){
                    unsortMap.put(termObjectIndex, dl.term[termObjectIndex].getCurrentDID());//(termObjectIndex,DID) 
                }    
            }
            
            Map<Integer, Integer> sortedMapDesc = MapSort.sortByComparator(unsortMap, ASC);
            List<Integer> sortedTermsDIDs = MapSort.getValuesFromMap(sortedMapDesc);
            List<Integer> sortedTerms = MapSort.getKeysFromMap(sortedMapDesc);
            
            //Find pivotTerm
            int ubForTerm = 0;      
            for(int termObjectIndex : sortedTerms){
                ubForTerm  += dl.term[termObjectIndex].termUpperBound(Const);
                if(ubForTerm > threshold){
                    pivotTerm = termObjectIndex;
                    break;
                }else{
                    dl.term[termObjectIndex].movePtr(numberSkips);
                }       
            }
            curDoc = dl.term[pivotTerm].getCurrentDID();
            int docUB = DocCalculations.docUB(queryTerms, curDoc, dl.termIDs, dl.term, Const);
            
            /*If pivoTerms Upperbound greater than threshold put calculate doc score and save doc and its score 
            in a heap, update scoring list*/
            if(docUB > threshold){
                this.docCount++;
                //calculate the document score 
                int docScore = DocCalculations.docScore(queryTerms, curDoc, dl.termIDs, dl.term);
                heap.put(curDoc, docScore); 
                //put in a list 
                li.add(docScore);
                Collections.sort(li);
                if(li.size()==topk){
                    li.remove(li.get(0));
                }
                threshold = li.get(0); 
            }
            
            dl.term[pivotTerm].movePtr(numberSkips);
            iter++; 
            if(DocCalculations.allDocsProcessed(queryTerms,dl.termIDs,dl.term)){break;}
        }
        long endTime   = System.currentTimeMillis();
         
        int j = 0; 
        System.out.println("Top K Documents using WAND Algorithm (DocID=>DocScore):");
        Map<Integer, Integer> sortedTopKResult = MapSort.sortByComparator(heap, DESC);
        for (Map.Entry entry : sortedTopKResult.entrySet()){
            if (j++ < topk){
                System.out.println(entry.getKey()+"=>"+entry.getValue());
            }
        }
           
        
        this.processingTime = (endTime-startTime);
        System.out.println("Total document counts: "+dl.totalDocCount);
        System.out.println("Full evaluation process invoked: "+docCount);
        //System.out.println("Saving: "+(dl.totalDocCount-docCount));
        System.out.println("Number of iteration: " +iter);   
        //System.out.println("Heap Size: "+(heap.size()-1));       
        System.out.println("Time needed:"+this.processingTime+" millisecs");     
    }             
}
         
 
     

