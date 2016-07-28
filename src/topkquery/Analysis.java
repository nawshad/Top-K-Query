/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topkquery;

import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author nawshad
 */
public class Analysis {
    public static void analysis() throws FileNotFoundException{
        DataLoading dl = new DataLoading();
        dl.loadData("DataFile1.txt");
        //System.out.println("Listed Termids:"+dl.termIDs);
        //System.out.println("Termids List Length:"+dl.termIDs.size());
        //System.out.println("Number of documents:"+dl.uniqueDIDs.size());
        
        int chopSize = 2;
        int topK = 4;
        
        int  noOfQueries = 40;
        
        List<List<String>> chops = ChoppingList.chopped(dl.termIDs, chopSize);
        //System.out.println(chops);
        
        double totalCostSavings=0;
        double costSavings = 0;
        double computTimeSavings = 0;
        double totalComputTimeSavings = 0;
        for(int i=0; i<noOfQueries; i++){
            //System.out.println(chops.get(i));
            String[] queryTerms = new String[chopSize];
            for(int j=0; j<chops.get(i).size();j++){
                queryTerms[j] = chops.get(i).get(j);
            }
            TopKAlgorithm topkObj = new  TopKAlgorithm();
            topkObj.topK("DataFile1.txt", queryTerms, topK, 1, 1);
            DAAT daat = new DAAT();
            daat.daatCalcul("DataFile1.txt", queryTerms, topK);
            
            //System.out.println("For DAAT, docCount:"+daat.docCount);
            //System.out.println("For WAND, docCount:"+topkObj.docCount);
            
            costSavings = (daat.docCount-topkObj.docCount);
            totalCostSavings += ((costSavings/daat.docCount))*100;
            //System.out.println(costSavings);
            //costSavings = (daat.docCount-topkObj.docCount);
            //totalCostSavings+=costSavings;
            computTimeSavings = (daat.processingTime-topkObj.processingTime);
            totalComputTimeSavings += ((computTimeSavings/daat.processingTime))*100;
            
            //System.out.println("ComputTimeSavings:"+computTimeSavings);
            
        }
        System.out.println("Cost Savings:"+(totalCostSavings/noOfQueries));
        System.out.println("Time Savings:"+(totalComputTimeSavings/noOfQueries));
        
        //System.out.println("For query length "+chopSize+" Avg Cost Savings:"+(double)costSavings/noOfQueries*100+"%");
        
        /*String fileName = "DataFile1.txt";
        String[] queryTerms = {"roll", "commission", "fast", "exce"};
        //String[] queryTerms = {"t1","t2"};
        TopKAlgorithm topkObj = new  TopKAlgorithm();
        topkObj.topK(fileName, queryTerms, topK, 1, 2);
        DAAT daat = new DAAT();
        daat.daatCalcul(fileName, queryTerms, topK);*/
        
        /*for(int i=0; i<381;i++){
            if(dl.term[i].postingList.size()>4 && dl.term[i].postingList.size()<=5){
                System.out.println("Termid:"+dl.termIDs.get(i));
            }
        }*/
    }
}
