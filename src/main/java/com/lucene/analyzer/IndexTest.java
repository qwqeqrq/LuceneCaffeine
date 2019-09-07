package com.lucene.analyzer;


import com.lucene.service.UniversityService;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class IndexTest {

    private UniversityService universityService;

    //计数变量
    private AtomicInteger integer = new AtomicInteger(0);

    //多线程循环导致线程不安全 加入lock锁
    private ReentrantLock reentrantLock = new ReentrantLock();

    @Autowired
    public IndexTest(UniversityService universityService) {
        this.universityService = universityService;
    }

    /**
     * 创建 索引
     */
    public void createIndex() {

        // 1.查询所有数据
        List<Map<String, Object>> lists = universityService.getUniversityByMysql();

        //2.遍历结果集 组装document数据列表
        Document document = new Document();
        lists.parallelStream().forEach(map -> { //多线程循环导致线程不安全 加入lock锁
            reentrantLock.lock();//加锁同步
            //3. 创建  Field

            //name 要分词 要索引 要存储
            Field fieldName = new StringField("wqe", map.get("name").toString(), Field.Store.YES);
            Field fieldId = new StringField("编号", map.get("id").toString(), Field.Store.YES);
            System.out.println(map.get("name").toString());
            //4.  将Field域所有对象，添加到文档对象中。调用Document.add
            document.add(fieldName);
            document.add(fieldId);
            //5.  创建一个标准分词器(Analyzer与StandardAnalyzer)，对文档中的Field域进行分词
            StandardAnalyzer  chineseAnalyzer = new StandardAnalyzer ();
            //CJKAnalyzer chineseAnalyzer = new CJKAnalyzer();

            String path = "E:\\lucene";

            //6.  指定索引储存目录，使用FSDirectory.open()方法。
            try {
                //----------------------
                FSDirectory directory = FSDirectory.open(Paths.get(path));
                //7.  创建IndexWriterConfig对象，直接new，用于接下来创建IndexWriter对象
                IndexWriterConfig config = new IndexWriterConfig(chineseAnalyzer);
                //8.  创建IndexWriter对象，直接new
                IndexWriter writer = new IndexWriter(directory, config);
                //9.  添加文档对象到索引库输出对象中，使用IndexWriter.addDocuments方法
                writer.addDocument(document);
                writer.numDocs();
                //10.  释放资源IndexWriter.close();
                writer.commit();//不commit就不能读到reader
                writer.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                if (reentrantLock.isLocked()) {
                    reentrantLock.unlock();//释放锁
                    System.out.println("========释放锁======");
                }
            }
            System.out.println(integer.incrementAndGet());
        });

        System.out.println("========索引创建完成======");
        System.out.println("========索引创建完成======");
        System.out.println("========索引创建完成======");
        System.out.println("========索引创建完成======");

    }
}
