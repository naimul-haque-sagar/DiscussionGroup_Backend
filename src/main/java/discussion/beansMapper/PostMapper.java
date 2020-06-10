package discussion.beansMapper;

import discussion.dto.PostRequest;
import discussion.dto.PostResponse;
import discussion.model.AppUser;
import discussion.model.DiscussionSubjects;
import discussion.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createDate", expression = "java(java.time.Instant.now())")
    @Mapping(target="description", source = "postRequest.description")
    Post mapToPost(PostRequest postRequest,DiscussionSubjects discussionSubjects,AppUser appUser);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "username" ,source = "appUser.appUsername")
    @Mapping(target = "discussionSubjectName", source = "discussionSubjects.subjectName")
    PostResponse mapToPostResponse(Post post);

}
