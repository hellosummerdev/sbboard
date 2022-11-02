package com.sbboard.board;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardDto> selectBoard();

    BoardDto detailBoard(Integer seq);
}
