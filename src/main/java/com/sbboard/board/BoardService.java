package com.sbboard.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public List<BoardDto> selectBoard() {
        List<BoardDto> boardList = this.boardMapper.selectBoard();
        return boardList;
    }

    public BoardDto detailBoard(Integer seq) {
        // todo : Optional 클래스 추가해보기
        BoardDto detailBoard = this.boardMapper.detailBoard(seq);
        return detailBoard;
    }


    public int createBoard(BoardDto boardDto) {
        int isCreate = boardMapper.createBoard(boardDto);
        return isCreate;
    }
}
