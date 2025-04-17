package org.example.tliaswebmanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tliaswebmanagement.anno.Log;
import org.example.tliaswebmanagement.pojo.Dept;
import org.example.tliaswebmanagement.pojo.Result;
import org.example.tliaswebmanagement.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {

    @Autowired
    private DeptService deptService;
//    private static final Logger log = LoggerFactory.getLogger(DeptController.class);

//    @RequestMapping(value = "/depts", method = RequestMethod.GET)
    @GetMapping
    public Result list() {
//        System.out.println("depts");
        log.info("查询所有部门信息");
        List<Dept> deptList = deptService.findAll();
        Result result = Result.success(deptList);
        return result;
    }

    @Log
    @DeleteMapping
    public Result delete(Integer id) {
//        System.out.println("delete" + id);
        log.info("delete {}", id);
        deptService.deleteById(id);
        return Result.success();
    }

    @Log
    @PostMapping
    public Result save(@RequestBody Dept dept) {
//        System.out.println("save" + dept);
        log.info("save {}", dept);
        deptService.save(dept);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable("id") Integer id) {
//        System.out.println("getInfo" + id);
//        log.info("getInfo {}", id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept) {
//        System.out.println("update" + dept);
        log.info("update {}", dept);
        deptService.update(dept);
        return Result.success();
    }
}
