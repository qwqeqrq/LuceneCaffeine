package com.lucene.conrtoller;

import com.lucene.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value ="/getDataByLucene")
    public List<Map<String,Object>> getDataByLucene(String key){
        return universityService.getUniversityByMysql();
    }

}
