package discussion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import discussion.model.Post;
import discussion.model.DiscussionSubjects;
import discussion.model.AppUser;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
   // List<Post> findAllBySubreddit(DiscussionSubjects subreddit);

   // List<Post> findByUser(AppUser user);
}
