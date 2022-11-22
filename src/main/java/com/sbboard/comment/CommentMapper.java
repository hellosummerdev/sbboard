package com.sbboard.comment;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    // * 댓글 생성
    int create(CommentDto commentDto);


    // * 댓글 리스트 조회
    List<CommentDto> getCommentList(Integer seq);

    // * 댓글 수정
    int modifyComment(CommentDto commentDto);

    CommentDto selectComment(Integer idx);

    int deleteComment(Integer idx, int seq);
}
