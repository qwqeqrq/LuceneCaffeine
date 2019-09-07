package com.lucene.conrtoller;

import com.lucene.analyzer.IndexTest;
import com.lucene.analyzer.QueryByLucene;
import com.lucene.service.UniversityService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.queryparser.classic.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping(value = "/lucene")
public class LuceneCtroller {

    private UniversityService universityService;

    @Autowired
    public LuceneCtroller(UniversityService universityService) {
        this.universityService = universityService;
    }

    @RequestMapping(value = "/getUniversityByMysql")
    public List<Map<String, Object>> getUniversityByMysql(String key) {
        return universityService.getUniversityByMysql();
    }

    /**
     * 根据本地索引文件进行查询
     * @param field 查询的域   keyWords  关键词
     * @throws IOException
     */
    @RequestMapping(value = "/getDataByLucene")
    public List<String> getDataByLucene(@RequestParam String field, @RequestParam String keyWords) throws Exception {
      return  QueryByLucene.queryByLucene(field,keyWords);
    }

    /**
     * 创建索引
     * @param key
     * @throws IOException
     */
    @RequestMapping(value = "/creatLuceneIndex")
    public void creatLuceneIndex(String key) throws  Exception {
        IndexTest index = new IndexTest(universityService);
        index.createIndex();
    }


}