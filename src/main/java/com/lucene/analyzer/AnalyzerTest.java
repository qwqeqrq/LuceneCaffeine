package com.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;

import java.io.IOException;

public class AnalyzerTest extends Analyzer {

    /**
     * 继承  Lucene    Analyzer 分词器基类 定义自己的分词器
     *
     * @param s
     * @return
     */


    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer source = new Tokenizer() {
            /**
             *  自定义创建抽象类的实例
             * @return
             * @throws IOException
             */
            @Override
            public boolean incrementToken() throws IOException {
                return false;
            }
        };
        TokenStream filter = new TokenStream(source) {
            /**
             *  自定义创建抽象类的实例
             * @return
             * @throws IOException
             */
            @Override
            public boolean incrementToken() throws IOException {
                return true;
            }
        };
        return new TokenStreamComponents(source, filter);

    }

    public static void main(String[] args) {
        AnalyzerTest analyzerTest = new AnalyzerTest();
        TokenStreamComponents tokenStreamComponents = analyzerTest.createComponents("sde问我不是手机");
        System.out.println("====");
        System.out.println("=Tokenizer==="+tokenStreamComponents.getTokenizer());
        System.out.println("=TokenStream==="+tokenStreamComponents.getTokenStream().toString());
    }
}
