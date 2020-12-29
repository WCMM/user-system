package com.example.demo.mapper;

import com.example.demo.entity.PCertificateInfo;

public interface PCertificateInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PCertificateInfo record);

    int insertSelective(PCertificateInfo record);

    PCertificateInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PCertificateInfo record);

    int updateByPrimaryKey(PCertificateInfo record);
}