package com.sbboard.comment;

import com.sbboard.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private BoardService boardService;

    @PostMapping("/create/{seq}")
    public String createComment(Model model, @PathVariable("seq") Integer seq, @RequestParam String comment_content) {
       // todo : 답변 저장하기
        return String.format("redirect:/board/detail/%s", seq);
    }
}
