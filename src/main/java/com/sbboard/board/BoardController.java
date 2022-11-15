package com.sbboard.board;

import com.sbboard.comment.CommentDto;
import com.sbboard.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    // * 게시판 전체목록 조히
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

    // * 게시판 상세페이지 조회
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
            if (commentList.isEmpty()) { // commentList==null 이 아닌 isEmpty로 빈 테이블 체크
                System.out.println("comment 없음");
                model.addAttribute("commentList", commentList);
            } else {
                System.out.println("comment 있음");
                model.addAttribute("commentList", commentList);
            }
            System.out.println("detail 정상 종료");
        } catch (Exception e) {
            model.addAttribute("status", 500);
        }

        return "board/detail";
    }

    // * 게시글 생성
    @GetMapping("/create")
    public String createBoard(BoardDto boardDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return "board/board_form";
        }
        return "redirect:/user/login";
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


    // * 게시글 수정
    @GetMapping("/modify/{seq}")
    public String modifyBoard(BoardDto boardDto, @PathVariable("seq") Integer seq, Model model) {
        // * 수정할 게시글 불러오기
        boardDto = this.boardService.detailBoard(seq);
        // * model을 통해 기존 글의 제목과 내용을 가져온다
        model.addAttribute("boardDto", boardDto);
        return "board/board_form";
    }
    @PostMapping("/modify/{seq}")
    public String modifyBoard(@Valid BoardDto boardDto, BindingResult bindingResult,
                              @PathVariable("seq") Integer seq, HttpServletRequest request) {
        // * 필수 입력항목 체크
        if (bindingResult.hasErrors()) {
            System.out.println("bindingresult 에러");
            return "board/board_form";
        }

        HttpSession session = request.getSession(false);
        // * session이 없으면 로그인 다시 하기
        if (session == null) {
            return "redirect:/user/login";
        }

        // * session에 저장된 user_id 가져오기
        String user_id = (String) session.getAttribute("user_id");
        System.out.println("session user_id : " + user_id);
        System.out.println("boardDto user_id : " + boardDto.getUser_id());

        // * session의 유저와 게시글의 유저가 같은지 확인하기
        if (user_id.equals(boardDto.getUser_id())) {
            // * 유저가 같으면 글 수정
            int isModify = boardService.modifyBoard(boardDto);
            // * 수정에 성공하여 1을 리턴했는지 확인
            if (isModify == 1) {
                System.out.println("수정 성공");
                return "redirect:/board/detail/{seq}";
            } else {
                // * 실패시 다시 수정할 수 있도록 해당 게시글로 보내기
                System.out.println("수정 실패");
                return "redirect:/board/detail/{seq}";
            }
        } else {
            // * session의 유저와 같지 않을 경우 수정권한 없음 메세지 (콘솔에 뜨네?)
            System.out.println("수정권한이 없습니다.");
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
            return "redirect:/board/detail/{seq}";
        }

    }

    // * 게시글 삭제
    @GetMapping("/delete/{seq}")
    public String deleteBoard(@PathVariable("seq") Integer seq, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        // * 세션이 없으면 로그인
        if (session == null) {
            return "redirect:/user/login";
        }

        // * session에서 아이디 가져오기
        String user_id = (String) session.getAttribute("user_id");
        System.out.println("session user_id : " + user_id);
        // * seq로 해당 게시물 가져오기
        BoardDto boardDto = boardService.detailBoard(seq);
        System.out.println("boardDto user_id : " + boardDto.getUser_id());

        // * 세션 사용자와 게시글 사용자 일치 확인
        if (user_id.equals(boardDto.getUser_id())) {
            System.out.println("delete 진입");
            int isDelete = boardService.deleteBoard(seq);
            if (isDelete == 1) {
                // * 성공시 게시판 목록으로 보내기
                return "redirect:/";
            } else {
                // * 실패시 다시 삭제할 수 있도록 해당 게시글로 보내기
                return "redirect:/detail/{seq}";
            }
        } else {
            System.out.println("수정권한이 없습니다.");
            return "redirect:/board/detail/{seq}";
        }
    }
}
