package com.sbboard.user;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Alias("userDto")
public class UserDto {
    private int seq;

    @NotEmpty(message = "ID는 필수항목입니다.")
    private String user_id;

    @NotEmpty(message = "이름은 필수항목입니다.")
    private String user_name;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String user_pwd;

    private boolean use_yn;
    private Date created_at;
    private Date modified_at;
}
