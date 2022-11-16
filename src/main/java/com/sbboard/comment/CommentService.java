package com.sbboard.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    public final CommentMapper commentMapper;

    // * 댓글 생성
    public int create(CommentDto commentDto) {
        int isCreate = commentMapper.create(commentDto);
        return isCreate;
    }


    // * 댓글 리스트 조회
    public List<CommentDto> getCommentList(Integer seq) {
        List<CommentDto> commentList = commentMapper.getCommentList(seq);
        return commentList;
    }

    // * 댓글 한 개 조회
    public CommentDto selectComment(Integer idx) {
        CommentDto commentDto = commentMapper.selectComment(idx);
        return commentDto;
    }

    // * 댓글 수정
    public int modifyComment(CommentDto commentDto) {
        int isModify = commentMapper.modifyComment(commentDto);
        return isModify;
    }

}
