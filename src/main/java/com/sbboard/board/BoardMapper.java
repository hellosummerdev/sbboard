package com.sbboard.board;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    // * 게시판 목록 조회
    List<BoardDto> selectBoard();

    // * 게시판 상세페이지 조회
    BoardDto detailBoard(Integer seq);

    // * 게시판 글 생성
    int createBoard(BoardDto boardDto);
}
