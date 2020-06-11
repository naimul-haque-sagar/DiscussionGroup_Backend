package discussion.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import discussion.beansMapper.DiscussionSubjectsMapper;
import discussion.exceptions.AppExceptionMessage;
import org.springframework.stereotype.Service;

import discussion.dto.DiscussionSubjectsDto;
import discussion.model.DiscussionSubjects;
import discussion.repository.DiscussionSubjectsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class DiscussionSubjectsService {
	private final DiscussionSubjectsRepository discussionSubjectsRepository;

	private final DiscussionSubjectsMapper discussionSubjectsMapper;

	private final AuthService authService;

	@Transactional
	public DiscussionSubjectsDto saveDiscussionSubjects(DiscussionSubjectsDto discussionSubjectsDto) {
		DiscussionSubjects discussionSubjects=discussionSubjectsRepository.save(discussionSubjectsMapper.mapToModel(discussionSubjectsDto,authService.currentUser()));
		if(discussionSubjects.getPosts()==null){
			discussionSubjectsDto.setId(discussionSubjects.getId());
			return discussionSubjectsDto;
		}
		return discussionSubjectsMapper.mapToDto(discussionSubjects);
	}

	//update discussion subject

	@Transactional
	public List<DiscussionSubjectsDto> getAll() {
		return discussionSubjectsRepository.findAll().stream().map(discussionSubjectsMapper::mapToDto)
		.collect(Collectors.toList());			
	}

	@Transactional
    public DiscussionSubjectsDto getDiscussionSubject(Long id) {
    	return discussionSubjectsMapper.mapToDto(discussionSubjectsRepository.findById(id)
				.orElseThrow(()-> new AppExceptionMessage("Error occured while retriving single DiscussionSubject")));
	}
}
