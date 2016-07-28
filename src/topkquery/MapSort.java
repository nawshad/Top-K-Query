/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topkquery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nawshad
 */
public class MapSort {
    public static boolean ASC = true;
    public static boolean DESC = false;
    
    public static Map<Integer, Integer> sortByComparator(Map<Integer, Integer> unsortMap, final boolean order)
    {

        List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>()
        {
            public int compare(Map.Entry<Integer, Integer> o1,
                    Map.Entry<Integer, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    
    public static List<Integer> getValuesFromMap(Map<Integer, Integer> relScoreMap){       
        List<Integer> values = new ArrayList<Integer>();
        for (Map.Entry<Integer, Integer> entry : relScoreMap.entrySet()) {
            values.add(entry.getValue());
        }
        return values;
    } 
    
     public static List<Integer> getKeysFromMap(Map<Integer, Integer> relScoreMap){       
        List<Integer> keys = new ArrayList<Integer>();
        for (Map.Entry<Integer, Integer> entry : relScoreMap.entrySet()) {
            keys.add(entry.getKey());
        }
        return keys;
    } 
    
}
