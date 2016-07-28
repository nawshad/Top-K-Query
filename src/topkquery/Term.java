/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topkquery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import static jdk.nashorn.internal.objects.NativeArray.map;

/**
 *
 * @author nawshad
 */
public class Term {
    public int did = 0;
    public int frequency = 0;
    public int pointer = 0;
    public int num_of_moves = 0;
    TreeMap<Integer,Integer> postingList = new TreeMap<Integer, Integer>();

    public void addToPostingList(int key, int value){   
         this.postingList.put(key, value);
    }
    
    public int getNumberOFMoves(){   
        return this.num_of_moves;
    }
    
    public TreeMap<Integer,Integer> getPostingList(){
         return this.postingList;
    }
    
    public void movePtr(int numberSkips){
        this.pointer+=numberSkips;
        if(this.pointer>this.postingList.size()){
            this.pointer = this.postingList.size();
        }
    }
    
    public int getCurrentPointer(){
        return this.pointer;
    }
    
    public void setCurrentPointer(int curDoc, int numSkips){
        while(this.getCurrentDID()<curDoc && this.pointer < this.getPostingList().size()){
            System.out.println("Inside Set Current Pointer");
            this.num_of_moves++;
            this.pointer+=numSkips;
        }
    }
     
    public int getCurrentDID(){
        int currentDID=0;
        LinkedList<Integer> keys = new LinkedList<Integer>();
        for (Map.Entry<Integer, Integer> entry : this.postingList.entrySet()) {
            keys.add(entry.getKey());
        }
        if(this.getCurrentPointer()>this.getPostingList().size()-1){
            currentDID = keys.get(this.getPostingList().size()-1);
        }
        else{
            currentDID = keys.get(this.getCurrentPointer());
        }
        return currentDID ;
    }

    public int termUpperBound(int Const){
        Iterator iterator = this.postingList.entrySet().iterator();
        int n = -1;
        while(iterator.hasNext()){
            Entry entry = (Entry) iterator.next();
            if((int)entry.getValue() > n){
                 n = (int)entry.getValue();
            }
            
        }  
        return n*Const;
    }
    
}
