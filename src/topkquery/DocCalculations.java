/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topkquery;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author nawshad
 */
public class DocCalculations {  
    public static int docScore(String[] queryTerms, int curDoc, List<String> termIDs, Term[] terms){
        int docScore = 0;
        for(String queryterm: queryTerms){
           int termObjectIndex = termIDs.indexOf(queryterm);
           if(terms[termObjectIndex].getPostingList().containsKey(curDoc)){
               docScore += terms[termObjectIndex].getPostingList().get(curDoc);
           }
        }
        return docScore;
    }
   
    public static int docUB(String[] queryTerms, int curDoc, List<String> termIDs, Term[] terms, int Const){
        int docUB = 0;
        for(String queryTerm: queryTerms){
            int termObjectIndex = termIDs.indexOf(queryTerm);
            if(terms[termObjectIndex].getCurrentDID()>=curDoc){
                if(terms[termObjectIndex].getPostingList().containsKey(curDoc)){
                    docUB += terms[termObjectIndex].termUpperBound(Const);
                }   
            }
        }
        return docUB;
    }

    public static boolean allDocsProcessed(String[] queryTerms, List<String> termIDs, Term[] terms){
        boolean allProcessed = true;
        for(String queryterm: queryTerms){
            int termObjectIndex = termIDs.indexOf(queryterm);
            if(terms[termObjectIndex].getCurrentPointer()<(terms[termObjectIndex].getPostingList().size()-1)){
                allProcessed = false;
                break;
            }
        }    
        return allProcessed;
    }
}
