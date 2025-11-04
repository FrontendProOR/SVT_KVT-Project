package fitpass.fitpass.controller;

import fitpass.fitpass.model.dto.CommentDTO;
import fitpass.fitpass.model.entity.Comment;
import fitpass.fitpass.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping
    ResponseEntity<CommentDTO> create(@RequestBody @Validated CommentDTO commentDto){
        Comment comment = commentService.create(commentDto);

        if(comment == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        CommentDTO commentDto1 = new CommentDTO(comment);
        return new ResponseEntity<>(commentDto1,HttpStatus.OK);
    }
}
