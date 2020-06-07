package discussion.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
	
	@Transactional
	public DiscussionSubjectsDto saveDiscussionSubjects(DiscussionSubjectsDto discussionSubjectsDto) {
		DiscussionSubjects returnedEntity=discussionSubjectsRepository.save(buildDiscussionSubjects(discussionSubjectsDto));
		
		discussionSubjectsDto.setId(returnedEntity.getId());
		return discussionSubjectsDto;
	}

	@Transactional
	public List<DiscussionSubjectsDto> getAll() {
		return discussionSubjectsRepository.findAll().stream().map(this::buildDiscussionSubjectsDto)
		.collect(Collectors.toList());			
	}
	
	private DiscussionSubjects buildDiscussionSubjects(DiscussionSubjectsDto discussionSubjectsDto) {
		return DiscussionSubjects.builder().subjectName(discussionSubjectsDto.getSubjectName())
		.description(discussionSubjectsDto.getDescription()).build();
	}
	
	private DiscussionSubjectsDto buildDiscussionSubjectsDto(DiscussionSubjects discussionSubjects){
		return DiscussionSubjectsDto.builder().subjectName(discussionSubjects.getSubjectName())
				.id(discussionSubjects.getId()).numberOfPosts(discussionSubjects.getPosts().size())
				.build();
	}
	

}
