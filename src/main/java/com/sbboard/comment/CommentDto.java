package com.sbboard.comment;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@Alias("commentDto")
public class CommentDto {
    private int idx;
    private int seq;
    private String comment_content;
    private String user_id;
    private Date created_at;
    private Date modified_at;
}
