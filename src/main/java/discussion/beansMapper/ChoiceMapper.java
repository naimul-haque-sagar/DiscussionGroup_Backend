package discussion.beansMapper;

import discussion.dto.ChoiceDto;
import discussion.model.AppUser;
import discussion.model.Choice;
import discussion.model.Post;
import discussion.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChoiceMapper {

    @Mapping(target = "choiceId",ignore = true)
    @Mapping(target = "post",source = "post")
    @Mapping(target = "appUser",source ="appUser" )
    Choice toModel(ChoiceDto choiceDto, Post post, AppUser appUser);
}
