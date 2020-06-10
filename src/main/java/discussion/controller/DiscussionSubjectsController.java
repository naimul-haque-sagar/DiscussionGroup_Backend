package discussion.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import discussion.dto.DiscussionSubjectsDto;
import discussion.service.DiscussionSubjectsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/discussionSubject")
@AllArgsConstructor  
@Slf4j
public class DiscussionSubjectsController {
	private final DiscussionSubjectsService discussionSubjectsService; 
	
	@PostMapping()
	public ResponseEntity<DiscussionSubjectsDto> createDiscussionSubjects(@RequestBody DiscussionSubjectsDto discussionSubjectsDto) {
		return ResponseEntity.status(HttpStatus.CREATED)
		.body(discussionSubjectsService.saveDiscussionSubjects(discussionSubjectsDto));
	}
	
	@GetMapping()
	public ResponseEntity<List<DiscussionSubjectsDto>> getAllDiscussionSubjects() {
		return ResponseEntity.status(HttpStatus.OK).body(discussionSubjectsService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<DiscussionSubjectsDto> getDiscussionSubject(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(discussionSubjectsService.getDiscussionSubject(id));
	}
}
