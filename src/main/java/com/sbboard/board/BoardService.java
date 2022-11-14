package com.sbboard.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    // * 게시판 전체목록 조회
    public List<BoardDto> selectBoard() {
        List<BoardDto> boardList = this.boardMapper.selectBoard();
        return boardList;
    }

    // * 게시판 상세페이지 조회
    public BoardDto detailBoard(Integer seq) {
        BoardDto detailBoard = this.boardMapper.detailBoard(seq);
        return detailBoard;
    }

    // * 게시글 생성
    public int createBoard(BoardDto boardDto) {
        int isCreate = boardMapper.createBoard(boardDto);
        return isCreate;
    }

    // * 게시글 수정
    public int modifyBoard(BoardDto boardDto) {
        int isModify = boardMapper.modifyBoard(boardDto);
        return isModify;
    }
}
