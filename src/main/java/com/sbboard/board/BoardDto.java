package com.sbboard.board;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@Alias("boardDto")
public class BoardDto {
    private int seq;
    private String board_title;
    private String board_content;
    private String user_id;
    private boolean use_yn;
    private Date created_at;
    private Date modified_at;
}
