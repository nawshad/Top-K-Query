/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topkquery;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nawshad
 */
public class ChoppingList {
        static <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }
}
