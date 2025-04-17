package org.example.tliaswebmanagement.service.impl;

import org.example.tliaswebmanagement.mapper.EmpMapper;
import org.example.tliaswebmanagement.mapper.StudentMapper;
import org.example.tliaswebmanagement.pojo.ClazzOption;
import org.example.tliaswebmanagement.pojo.JobOption;
import org.example.tliaswebmanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JobOption getEmpJobData() {
        //1. 调用mapper获取员工岗位数据
        List<Map<String, Object>> list = empMapper.countEmpJobData();
        //2.封装JobOption对象并返回
        List<Object> jobList = list.stream().map(map -> map.get("pos")).toList();
        List<Object> dataList = list.stream().map(map -> map.get("num")).toList();
        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.getEmpGenderData();
    }

    @Override
    public ClazzOption getStudentCountData() {
        List<Map<String, Object>> list = studentMapper.countStudentData();
        List<Object> clazzList = list.stream().map(map -> map.get("cla")).toList();
        List<Object> dataList = list.stream().map(map -> map.get("num")).toList();
        return new ClazzOption(clazzList, dataList);
    }

    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.getStudentDegreeData();
    }
}
