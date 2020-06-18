package discussion.beansMapper;

import discussion.dto.CommentDto;
import discussion.model.AppUser;
import discussion.model.Comment;
import discussion.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "post",source = "post")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "appUser",source = "appUser")
    Comment mapToComment(CommentDto commentDto, Post post, AppUser appUser);

    @Mapping(target = "postId" ,expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName",expression = "java(comment.getAppUser().getAppUsername())")
    CommentDto mapToDto(Comment comment);
}
