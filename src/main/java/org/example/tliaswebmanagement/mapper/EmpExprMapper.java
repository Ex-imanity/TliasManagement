package org.example.tliaswebmanagement.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.tliaswebmanagement.pojo.EmpExpr;

import java.util.List;

@Mapper
public interface EmpExprMapper {

    /**
     * 批量保存工作经历信息
     * @param exprList
     */
    void inertBatch(List<EmpExpr> exprList);

    /**
     * 根据员工id批量删除工作经历信息
     * @param empIds
     */
    void deleteByEmpIds(List<Integer> empIds);
}
