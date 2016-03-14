package teisei.ir.util;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Created by Teisei on 2014/12.
 */
public class EditDistance {


    private static final char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z' };

    /**
     * Computes all strings of edit distance 1 away from input string.
     *
     * @param word
     *            the input string
     * @return a set of all strings one edit distance away.
     */
    public static Multiset<String> editDist1(String word) {

        Set<Tuple<String, String>> splits = buildSplits(word);

        Multiset<String> combined = HashMultiset.create();
        combined.addAll(buildDeletes(splits));
        combined.addAll(buildTransposes(splits));
        combined.addAll(buildReplaces(splits));
        combined.addAll(buildInserts(splits));

        return combined;

    }

    /**
     * Computes all strings of edit distance 1 from a set of strings.
     *
     * @param editsOne
     *            a set of strings all within 1 edit distance from each other.
     * @return a set of strings all within 2 edit distances from each other.
     */
    public static Multiset<String> editDist2(Multiset<String> editsOne) {

        Multiset<String> editsTwo = HashMultiset.create();

        for (String editOne : editsOne) {
            editsTwo.addAll(editDist1(editOne));
        }

        return editsTwo;

    }

    private static Multiset<String> buildInserts(Set<Tuple<String, String>> splits) {

        Multiset<String> inserts = HashMultiset.create();

        for (Tuple<String, String> tuple : splits) {
            for (char letter : alphabet) {
                inserts
                        .add(new String(tuple.getOne() + letter
                                + tuple.getTwo()));
            }
        }

        return inserts;

    }

    private static Multiset<String> buildReplaces(Set<Tuple<String, String>> splits) {

        Multiset<String> replaces = HashMultiset.create();

        for (Tuple<String, String> tuple : splits) {
            for (char letter : alphabet) {
                if (tuple.getTwo().length() > 0) {
                    replaces.add(new String(tuple.getOne() + letter
                            + tuple.getTwo().substring(1)));
                }
            }
        }

        return replaces;

    }

    private static Multiset<String> buildTransposes(Set<Tuple<String, String>> splits) {

        Multiset<String> transposes = HashMultiset.create();

        for (Tuple<String, String> tuple : splits) {
            if (tuple.getTwo().length() > 1) {
                transposes.add(new String(tuple.getOne()
                        + tuple.getTwo().substring(1, 2)
                        + tuple.getTwo().substring(0, 1)
                        + tuple.getTwo().substring(2)));
            }
        }

        return transposes;

    }

    private static Multiset<String> buildDeletes(Set<Tuple<String, String>> splits) {

        Multiset<String> deletes = HashMultiset.create();

        for (Tuple<String, String> tuple : splits) {
            if (tuple.getTwo().length() > 0) {
                deletes.add(new String(tuple.getOne()
                        + tuple.getTwo().substring(1)));
            }
        }

        return deletes;
    }

    private static Set<Tuple<String, String>> buildSplits(String word) {
        Set<Tuple<String, String>> splits = new HashSet<Tuple<String, String>>();

        for (int i = 0; i < word.length() + 1; i++) {
            splits.add(new Tuple<String, String>(word.substring(0, i), word.substring(i, word.length())));
        }

        return splits;
    }
}
