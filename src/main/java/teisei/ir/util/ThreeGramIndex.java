package teisei.ir.util;

import com.google.common.collect.Multiset;

import java.util.*;

/**
 * Created by Teisei on 2016/3/13.
 */
public class ThreeGramIndex {
    String dicPath;
    Map<String, HashSet<String>> index;

    public ThreeGramIndex(String dicPath) {
        this.dicPath = dicPath;
        constructIndex();
    }

    private void constructIndex() {
        index = new HashMap<>();
        Set<Multiset.Entry<String>> wordSet = TrainingSetGenerator.buildWordSet(dicPath);
        for (Multiset.Entry<String> w : wordSet) {
            String word = "$" + w.getElement() + "$";
            for (int i = 0; i < word.length() - 2; i++) {
                String k = word.substring(i, i + 3);
                String v = w.getElement();
                if (!index.containsKey(k)) index.put(k, new HashSet<>());
                index.get(k).add(v);
            }
        }
    }

    public String getSimilar(String w) {
        String word = "$" + w + "$";
        Map<String, Integer> map = new TreeMap<>();
        for (int i = 0; i < word.length() - 2; i++) {
            String k = word.substring(i, i + 3);
            HashSet<String> postlist = index.get(k);
            if (postlist != null)
                for (String e : index.get(k)) {
                    if (!map.containsKey(e)) map.put(e, 1);
                    else map.put(e, map.get(e) + 1);
                }
        }

        String res = "";
        int max_num = 0;
        for (String k : map.keySet()) {
            if (map.get(k) > max_num) {
                res = k;
                max_num = map.get(k);
            }
        }
        return res;
    }
}
