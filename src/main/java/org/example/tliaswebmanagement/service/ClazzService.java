package org.example.tliaswebmanagement.service;

import org.example.tliaswebmanagement.pojo.Clazz;
import org.example.tliaswebmanagement.pojo.ClazzQueryParam;
import org.example.tliaswebmanagement.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    List<Clazz> findAll();

    void save(Clazz clazz);

    Clazz getById(Integer id);

    void deleteById(Integer id);

    void update(Clazz clazz);
}
