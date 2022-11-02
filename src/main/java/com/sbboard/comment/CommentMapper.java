package com.sbboard.comment;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    int create(CommentDto commentDto);

    List<CommentDto> getCommentList();
}
