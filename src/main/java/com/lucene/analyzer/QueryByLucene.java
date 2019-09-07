package com.lucene.analyzer;


import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;


import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class QueryByLucene {
    /**
     * 创建查询搜索
     */
    public static List<String> queryByLucene(String field , String keyWords) throws Exception {

        String indexPath = "E:\\lucene";

        List<String> stringList = new ArrayList<>();
        // 1.  创建一个Directory对象，FSDirectory.open指定索引库存放的位置
        FSDirectory directory = FSDirectory.open(Paths.get(indexPath));

       /* SmartChineseAnalyzer chineseAnalyzer = new SmartChineseAnalyzer();

        IndexWriterConfig config = new IndexWriterConfig(chineseAnalyzer);

        IndexWriter writer = new IndexWriter(directory, config);
        writer.commit();*/


        // 2.  创建一个IndexReader对象，DirectoryReader.open需要指定Directory对象
        IndexReader indexReader = DirectoryReader.open(directory);
        // 3.  创建一个Indexsearcher对象，直接new，需要指定IndexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 4.  创建一个TermQuery对象，直接new，指定查询的域和查询的关键词new Term(域名称，关键词)
      /*  QueryParser queryParser = new QueryParser(field, new SmartChineseAnalyzer());
        Query query = queryParser.parse(keyWords);*/
        TermQuery query = new TermQuery(new Term(field,keyWords));
        // 5.  执行查询，IndexSearcher.search，需要指定TermQuery对象与查询排名靠多少名前的记录数，得到结果TopDocs
        TopDocs docs = indexSearcher.search(query, 50);
        System.out.println(docs.scoreDocs.length);
        // 6.  遍历查询结果并输出，TopDocs.totalHits总记录数，topDocs.scoreDocs数据列表，通过scoreDoc.doc得到唯一id,再通过IndexSearcher.doc(id)，得到文档对象Document再Document.get(域名称)得到结果
        System.out.println("查询总记录数为:" + docs.totalHits);
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            //得到文档
            int id = scoreDoc.doc;
            Document doc = indexSearcher.doc(id);
            /**
             * 打印结果集
             */
           /* System.out.println("id:" + doc.get("编号"));
            System.out.println("name:" + doc.get("wqe"));*/
            stringList.add( doc.get("wqe"));
            // System.out.println("price:" + doc.get("price"));
            // System.out.println("pic:" + doc.get("pic"));
            // System.out.println("description:" + doc.get("description"));
        }
        // 7.  关闭IndexReader对象
        indexReader.close();
        return stringList;
    }

}
