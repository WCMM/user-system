package com.example.demo.mapper;

import com.example.demo.entity.PMaterielUpdateInfo;

public interface PMaterielUpdateInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PMaterielUpdateInfo record);

    int insertSelective(PMaterielUpdateInfo record);

    PMaterielUpdateInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PMaterielUpdateInfo record);

    int updateByPrimaryKey(PMaterielUpdateInfo record);
}