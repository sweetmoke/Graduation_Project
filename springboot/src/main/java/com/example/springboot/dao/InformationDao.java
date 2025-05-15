package com.example.springboot.dao;

import com.example.springboot.entity.Information;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InformationDao {
    List<Information> selectByUser(@Param("userId") Integer userId,
                                   @Param("role") String role,
                                   @Param("offset") Integer offset,
                                   @Param("pageSize") Integer pageSize);

    int countByUser(@Param("userId") Integer userId,
                    @Param("role") String role);

    void updateIsRead(@Param("informationId") Integer informationId);
}
