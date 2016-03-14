package teisei.ir.dic.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Created by Teisei on 2014/12.
 */
public class TrieTree<T> implements Comparable<TrieTree<T>> {

    /*
        the number of all words
     */
    private int size = 0;

    public TrieTree<T>[] branches = null;

    TrieTree<T> branch = null;

    /*
    content of this word
     */
    private char c;

    /*
    status of the word:
    1:continue; 2:is a word and can continue; 3:nature is sure
     */
    private byte status = 1;
    // 词典后的参数
    private List<T> param = null;

    public TrieTree() {
    }

    public TrieTree(char c) {
        this.c = c;
    }

    public TrieTree(char c, int status, T t) {
        this.c = c;
        this.status = (byte) status;
        this.param = new ArrayList<T>();
        this.param.add(t);
    }

    public char getC() {
        return c;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = (byte) status;
    }

    public List<T> getParam() {
        return param;
    }

    public TrieTree<T> getBranch() {
        return branch;
    }

    public TrieTree<T> add(String keyword, T t) {
        if (branches == null) {
            branches = new TrieTree[0];
        }
        char c = keyword.charAt(0);
        return branches[(int) c];
    }

    private TrieTree<T> addAChar(TrieTree<T> branch_1, boolean append) {
        TrieTree<T> branch = (TrieTree<T>) branch_1;
        if (branches == null) {
            branches = new TrieTree[0];
        }
        int bs = search(branch.getC());
        if (bs > -1) {
            this.branch = this.branches[bs];
            switch (branch.getStatus()) {
                case -1:
                    this.branch.setStatus(1);
                    break;
                case 1:
                    if (this.branch.getStatus() == 3) {
                        this.branch.setStatus(2);
                    }
                    break;
                case 3:
                    if (this.branch.getStatus() != 3) {
                        this.branch.setStatus(2);
                    }
                    if (append) {
                        this.branch.param.addAll(branch.getParam());
                    } else {
                        this.branch.param = branch.getParam();
                    }
            }
            return this.branch;
        }

        if (bs < 0) {
            TrieTree<T>[] newBranches = new TrieTree[branches.length + 1];
            int insert = -(bs + 1);
            System.arraycopy(this.branches, 0, newBranches, 0, insert);
            System.arraycopy(branches, insert, newBranches, insert + 1, branches.length - insert);
            newBranches[insert] = branch;
            this.branches = newBranches;
        }
        return branch;
    }

    /**
     * search for char c in the tree rooted by this Node.
     */
    public int search(char c) {
        if (branches == null) {
            return -1;
        }
        int res = Arrays.binarySearch(this.branches, new TrieTree<T>(c));
        return res;
    }

    /**
     * 根据一个词获得所取的参数,没有就返回null
     *
     * @param keyWord
     */
    public TrieTree<T> getBranch(String keyWord) {
        TrieTree<T> tempBranch = this;
        int index = 0;
        for (int j = 0; j < keyWord.length(); j++) {
            index = tempBranch.search(keyWord.charAt(j));
            if (index < 0) {
                return null;
            }
            tempBranch = tempBranch.branches[index];
        }
        return tempBranch;
    }

    /**
     * 增加新词
     */
    public void append(String keyWord, T t) {
        TrieTree<T> tempBranch = this;
        for (int i = 0; i < keyWord.length(); i++) {
            if (keyWord.length() == i + 1) {
                tempBranch.addAChar(new TrieTree<T>(keyWord.charAt(i), 3, t), true);
            } else {
                tempBranch.addAChar(new TrieTree<T>(keyWord.charAt(i), 1, null), true);
            }
            tempBranch = tempBranch.branches[tempBranch.search(keyWord.charAt(i))];
        }
        size++;
    }

    public int getSize() {
        return size;
    }

    public static void main(String args[]) {
//        TrieTree<Person> tree = new TrieTree<Person>();
//        TrieTree<Person> tree2 = new TrieTree<Person>('a');
//        tree.add("abcde", new Person("hai"));
        TrieTree<Integer> sf = new TrieTree<Integer>();
        sf.append("c", 1);
        sf.append("e", 2);
        sf.append("a", 3);
//        sf.append("java", 1);
//        sf.append("java", 2);
//        sf.append("java", 3);
//        sf.append("php", 2);
//        sf.append("python", 3);
//        sf.append("ruby", 4);
//        sf.append(".net", 5);

        TrieTree<Integer> branch2 = sf.getBranch("java");
//        System.out.println(branch2.getParam());
//
//        sf.add("java", 1);
//        branch2 = sf.getBranch("java");
//        System.out.println(branch2.getParam());

    }

    public int compareTo(TrieTree<T> o) {
        if (this.c > o.c)
            return 1;
        if (this.c < o.c) {
            return -1;
        }
        return 0;
    }
}
