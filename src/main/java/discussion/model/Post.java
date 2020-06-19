package discussion.model;

import java.time.Instant;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;
	
	@NotBlank(message="Post Name can not be null")
	private String postName;
	
	@Nullable
	private String url;
	
	@Nullable
	@Lob
	private String description;

	//should be changed to choiceNumber
	@Builder.Default
	private Integer choiceCount=0;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="appUserId", referencedColumnName ="appUserId" )
	private AppUser appUser;
	
	private Instant createDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id",referencedColumnName = "id")
	private DiscussionSubjects discussionSubjects;
}
