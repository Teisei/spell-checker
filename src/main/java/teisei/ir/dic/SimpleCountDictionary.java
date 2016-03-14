package teisei.ir.dic;

import com.google.common.collect.Multiset;
import teisei.ir.util.TrainingSetGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * Created by Teisei on 2014/12.
 */
public class SimpleCountDictionary implements DictionaryImpl {
    private String dicPath;
    private Map<String, Integer> knownWords = new HashMap<>();

    private static final Pattern nonWord = Pattern
            .compile("[ \t\n\f\r-.,_;0-9?=:\\]\\[]+");

    public SimpleCountDictionary(String dicPath) {
        this.dicPath = dicPath;
        Set<Multiset.Entry<String>> wordList = TrainingSetGenerator.buildWordSet(dicPath);
        buildKnownWords(wordList);
    }

    public int getSize() {
        return knownWords.size();
    }

    public int hasWordCount(String word) {
        return (knownWords.get(word) == null) ? 0
                : knownWords.get(word);
    }

    public boolean containsWord(String word) {
        return knownWords.containsKey(word);
    }

    private void buildKnownWords(Set<Multiset.Entry<String>> wordSet) {

        for (Multiset.Entry<String> word : wordSet) {
            knownWords.put(word.getElement(), word.getCount());
        }

    }
}
