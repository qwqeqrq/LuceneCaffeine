package com.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;

public class AnalyzerTest extends Analyzer {

    /**
     * 继承  Lucene    Analyzer 分词器
     * @param s
     * @return
     */


    @Override
    protected TokenStreamComponents createComponents(String s) {
        return null;
    }
}
