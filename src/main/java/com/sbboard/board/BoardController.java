package com.sbboard.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;


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

    @PostMapping("/board/list")
    public Map<String, Object> boardList() {
        System.out.println("boardList 진입");
        Map<String, Object> map = new HashMap();

        try {
            List<BoardDto> boardList = this.boardService.selectBoard();
            map.put("data", boardList);
            map.put("status", 200);
        } catch (Exception var3) {
            map.put("status", 500);
        }

        return map;
    }

    @GetMapping("/board/detail/{seq}")
    public String detail(Model model, @PathVariable("seq") Integer seq) {
        try {
            BoardDto detailBoard = this.boardService.detailBoard(seq);
            model.addAttribute("detail", detailBoard);
        } catch (Exception var4) {
            model.addAttribute("status", 500);
        }

        return "board/detail";
    }
}
