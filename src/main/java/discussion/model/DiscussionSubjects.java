package discussion.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class DiscussionSubjects {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="Discussion Subject name is required")
	private String subjectName;
	
	@NotBlank(message="Description is required")
	private String description;
	
	private Instant createDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appUserId" ,referencedColumnName = "appUserId")
	private AppUser appUser;
}
