package org.example.tliaswebmanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tliaswebmanagement.pojo.Result;
import org.example.tliaswebmanagement.pojo.Student;
import org.example.tliaswebmanagement.pojo.StudentQueryParam;
import org.example.tliaswebmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Result page(StudentQueryParam studentQueryParam) {
        log.info("分页查询，参数：{}", studentQueryParam);
        return Result.success(studentService.page(studentQueryParam));
    }

    @PostMapping
    public Result save(@RequestBody Student student) {
        log.info("新增学生，学生信息：{}", student);
        studentService.save(student);
        return Result.success();
    }

    @DeleteMapping({"/{id}"})
    public Result delete(@PathVariable Integer id) {
        log.info("删除学生，id：{}", id);
        studentService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据id查询，id：{}", id);
        return Result.success(studentService.getById(id));
    }

    @PutMapping
    public Result update(@RequestBody Student student) {
        log.info("更新学生，学生信息：{}", student);
        studentService.update(student);
        return Result.success();
    }

    @PutMapping("/violation/{id}/{score}")
    public Result updateViolation(@PathVariable Integer id, @PathVariable Integer score) {
        log.info("更新学生违规信息，学生id：{}，违规分数：{}", id, score);
        studentService.updateViolation(id, score);
        return Result.success();
    }
}
