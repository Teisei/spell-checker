package teisei.ir.cg;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import teisei.ir.dic.DictionaryImpl;
import teisei.ir.util.EditDistance;

/**
 *
 * Created by Teisei on 2014/12.
 */
public class EditDistanceCandidatesGenerator implements CandidatesGeneratorImpl {
    DictionaryImpl dictionary;

    public EditDistanceCandidatesGenerator(DictionaryImpl dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * get all the candidates that is similar to the misspelling word
     * @param misspelling
     * @return
     */
    public String getCorrection(String misspelling) {

        Multiset<String> wordItself = HashMultiset.create();
        Multiset<String> candidates = HashMultiset.create();

        wordItself.add(misspelling);
        candidates.addAll(getKnownWords(wordItself));
        Multiset<String> edits1 = getKnownWords(EditDistance.editDist1(misspelling));

        candidates.addAll(edits1);
        candidates.addAll(getKnownWords(EditDistance.editDist2(edits1)));

        return max(candidates);

    }


    /**
     * get known words which is similar to the candidatesWords
     * @param candidateWords
     * @return
     */
    private Multiset<String> getKnownWords(
            Multiset<String> candidateWords) {
        Multiset<String> verifiedWords = HashMultiset.create();

        for (String candidate : candidateWords) {
            if (dictionary.containsWord(candidate)) {
                verifiedWords.add(candidate);
            }
        }
        return verifiedWords;
    }

    private static String max(Multiset<String> choices) {
        int maxCount = Integer.MIN_VALUE;
        String bestCandidate = "";

        for (Multiset.Entry<String> choice : choices.entrySet()) {
            if (choice.getCount() > maxCount) {
                maxCount = choice.getCount();
                bestCandidate = choice.getElement();
            }
        }

        return bestCandidate;
    }
}
