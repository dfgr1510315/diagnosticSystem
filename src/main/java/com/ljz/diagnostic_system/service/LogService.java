package com.ljz.diagnostic_system.service;

import com.ljz.diagnostic_system.model.Log;

public interface LogService {
    void createLog(Log log);

    void updateLog(Log log);
}
