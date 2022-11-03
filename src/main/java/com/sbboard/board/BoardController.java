package com.sbboard.board;

import com.sbboard.comment.CommentDto;
import com.sbboard.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    // 게시글 전체목록 조히
    @GetMapping("/board")
    public String board(Model model) {
        try {
            List<BoardDto> boardList = this.boardService.selectBoard();
            model.addAttribute("boardList", boardList);
        } catch (Exception e) {
            model.addAttribute("status", 500);
        }

        return "board/board";
    }

    @PostMapping("/list")
    public Map<String, Object> boardList() {
        System.out.println("boardList 진입");
        Map<String, Object> map = new HashMap();

        try {
            List<BoardDto> boardList = this.boardService.selectBoard();
            map.put("data", boardList);
            map.put("data", boardList);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 500);
        }

        return map;
    }

    // 게시글 상세페이지
    @GetMapping("/detail/{seq}")
    public String detail(Model model, @PathVariable("seq") Integer seq) {
        try {
            BoardDto detailBoard = this.boardService.detailBoard(seq);
            model.addAttribute("detail", detailBoard);

            // 댓글 리스트
            List<CommentDto> commentList = this.commentService.getCommentList(seq);
            if (commentList == null) {
                System.out.println("commentList == null");
                model.addAttribute("commentList", 0);
            } else {
                model.addAttribute("commentList", commentList);
            }
        } catch (Exception e) {
            model.addAttribute("status", 500);
        }

        return "board/detail";
    }

    // 신규 게시글
    @GetMapping("/create")
    public String createBoard(BoardDto boardDto) {
        return "board/board_form";
    }

    @PostMapping("/create")
    public String createBoard(@Valid BoardDto boardDto, BindingResult bindingResult) {
        // 오류가 있는 경우에 다시 폼 작성 화면 렌더링
        if (bindingResult.hasErrors()) {
            return "board/board_form";
        }

        try {
            int isCreate = boardService.createBoard(boardDto);

            if (isCreate == 1) {
                System.out.println("게시글 등록 성공");
            } else {
                System.out.println("게시글 등록 실패");
            }
            return "redirect:board/board";
        } catch (Exception e) {
            return "redirect:board/board";
        }
    }
}
