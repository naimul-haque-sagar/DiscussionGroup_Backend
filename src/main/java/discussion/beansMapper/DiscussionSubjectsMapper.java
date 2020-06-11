package discussion.beansMapper;

import discussion.dto.DiscussionSubjectsDto;
import discussion.model.AppUser;
import discussion.model.DiscussionSubjects;
import discussion.model.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiscussionSubjectsMapper {
    @Mapping(target = "numberOfPosts",expression = "java(countPosts(discussionSubjects.getPosts()))")
    DiscussionSubjectsDto mapToDto(DiscussionSubjects discussionSubjects);

    default  Integer countPosts(List<Post> numberOfPosts){
        return numberOfPosts.size();
    }

    @Mapping(target = "posts" ,ignore = true)
    @Mapping(target = "createDate",expression = "java(java.time.Instant.now())")
    @Mapping(target = "appUser",source = "appUser")
    DiscussionSubjects mapToModel(DiscussionSubjectsDto discussionSubjectsDto, AppUser appUser);
}
