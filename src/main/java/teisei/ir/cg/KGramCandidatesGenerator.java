package teisei.ir.cg;

import teisei.ir.util.ThreeGramIndex;

/**
 *
 * Created by Teisei on 2014/12.
 */
public class KGramCandidatesGenerator implements CandidatesGeneratorImpl {
    ThreeGramIndex kgramindex;

    public KGramCandidatesGenerator(ThreeGramIndex kgramindex) {
        this.kgramindex = kgramindex;
    }

    /**
     * get all the candidates that is similar to the misspelling word
     * @param misspelling
     * @return
     */
    public String getCorrection(String misspelling) {
        return kgramindex.getSimilar(misspelling);
    }
}
