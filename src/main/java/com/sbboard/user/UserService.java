package com.sbboard.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    // * 아이디 중복체크
    public int isExist(String user_id) {
        int isExist = userMapper.checkId(user_id);
        if (isExist == 1) {
            System.out.println("중복된 아이디입니다.");
        } else {
            System.out.println("사용가능한 아이디입니다.");
        }
            return isExist;
    }

    // * 회원가입
    public int createUser(UserDto userDto) {

        int isCreate = 0;

        try {
            isCreate = userMapper.createUser(userDto);
            System.out.println("회원가입 성공");
            return isCreate;
        } catch (Exception e) {
            System.out.println("회원가입 실패");
            return isCreate;
        }
    }

    // * 로그인 아이디, 비밀번호 체크
    public int userCheck(UserDto userDto) {
        int isUser = userMapper.userCheck(userDto);
        return isUser;
    }

//    public Optional<UserDto> findById(String user_id) {
//        Optional<UserDto> _user = userMapper.findById(user_id);
//        return _user;
//    }
}
