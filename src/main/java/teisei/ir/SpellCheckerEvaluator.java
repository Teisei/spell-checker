package teisei.ir;

import com.google.common.collect.ImmutableMap;
import teisei.ir.dic.DictionaryImpl;
import teisei.ir.util.TestingSetGenerator;

import java.util.Iterator;
import java.util.Map;

/**
 *
 * Created by Teisei on 2014/12.
 */
public class SpellCheckerEvaluator {

	private static final ImmutableMap<String, String> development = TestingSetGenerator.createTest1Map();

	private static final ImmutableMap<String, String> evaluation = TestingSetGenerator.createTest2Map();

	private DictionaryImpl dictionary;


	public SpellCheckerEvaluator(DictionaryImpl dictionary) {
		this.dictionary = dictionary;
	}

	public static Iterator<String> getTestErrors(ImmutableMap<String, String> data) {
		return data.keySet().iterator();
	}


	public double evaluateCorrections(Map<String, String> corrections,
									  GeneralType.TestType type, GeneralType.Verboseness verbosity) {

		int count = 0;
		int correct = 0;

		if (verbosity.equals(GeneralType.Verboseness.VERBOSE))
			System.out.printf("Size of training corpus: %d\n", dictionary.getSize());

		for (String error : corrections.keySet()) {
			String correctAnswer = "";
			if (type.equals(GeneralType.TestType.DEVELOPMENT)) {
				correctAnswer = development.get(error);
			} else {
				correctAnswer = evaluation.get(error);
			}
			String proposedAnswer = corrections.get(error);
			if (correctAnswer.equals(proposedAnswer))
				correct++;
			else if (verbosity.equals(GeneralType.Verboseness.VERBOSE)) {
				int proposedCount = dictionary.hasWordCount(proposedAnswer);
				int correctCount = dictionary.hasWordCount(correctAnswer);
				System.out
						.printf(
								"getCorrection('%s') - PROPOSED: %s (%d), TARGET: %s (%d)\n",
								error, proposedAnswer, proposedCount,
								correctAnswer, correctCount);
			}

			count++;
		}

		return (float) correct / count;

	}
}
