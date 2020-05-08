package com.ljz.diagnostic_system.service.impl;

import com.ljz.diagnostic_system.common.Utils.JWTUtil;
import com.ljz.diagnostic_system.common.Utils.SnowFlake;
import com.ljz.diagnostic_system.dao.UserRelationDao;
import com.ljz.diagnostic_system.model.UserProfile;
import com.ljz.diagnostic_system.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    //private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    @Autowired
    private UserRelationDao userRelationDao;

    @Override
    public UserProfile login(String email, String password) {
        UserProfile userProfile = loadUserByEmail(email);
        if(userProfile==null||!password.equals(userProfile.getPassword())){
            return null;
        }
        userProfile.setToken(JWTUtil.generToken(userProfile.getId(),"LJZ",email));
        return userProfile;
    }

    @Override
    public int register(UserProfile userProfile) {
        userProfile.setId(String.valueOf(SnowFlake.getNextId()));
        return userRelationDao.register(userProfile);
    }

    @Override
    public UserProfile loadUserByEmail(String email) {
        //List<UserProfile> userProfiles = userRelationDao.loadUserByEmail(email);
        //return userProfiles.size()==1?userProfiles.get(0):null;
        return userRelationDao.loadUserByEmail(email);
    }

    @Override
    public UserProfile loadUserById(String id) {
        return userRelationDao.loadUserById(id);
    }

    @Override
    public int newUsername(String id,String newName) {
        return userRelationDao.newUsername(id,newName);
    }

    @Override
    public int newPassword(String id, String newPassword) {
        return userRelationDao.newPassword(id,newPassword);
    }


}
