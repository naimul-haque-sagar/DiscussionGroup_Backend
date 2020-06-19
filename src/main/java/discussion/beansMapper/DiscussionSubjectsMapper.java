package discussion.beansMapper;

import discussion.dto.DiscussionSubjectsDto;
import discussion.exceptions.AppExceptionMessage;
import discussion.model.AppUser;
import discussion.model.DiscussionSubjects;
import discussion.model.Post;
import discussion.repository.DiscussionSubjectsRepository;
import discussion.repository.PostRepository;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DiscussionSubjectsMapper {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DiscussionSubjectsRepository discussionSubjectsRepository;

    @Mapping(target = "numberOfPosts",expression = "java(countPosts(discussionSubjects.getId()))")
    public abstract DiscussionSubjectsDto mapToDto(DiscussionSubjects discussionSubjects);

    Long countPosts(Long id){
        return postRepository.findByDiscussionSubjects(discussionSubjectsRepository.findById(id)
                .orElseThrow(()->new AppExceptionMessage("No discussion subject found"))).stream().count();
    }

    @Mapping(target = "createDate",expression = "java(java.time.Instant.now())")
    @Mapping(target = "appUser",source = "appUser")
    public abstract DiscussionSubjects mapToModel(DiscussionSubjectsDto discussionSubjectsDto, AppUser appUser);
}
