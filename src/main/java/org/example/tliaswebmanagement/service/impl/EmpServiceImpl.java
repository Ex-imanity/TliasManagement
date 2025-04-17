package org.example.tliaswebmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.example.tliaswebmanagement.mapper.EmpExprMapper;
import org.example.tliaswebmanagement.mapper.EmpMapper;
import org.example.tliaswebmanagement.pojo.*;
import org.example.tliaswebmanagement.service.EmpLogService;
import org.example.tliaswebmanagement.service.EmpService;
import org.example.tliaswebmanagement.utils.AliyunOSSOperator;
import org.example.tliaswebmanagement.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize) {
//        // 1. 计算起始索引
//        Long start = ((long) (page - 1) * pageSize);
//
//        // 2. 查询数据
//        List<Emp> rows = empMapper.list(start, pageSize);
//
//        // 3. 查询总记录数
//        Long total = empMapper.count();
//
//        // 4. 封装 PageResult 对象并返回
//        return new PageResult<>(total, rows);
//   }

//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end) {
//
//        // 1. 开启分页
//        PageHelper.startPage(page, pageSize);
//
//        // 2. 查询数据
//        List<Emp> rows = empMapper.list(name, gender, begin, end);
//
//        // 3. 封装 PageResult 对象并返回
//        Page<Emp> p = (Page<Emp>) rows;
//        return new PageResult<>(p.getTotal(), p.getResult());
//    }

    @Override
    public List<Emp> findAll() {
        return empMapper.findAll();
    }

    @Override
    public LoginInfo login(Emp emp) {
        // 1. 调用mapper, 根据用户名查询用户信息
        Emp e = empMapper.getByUsernameAndPassword(emp);
        // 2. 判断用户是否存在，若存在，组装登录信息并返回
        if (e != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("username", e.getUsername());
            String jwt = JwtUtils.generateJwt(claims);
            return new LoginInfo(e.getId(), e.getUsername(), e.getName(), jwt);
        }
        // 3. 不存在，返回null
        return null;
    }

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {

        // 1. 开启分页
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

        // 2. 查询数据

        List<Emp> rows = empMapper.list(empQueryParam);

        // 3. 封装 PageResult 对象并返回
        Page<Emp> p = (Page<Emp>) rows;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void save(Emp emp) {
        try {
            //1.保存员工基本信息
            // 设置默认值
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());

            empMapper.insert(emp);
            //2.保存工作经历信息
            List<EmpExpr> exprList = emp.getExprList();
            if (!CollectionUtils.isEmpty(exprList)) {
                //遍历集合，为EmpExpr的empId赋值
                exprList.forEach(expr -> {
                    expr.setEmpId(emp.getId());
                });
                empExprMapper.inertBatch(exprList);
            }
        } finally {
            //3.保存员工日志信息
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工：" + emp);
            empLogService.insertLog(empLog);
        }

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delete(List<Integer> ids) {

        // 1.删除OSS中该用户上传的文件
        for (Integer id : ids) {
            // 假设通过员工ID查询到文件路径
            String filePath = empMapper.getFilePathById(id);
            if (filePath != null) {
                try {
                    aliyunOSSOperator.delete(filePath);
//                    log.info("文件删除成功，文件路径：{}", filePath);
                } catch (Exception e) {
                    log.error("文件删除失败，文件路径：{}", filePath, e);
                }
            }
        }
        //2.删除员工基本信息
        empMapper.deleteByIds(ids);

        //3.删除员工工作经历信息
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getById(Integer id) {
        return empMapper.getById(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Emp emp) {
        //1.更新员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
        //2.更新员工工作经历信息
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            //遍历集合，为EmpExpr的empId赋值
            exprList.forEach(expr -> {
                expr.setEmpId(emp.getId());
            });
            empExprMapper.inertBatch(exprList);
        }
    }
}
