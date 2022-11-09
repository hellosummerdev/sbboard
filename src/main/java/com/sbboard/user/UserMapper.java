package com.sbboard.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    // * 아이디 중복체크
    int checkId(String user_id);

    // * 회원가입
    int createUser(UserDto userDto);
}
