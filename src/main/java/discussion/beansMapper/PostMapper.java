package discussion.beansMapper;

import com.github.kevinsawicki.timeago.TimeAgo;
import discussion.dto.PostRequest;
import discussion.dto.PostResponse;
import discussion.model.AppUser;
import discussion.model.DiscussionSubjects;
import discussion.model.Post;
import discussion.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;

    @Mapping(target = "createDate", expression = "java(java.time.Instant.now())")
    @Mapping(target="description", source = "postRequest.description")
    @Mapping(target = "discussionSubjects", source="discussionSubjects")
    @Mapping(target = "appUser", source = "appUser")
    @Mapping(target = "choiceCount",constant = "0")
    public abstract Post mapToPost(PostRequest postRequest,DiscussionSubjects discussionSubjects,AppUser appUser);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "username" ,source = "appUser.appUsername")
    @Mapping(target = "discussionSubjectName", source = "discussionSubjects.subjectName")
    @Mapping(target = "commentCount",expression = "java(commentCount(post))")
    @Mapping(target = "duration",expression = "java(getDuration(post))")
    public abstract PostResponse mapToPostResponse(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post){
        return new TimeAgo().timeAgo(post.getCreateDate().toEpochMilli());
    }
}
