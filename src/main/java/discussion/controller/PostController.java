package discussion.controller;

import discussion.dto.PostRequest;
import discussion.dto.PostResponse;
import discussion.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getSinglePost(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getSinglePost(id));
    }

    @GetMapping("/discussionSubject/{id}")
    public ResponseEntity<List<PostResponse>> getByDiscussionSubject(@PathVariable  Long id){
       return ResponseEntity.status(HttpStatus.OK).body(postService.getByDiscussionSubject(id));
    }

    @GetMapping("/appUser/{name}")
    public ResponseEntity<List<PostResponse>> getByAppUser(@PathVariable("name") String username){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getByAppUser(username));
    }

}
