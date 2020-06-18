package discussion.controller;

import discussion.dto.CommentDto;
import discussion.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
@AllArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity postComment(@RequestBody CommentDto commentDto){
        commentService.postComment(commentDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsFromPost(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsFromPost(postId));
    }

    @GetMapping("user/{userName}")
    public ResponseEntity<List<CommentDto>> getAllCommentsFromUser(@PathVariable String userName){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsFromUser(userName));
    }
}
