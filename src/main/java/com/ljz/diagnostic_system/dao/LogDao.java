package com.ljz.diagnostic_system.dao;

import com.ljz.diagnostic_system.model.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LogDao {
    void createLog(Log log);

    void updateLog(Log log);
}
