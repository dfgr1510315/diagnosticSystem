package com.ljz.diagnostic_system.dao;

import com.ljz.diagnostic_system.model.UserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserRelationDao {
    //@Select("select * from room where RoomId=#{id};")

    //List<UserProfile> loadUserByEmail(@Param("email")String email);
    int register(UserProfile userProfile);

    UserProfile loadUserByEmail(@Param("email")String email);

    UserProfile loadUserById(@Param("id")String id);

    int newUsername(@Param("id")String id,@Param("newName")String newName);

    int newPassword(@Param("id")String id,@Param("password")String password);
}
