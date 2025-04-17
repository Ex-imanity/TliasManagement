package org.example.tliaswebmanagement.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.tliaswebmanagement.pojo.Clazz;
import org.example.tliaswebmanagement.pojo.ClazzQueryParam;

import java.util.List;

@Mapper
public interface ClazzMapper {
    // 分页查询
    List<Clazz> list(ClazzQueryParam clazzQueryParam);

    List<Clazz> findAll();

    void insert(Clazz clazz);

    Clazz getById(Integer id);

    void deleteById(Integer id);

    void update(Clazz clazz);
}
