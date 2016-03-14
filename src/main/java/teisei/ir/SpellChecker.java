package teisei.ir;

import teisei.ir.cg.CandidatesGeneratorImpl;
import teisei.ir.cg.KGramCandidatesGenerator;
import teisei.ir.dic.DictionaryImpl;
import teisei.ir.dic.SimpleCountDictionary;
import teisei.ir.dic.TrieTreeDictionary;
import teisei.ir.cg.EditDistanceCandidatesGenerator;
import teisei.ir.util.TestingSetGenerator;
import teisei.ir.util.ThreeGramIndex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * Created by Teisei on 2014/12.
 */
public class SpellChecker {

    public static void main(String[] args) {
        args = new String[]{
                "./data/big.txt"
        };
        usage(args);

        String trainingSetPath = args[0];

        DictionaryImpl dictionary = new SimpleCountDictionary(trainingSetPath);
        dictionary = new TrieTreeDictionary(trainingSetPath);

        ThreeGramIndex kgramindex = new ThreeGramIndex(trainingSetPath);


        SpellCheckerEvaluator evaluator = new SpellCheckerEvaluator(dictionary);
        CandidatesGeneratorImpl candidatesGenerator = new EditDistanceCandidatesGenerator(dictionary);
        candidatesGenerator = new KGramCandidatesGenerator(kgramindex);

        evaluateModel(evaluator, candidatesGenerator);

        boolean quit = false;
        while (!quit) {
            BufferedReader inputText = new BufferedReader(
                    new InputStreamReader(System.in));
            System.out.print("Enter misspelled word: ");
            try {
                String word = inputText.readLine();
                if (word.equals("quit")) {
                    quit = true;
                    System.out.println("Quitting spellchecker.");
                } else {
                    System.out.printf("Suggested correction: %s\n", candidatesGenerator.getCorrection(word));

                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.exit(1);
            }
        }

        System.exit(1);

    }


    /**
     * evaluate the model trained
     * @param evaluator
     */
    private static void evaluateModel(SpellCheckerEvaluator evaluator, CandidatesGeneratorImpl editDistanceCandidatesGenerator) {
        Iterator<String> testSet = TestingSetGenerator.getTestErrors(GeneralType.TestType.DEVELOPMENT);

        Map<String, String> proposedCorrections = new HashMap<String, String>();

        Date startTime = new Date();

        while (testSet.hasNext()) {
            String misspelling = testSet.next();
            String correction = editDistanceCandidatesGenerator.getCorrection(misspelling);
            proposedCorrections.put(misspelling, correction);
        }

        Date endTime = new Date();

        long seconds = endTime.getTime() - startTime.getTime();

        double accuracy = evaluator.evaluateCorrections(
                proposedCorrections, GeneralType.TestType.DEVELOPMENT, GeneralType.Verboseness.VERBOSE);

        System.out
                .printf(
                        "Words tested: %d, percent correct: %f, time taken: %d milliseconds, %f ms/word\n",
                        proposedCorrections.keySet().size(), accuracy, seconds,
                        proposedCorrections.keySet().size() / (float) seconds);

    }

    private static void usage(String[] args) {
        if (args.length != 1) {
            System.err.println("Length of args: " + args.length);
            System.err.printf("Didn't specify just a training set!\n");
            System.exit(1);
        }
    }

//
//    private static TrieTree<Word> buildKnowTrieTree(Set<Multiset.Entry<String>> wordSet) {
//        TrieTree<Word> tree = new TrieTree<>();
//        for (Multiset.Entry<String> word : wordSet) {
//            tree.add(word.getElement(), new Word(word.getElement(), "x", "haha"));
//        }
//        return tree;
//    }
}
