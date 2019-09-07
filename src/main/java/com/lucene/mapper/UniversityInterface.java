package com.lucene.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface UniversityInterface {

    @Select("SELECT a.id id, a.school_id schoolId, a.`name`, a.level_name levelName, " +
            "a.type_name typeName, a.dual_class_name dualClassName, a.county_name countyName," +
            " a.province_name provinceName, a.city_name cityName, a.is_top isTop, a.f211 f211, a.f985 f985, a.special" +
            " special, a.deleted deleted, a.createDate createDate, a.updateDate updateDate FROM `university` a  limit 100 " )
    List<Map<String,Object>> selectUniversity();
}
