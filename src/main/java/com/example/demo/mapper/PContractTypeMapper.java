package com.example.demo.mapper;

import com.example.demo.entity.PContractType;

public interface PContractTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PContractType record);

    int insertSelective(PContractType record);

    PContractType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PContractType record);

    int updateByPrimaryKey(PContractType record);
}