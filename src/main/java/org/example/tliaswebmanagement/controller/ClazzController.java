package org.example.tliaswebmanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tliaswebmanagement.pojo.Clazz;
import org.example.tliaswebmanagement.pojo.ClazzQueryParam;
import org.example.tliaswebmanagement.pojo.PageResult;
import org.example.tliaswebmanagement.pojo.Result;
import org.example.tliaswebmanagement.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/clazzs")
@RestController
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam) {
        log.info("分页查询，参数：{}", clazzQueryParam);
        PageResult<Clazz> pageResult = clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    @GetMapping("/list")
    public Result list() {
        log.info("查询所有班级信息");
        List<Clazz> clazzList = clazzService.findAll();
        return Result.success(clazzList);
    }

    @PostMapping
    public Result save(@RequestBody Clazz clazz) {
        log.info("新增班级，班级信息：{}", clazz);
        clazzService.save(clazz);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据id查询班级信息，id：{}", id);
        Clazz clazz = clazzService.getById(id);
        return Result.success(clazz);
    }

    @DeleteMapping({"/{id}"})
    public Result deleteById(@PathVariable Integer id) {
        log.info("根据id删除班级信息，id：{}", id);
        clazzService.deleteById(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Clazz clazz) {
        log.info("修改班级信息，班级信息：{}", clazz);
        clazzService.update(clazz);
        return Result.success();
    }
}
