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
    public String detail(Model model, @PathVariable("seq") Integer seq, CommentDto commentDto) {
        try {
            System.out.println("보드 상새내용 진입");
            // 보드 상세 내용 불러오기
            BoardDto detailBoard = this.boardService.detailBoard(seq);
            model.addAttribute("detail", detailBoard);

            System.out.println("댓글 리스트 진입");
            // 댓글 리스트 불러오기
            List<CommentDto> commentList = this.commentService.getCommentList(seq);
            if (commentList == null) {
                System.out.println("commentList == null");
                model.addAttribute("commentList", 0);
                System.out.println("comment 없음");
            } else {
                // todo : commentList가 없는데 if 안으로 안넘어가지고 else로 넘어가지는 문제
                System.out.println("comment 있음");
                model.addAttribute("commentList", commentList);
                model.addAttribute("commentDto", commentDto);
            }
//            System.out.println("댓글 폼에 넘겨줄 오브젝트 진입");
            // 댓글 폼을 위해 빈 오브젝트를 view에 넘겨주기
//            model.addAttribute("commentDto", commentDto);
            System.out.println("detail 정상 종료");
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
