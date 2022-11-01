package com.sbboard.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BoardService {
    @Autowired
    private BoardMapper boardMapper;

    public BoardService() {
    }

    public List<BoardDto> selectBoard() {
        List<BoardDto> boardList = this.boardMapper.selectBoard();
        return boardList;
    }

    public BoardDto detailBoard(Integer seq) {
        BoardDto detailBoard = this.boardMapper.detailBoard(seq);
        return detailBoard;
    }
}
