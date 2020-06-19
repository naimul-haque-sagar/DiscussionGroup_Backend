package discussion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String username;
    private String discussionSubjectName;
    private String postName;
    private String url;
    private String description;
    private Integer choiceCount;
    private Integer commentCount;
    private String duration;
}
