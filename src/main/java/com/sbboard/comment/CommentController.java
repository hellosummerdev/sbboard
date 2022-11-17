package com.sbboard.comment;

import com.sbboard.board.BoardDto;
import com.sbboard.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor //lombok, Autowired보다 추천되는 방법이라고 함
public class CommentController {
    private final BoardService boardService;
    private final CommentService commentService;

    // * 댓글 생성
    @PostMapping("/create/{seq}")
    // 파라미터 순서에도 영향을 받기 때문에 Dto 다음 BindingResult가 와야 함
    public String createComment(@Valid CommentDto commentDto, BindingResult bindingResult,
                                Model model, @PathVariable("seq") Integer seq
            , HttpServletRequest request
    ) {


        if (bindingResult.hasErrors()) {
            System.out.println("에러 진입");
            return "redirect:/board/detail/{seq}";
        }

        try {
            System.out.println("create comment 진입");
            HttpSession session = request.getSession();
            String user_id = (String) session.getAttribute("user_id");
            commentDto.setUser_id(user_id);
            commentDto.setSeq(seq);
            int isCreate = commentService.create(commentDto);
            if (isCreate == 1) {
                System.out.println("댓글 저장 성공");
            } else {
                System.out.println("댓글 저장 실패");
                // todo : 댓글 저장 실패 시 alert 메세지 어떻게 띄우는지 찾아보기
            }
            return String.format("redirect:/board/detail/%s", seq);
        } catch (Exception e) {
            return "redirect:/board/detail/{seq}";
        }

    }

    // * 댓글 수정
    @GetMapping("/modify/{idx}")
    public String modifyComment(CommentDto commentDto, @PathVariable("idx") Integer idx,
                                Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        // * session이 없으면 로그인 다시 하기
        if (session == null) {
            return "redirect:/user/login";
        }

        // * session에 저장된 user_id 가져오기
        String user_id = (String) session.getAttribute("user_id");
        commentDto = commentService.selectComment(idx);

        System.out.println("session user_id : " + user_id);
        System.out.println("commentDto user_id : " + commentDto.getUser_id());

        // * session의 유저와 댓글의 유저가 같은지 확인하기
        if (user_id.equals(commentDto.getUser_id())) {
            model.addAttribute("commentDto", commentDto);
            return "comment/comment_form";
        } else {
            // todo : 원래 글로 보내려면 seq가 필요. 어떻게 가져오지?
            return "redirect:/";
        }
    }

    @PostMapping("/modify/{idx}")
    public  String modifyComment(@Valid CommentDto commentDto, BindingResult bindingResult,
//                                 @PathVariable("idx") Integer idx, Model model,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        // * session이 없으면 로그인 다시 하기
        if (session == null) {
            return "redirect:/user/login";
        }

        // * 필수 입력항목 체크
        if (bindingResult.hasErrors()) {
            System.out.println("bindingresult 에러");
            return "comment/comment_form";
        }

        // * session에 저장된 user_id 가져오기
        String user_id = (String) session.getAttribute("user_id");
        System.out.println("session user_id : " + user_id);
        System.out.println("commentDto user_id : " + commentDto.getUser_id());

        int seq = commentDto.getSeq();

        // * session의 유저와 댓글의 유저가 같은지 확인하기
        if (user_id.equals(commentDto.getUser_id())) {
            // * 유저가 같으면 댓글 수정
            System.out.println("댓글 수정할 commentDto select");
            int isModify = commentService.modifyComment(commentDto);
            System.out.println(commentDto.getIdx());
            System.out.println(commentDto.getSeq());
            System.out.println(commentDto.getUser_id());
            System.out.println(commentDto.getComment_content());
            // * 수정에 성공하면 1을 리턴
            if (isModify == 1) {
                System.out.println("수정 성공");
                return String.format("redirect:/board/detail/%s", seq);
            } else {
                // * 실패시 다시 수정할 수 있도록 해당 게시글로 보내가
                System.out.println("수정 실패");
                return String.format("redirect:/board/detail/%s", seq);
            }
        } else {
            System.out.println("session 유저와 댓글의 유저가 다름");
            return String.format("redirect:/board/detail/%s", seq);
        }
    }

}
