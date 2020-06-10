package discussion.repository;

import java.util.List;
import java.util.Optional;

import discussion.dto.PostResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import discussion.model.Post;
import discussion.model.DiscussionSubjects;
import discussion.model.AppUser;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByDiscussionSubjects(DiscussionSubjects discussionSubjects);

    List<Post> findByAppUser(AppUser appUser);
}
