package discussion.service;

import discussion.beansMapper.PostMapper;
import discussion.dto.PostRequest;
import discussion.dto.PostResponse;
import discussion.exceptions.AppExceptionMessage;
import discussion.exceptions.DiscussionSubjectsException;
import discussion.exceptions.PostNotFoundException;
import discussion.model.AppUser;
import discussion.model.DiscussionSubjects;
import discussion.model.Post;
import discussion.repository.AppUserRepository;
import discussion.repository.DiscussionSubjectsRepository;
import discussion.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final AuthService authService;

    private final PostRepository postRepository;

    private final DiscussionSubjectsRepository discussionSubjectsRepository;

    private final AppUserRepository appUserRepository;

    private final PostMapper postMapper;

    @Transactional
    public void save(PostRequest postRequest) {
        DiscussionSubjects discussionSubjects=discussionSubjectsRepository.findBySubjectName(postRequest.getDiscussionSubjectsName())
                .orElseThrow(()->new DiscussionSubjectsException("Discussion subjects not found"+postRequest.getDiscussionSubjectsName()));
        postRepository.save(postMapper.mapToPost(postRequest,discussionSubjects,authService.currentUser()));
    }

    @Transactional
    public PostResponse getSinglePost(Long id) {
        return postMapper.mapToPostResponse(postRepository.findById(id)
                .orElseThrow(()->new PostNotFoundException("Post not found")));
    }

    @Transactional
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::mapToPostResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> getByDiscussionSubject(Long id) {
        DiscussionSubjects discussionSubjects=discussionSubjectsRepository.findById(id)
                .orElseThrow(()->new DiscussionSubjectsException("Discussion subject not found"));
        return postRepository.findByDiscussionSubjects(discussionSubjects)
                .stream().map(postMapper::mapToPostResponse).collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> getByAppUser(String username) {
        AppUser appUser=appUserRepository.findByAppUsername(username)
                .orElseThrow(()->new AppExceptionMessage("App User not found"));
        return postRepository.findByAppUser(appUser)
                .stream().map(postMapper::mapToPostResponse).collect(Collectors.toList());
    }
}
