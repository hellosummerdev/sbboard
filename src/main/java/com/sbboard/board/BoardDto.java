package com.sbboard.board;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Alias("boardDto")
public class BoardDto {
    private int seq;

    // 점프투스프링부트의 예제에서는 boardForm 클래스를 별도로 만들었으나 Dto에 바로 적용시켰고 문제 없이 작동함
    @NotEmpty(message = "제목은 필수항목입니다.")
    @Size(max = 100)
    private String board_title;

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String board_content;
    private String user_id;
    private boolean use_yn;
    private Date created_at;
    private Date modified_at;
}
