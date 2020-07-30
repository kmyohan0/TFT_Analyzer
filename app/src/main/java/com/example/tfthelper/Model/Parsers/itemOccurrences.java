package com.example.tfthelper.Model.Parsers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class itemOccurrences {

    private List<Integer> items = new ArrayList<>();

    public itemOccurrences(List<Integer> items) {
        this.items = items;
        sortItems();
    }

    public void sortItems() {
        //sort using Hashmap
        HashMap<Integer, Integer> occurrences = new HashMap<>();
        for (Integer i : items) {
            boolean ifAdded = false;
            for (Map.Entry<Integer, Integer> elements : occurrences.entrySet()) {
                if (elements.getKey() == i) {
                    ifAdded = true;
                    elements.setValue(elements.getValue() + 1);
                }
            }
            if (!ifAdded) {
                occurrences.put(i, 1);
            }
        }
        List<Map.Entry<Integer, Integer> > list =
                new LinkedList<Map.Entry<Integer, Integer> >(occurrences.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() {
            public int compare(Map.Entry<Integer, Integer> o1,
                               Map.Entry<Integer, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        items = new ArrayList<>();
        for (Map.Entry<Integer, Integer> i : temp.entrySet()) {
             items.add(i.getKey());
        }
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }
}
