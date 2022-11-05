package com.sbboard.comment;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Alias("commentDto")
public class CommentDto {
    private int idx;
    private int seq;

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String comment_content;
    private String user_id;
    private Date created_at;
    private Date modified_at;
}
