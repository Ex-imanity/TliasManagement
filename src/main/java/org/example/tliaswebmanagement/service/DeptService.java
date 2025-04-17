package org.example.tliaswebmanagement.service;

import org.example.tliaswebmanagement.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> findAll();

    void deleteById(Integer id);

    void save(Dept dept);

    void update(Dept dept);

    Dept getById(Integer id);
}
