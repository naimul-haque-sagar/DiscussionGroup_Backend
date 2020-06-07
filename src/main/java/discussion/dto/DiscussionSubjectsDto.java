package discussion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscussionSubjectsDto {
	private Long id;
	private String subjectName;
	private String description;
	private Integer numberOfPosts;
}
