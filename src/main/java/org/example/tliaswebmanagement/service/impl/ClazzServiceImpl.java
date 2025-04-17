package org.example.tliaswebmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.tliaswebmanagement.exception.UnsafeDeletionException;
import org.example.tliaswebmanagement.mapper.ClazzMapper;
import org.example.tliaswebmanagement.mapper.StudentMapper;
import org.example.tliaswebmanagement.pojo.Clazz;
import org.example.tliaswebmanagement.pojo.ClazzQueryParam;
import org.example.tliaswebmanagement.pojo.PageResult;
import org.example.tliaswebmanagement.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {
        // 1. 开启分页
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());

        // 2. 查询数据
        List<Clazz> rows = clazzMapper.list(clazzQueryParam);

        // 3. 设置班级状态
        for (Clazz clazz : rows) {
            if (clazz.getEndDate().isBefore(LocalDate.now())) {
                clazz.setStatus("已结束");
            } else if (clazz.getBeginDate().isAfter(LocalDate.now())) {
                clazz.setStatus("未开始");
            } else {
                clazz.setStatus("进行中");
            }
        }

        // 4. 封装 PageResult 对象并返回
        Page<Clazz> p = (Page<Clazz>) rows;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public List<Clazz> findAll() {
        List<Clazz> rows = clazzMapper.findAll();
        for (Clazz clazz : rows) {
            if (clazz.getEndDate().isBefore(LocalDate.now())) {
                clazz.setStatus("已结束");
            } else if (clazz.getBeginDate().isAfter(LocalDate.now())) {
                clazz.setStatus("未开始");
            } else {
                clazz.setStatus("进行中");
            }
        }
        return rows;
    }

    @Override
    public void save(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
    }

    @Override
    public Clazz getById(Integer id) {
        return clazzMapper.getById(id);
    }

    @Override
    public void update(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.update(clazz);
    }

    @Override
    public void deleteById(Integer id) {
        if (studentMapper.countByClazzId(id) > 0) {
            throw new UnsafeDeletionException("该班级有学生，不能删除");
        }
        clazzMapper.deleteById(id);
    }
}
