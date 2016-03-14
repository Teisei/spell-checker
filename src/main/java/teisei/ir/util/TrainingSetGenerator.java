package teisei.ir.util;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * Created by Teisei on 2014/12.
 */
public class TrainingSetGenerator {
	
	private static final Pattern nonWord = Pattern
			.compile("[ \t\n\f\r-.,_;0-9?=:\\]\\[]+");

	/**
	 * Generates a word count for each word found in the file specified by
	 * fileName.
	 * 
	 * @param fileName
	 *            The fileName to be opened.
	 * @return Set<Multi.Entry<String>> Bag of words for lazy counting.
	 * @throws UnsupportedOperationException
	 *             If file cannot be found.
	 */
	public static Set<Multiset.Entry<String>> buildWordSet(String fileName)
			throws UnsupportedOperationException {

		File file = new File(fileName);
		Multiset<String> wordOccurrences = null;

		try {
			Iterable<String> words = Splitter.on(nonWord).omitEmptyStrings()
					.trimResults().split(Files.toString(file, Charsets.UTF_8));
			List<String> lowercaseWords = new ArrayList<String>();
			for (String word : words) {
				lowercaseWords.add(word.toLowerCase());
			}
			wordOccurrences = HashMultiset.create();
			wordOccurrences.addAll(lowercaseWords);
		} catch (IOException ioe) {
			throw new UnsupportedOperationException("Training file not found! "
					+ fileName + "\n", ioe);
		}

		return wordOccurrences.entrySet();
	}

//	public static TrieTree<Word> buildWordTrieTree(String fileName)
//			throws UnsupportedOperationException {
//
//		File file = new File(fileName);
//		Multiset<String> wordOccurrences = null;
//		TrieTree<Word> tree = new TrieTree<>();
//		try {
//			Iterable<String> words = Splitter.on(nonWord).omitEmptyStrings()
//					.trimResults().split(Files.toString(file, Charsets.UTF_8));
//			List<String> lowercaseWords = new ArrayList<String>();
//			for (String word : words) {
//				TrieTree<Word> wordBranch = tree.getBranch(word);
//				if (wordBranch == null) {
//					tree.add(word, new Word(word, "x", "haha"));
//				}
//			}
//		} catch (IOException ioe) {
//			throw new UnsupportedOperationException("Training file not found! "
//					+ fileName + "\n", ioe);
//		}
//
//		return tree;
//	}



}
