package com.sbboard.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    public final CommentMapper commentMapper;
    public int create(CommentDto commentDto) {
        int isCreate = commentMapper.create(commentDto);
        return isCreate;
    }

    public List<CommentDto> getCommentList(Integer seq) {
        List<CommentDto> commentList = commentMapper.getCommentList(seq);
        return commentList;
    }
}
