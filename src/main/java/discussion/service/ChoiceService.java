package discussion.service;

import discussion.beansMapper.ChoiceMapper;
import discussion.dto.ChoiceDto;
import discussion.exceptions.AppExceptionMessage;
import discussion.model.Choice;
import discussion.model.Post;
import discussion.repository.ChoiceRepository;
import discussion.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChoiceService {
    private final ChoiceRepository choiceRepository;

    private final PostRepository postRepository;

    private final AuthService authService;

    private final ChoiceMapper choiceMapper;

    @Transactional
    public void choice(ChoiceDto choiceDto) {
        Post post=postRepository.findById(choiceDto.getPostId()).orElseThrow(()->new AppExceptionMessage("Post not found"));
        Optional<Choice> lastChoice=choiceRepository.findByPostAndAppUser(post,authService.currentUser());
        if(lastChoice.isPresent() && lastChoice.get().getChoiceType()==choiceDto.getChoiceType()){
            throw new AppExceptionMessage("Already clicked");
        }
        if(choiceDto.getChoiceType()==1){
            post.setChoiceCount(post.getChoiceCount()+1);
        }else post.setChoiceCount(post.getChoiceCount()-1);

        postRepository.save(post);

        if(lastChoice.isPresent()){
            Choice choice=choiceRepository.findById(lastChoice.get().getChoiceId())
                    .orElseThrow(()->new AppExceptionMessage("Choice not fond"));
            choice.setChoiceType(choiceDto.getChoiceType());
            choiceRepository.save(choice);
        }else{
            choiceRepository.save(choiceMapper.toModel(choiceDto,post,authService.currentUser()));
        }
    }
}
