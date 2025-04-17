package org.example.tliaswebmanagement.mapper;

import org.apache.ibatis.annotations.*;
import org.example.tliaswebmanagement.pojo.Emp;
import org.example.tliaswebmanagement.pojo.EmpQueryParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {

    /**
     * 查询总记录数
     * @return
     */
//    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
//    public Long count();

    /**
     * 分页查询
     */
//    @Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id = d.id " +
//            "order by e.update_time desc limit #{start},#{pageSize}")
//    public List<Emp> list(Long start, Integer pageSize);
//    @Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id = d.id " +
//            "order by e.update_time desc")
//    public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);
    public List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工基本信息
     */
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into emp(username,password,name,gender,phone,job,salary,image,entry_date,dept_id,create_time,update_time) " +
    "values(#{username},#{password},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    /**
     * 批量删除员工基本信息
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据id查找员工头像图片url
     */
    @Select("select image from emp where id = #{id}")
    String getFilePathById(Integer id);

    /**
     * 根据id查找员工基本信息
     */
    Emp getById(Integer id);

    /**
     * 根据id修改员工基本信息
     */
//    @Update("update emp set username=#{username},password=#{password},name=#{name},gender=#{gender},phone=#{phone},job=#{job},salary=#{salary},image=#{image},entry_date=#{entryDate},dept_id=#{deptId},update_time=#{updateTime} where id = #{id}")
    void updateById(Emp emp);

    /**
     * 统计员工职位数量
     * @return
     */
    @MapKey("pos") //防止mybatisX 报错
    List<Map<String, Object>> countEmpJobData();

    @MapKey("name") //防止mybatisX 报错
    List<Map<String, Object>> getEmpGenderData();

    @Select("select * from emp")
    List<Emp> findAll();

    @Select("select id, username, name from emp where username = #{username} and password = #{password}")
    Emp getByUsernameAndPassword(Emp emp);
}
