package org.example.tliaswebmanagement.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.example.tliaswebmanagement.pojo.Student;
import org.example.tliaswebmanagement.pojo.StudentQueryParam;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    // 根据班级id查询班级人数
    int countByClazzId(Integer clazzId);

    List<Student> list(StudentQueryParam studentQueryParam);

    void insert(Student student);

    void delete(Integer id);

    Student getById(Integer id);

    void update(Student student);

    void updateViolation(Integer id, Integer score);

    @MapKey("cla")
    List<Map<String, Object>> countStudentData();

    @MapKey("name")
    List<Map<String, Object>> getStudentDegreeData();
}
