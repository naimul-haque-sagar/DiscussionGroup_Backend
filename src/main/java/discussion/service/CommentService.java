package discussion.service;

import discussion.beansMapper.CommentMapper;
import discussion.dto.CommentDto;
import discussion.exceptions.AppExceptionMessage;
import discussion.model.AppUser;
import discussion.model.Post;
import discussion.repository.AppUserRepository;
import discussion.repository.CommentRepository;
import discussion.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final PostRepository postRepository;

    private final AuthService authService;

    private final AppUserRepository appUserRepository;

    public void postComment(CommentDto commentDto) {
        Post post=postRepository.findById(commentDto.getPostId()).orElseThrow(()->new AppExceptionMessage("Post not found"));
        commentRepository.save(commentMapper.mapToComment(commentDto,post,
                authService.currentUser()));

        //you can send a mail confirmation
    }

    public List<CommentDto> getAllCommentsFromPost(Long postId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new AppExceptionMessage("Post not found"));
        return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public List<CommentDto> getAllCommentsFromUser(String userName) {
        AppUser appUser=appUserRepository.findByAppUsername(userName).orElseThrow(()->new AppExceptionMessage("AppUser not found"));
        return commentRepository.findByAppUser(appUser).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }
}
