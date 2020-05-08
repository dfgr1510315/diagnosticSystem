package com.ljz.diagnostic_system.service.impl;

import com.ljz.diagnostic_system.dao.LogDao;
import com.ljz.diagnostic_system.model.Log;
import com.ljz.diagnostic_system.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;

    @Override
    public void createLog(Log log) {
        logDao.createLog(log);
    }

    @Override
    public void updateLog(Log log) {
        logDao.updateLog(log);
    }
}
