package teisei.ir.dic;

/**
 *
 * Created by Teisei on 2014/12.
 */
public interface DictionaryImpl {
    /** the number of all words in this dictionary */
    int getSize();

    /** the number of the word occur in this training set */
    int hasWordCount(String word);

    /** if the given word exists in the dictionary */
    boolean containsWord(String word);
}
