package discussion.beansMapper;

import discussion.dto.DiscussionSubjectsDto;
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

    @InheritInverseConfiguration
    @Mapping(target = "posts" ,ignore = true)
    DiscussionSubjects mapToModel(DiscussionSubjectsDto discussionSubjectsDto);
}
