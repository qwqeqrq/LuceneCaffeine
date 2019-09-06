package com.lucene.service;


import com.lucene.mapper.UniversityInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UniversityService {
    private UniversityInterface universityInterface;

    @Autowired
    public UniversityService(UniversityInterface universityInterface) {
        this.universityInterface = universityInterface;
    }

    public List<Map<String,Object>> getUniversityByMysql(){
      return   universityInterface.selectUniversity();
    }

}
