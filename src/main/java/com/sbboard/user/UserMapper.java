package com.sbboard.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    // * 아이디 중복체크
    int checkId(String user_id);

    // * 회원가입
    int createUser(UserDto userDto);

    // * 로그인
    int userCheck(UserDto userDto);

//    Optional<UserDto> findById(String user_id);
}
