package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
class BoardApiController {
    @Autowired
    private BoardRepository repository;

    // Aggregate root

    @GetMapping("/boards")  // 전체 조회
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
                    @RequestParam(required = false, defaultValue = "") String content) {
        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
            return repository.findAll();
        } else {
            return repository.findByTitleOrContent(title,content);
        }
    }

    @PostMapping("/boards") // 추가
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    // Single item

    @GetMapping("/boards/{id}") // id에 해당하는 board 조회
    Board one(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}") // 업데이트
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")  // 삭제
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}