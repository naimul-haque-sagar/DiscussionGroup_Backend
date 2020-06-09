package discussion.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import discussion.beansMapper.DiscussionSubjectsMapper;
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
	
	@Transactional
	public DiscussionSubjectsDto saveDiscussionSubjects(DiscussionSubjectsDto discussionSubjectsDto) {
		DiscussionSubjects returnedEntity=discussionSubjectsRepository.save(discussionSubjectsMapper.mapToModel(discussionSubjectsDto));
		
		discussionSubjectsDto.setId(returnedEntity.getId());
		return discussionSubjectsDto;
	}

	@Transactional
	public List<DiscussionSubjectsDto> getAll() {
		return discussionSubjectsRepository.findAll().stream().map(discussionSubjectsMapper::mapToDto)
		.collect(Collectors.toList());			
	}

	

}
