package teisei.ir.dic;

import com.google.common.collect.Multiset;
import teisei.ir.dic.trie.TrieTree;
import teisei.ir.dic.trie.Word;
import teisei.ir.util.TrainingSetGenerator;

import java.util.Set;

/**
 *
 * Created by Teisei on 2014/12.
 */
public class TrieTreeDictionary implements DictionaryImpl {
    String dicPath;
    TrieTree<Word> tree;

    public TrieTreeDictionary(String dicPath) {
        this.dicPath = dicPath;
        constructDic(this.dicPath);
    }

    private void constructDic(String dicPath) {
        tree = new TrieTree<>();
        Set<Multiset.Entry<String>> wordSet = TrainingSetGenerator.buildWordSet(dicPath);
        for (Multiset.Entry<String> word : wordSet) {
            TrieTree<Word> wordBranch = tree.getBranch(word.getElement());
            if (wordBranch == null) {
                tree.append(word.getElement(), new Word(word.getElement(), word.getCount()));
            }
        }
    }

    public int getSize() {
        return tree.getSize();
    }

    public int hasWordCount(String word) {
        TrieTree<Word> branch = tree.getBranch(word);
        if(branch!=null)
            return branch.getStatus()==1 ? 0 : branch.getParam().get(0).getCount();
        return 0;
    }

    public boolean containsWord(String word) {
        TrieTree<Word> branch = tree.getBranch(word);
        if(branch!=null)
            return branch.getStatus()>1;
        return false;
    }
}
