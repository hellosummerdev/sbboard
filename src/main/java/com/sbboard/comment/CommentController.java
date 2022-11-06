package com.sbboard.comment;

import com.sbboard.board.BoardDto;
import com.sbboard.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor //lombok, Autowired보다 추천되는 방법이라고 함
public class CommentController {
    private final BoardService boardService;
    private final CommentService commentService;

    // todo : 댓글 create 404에러 수정하기
    @PostMapping("/create/{seq}")
    // 파라미터 순서에도 영향을 받기 때문에 Dto 다음 BindingResult가 와야 함
    public String createComment(@Valid CommentDto commentDto, BindingResult bindingResult, Model model, @PathVariable("seq") Integer seq

//            , HttpSession session
    ) {
//        String user_id = (String) session.getAttribute("user_id");
//        commentDto.setUser_id(user_id);
        System.out.println("create comment 진입");
        if (bindingResult.hasErrors()) {
            System.out.println("에러 진입");
//            model.addAttribute("boardDto", new BoardDto());
//            return String.format("redirect:/board/detail/%s", seq);
            return "redirect:/board/detail/{seq}";
        }

        try {
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
}
