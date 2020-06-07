package discussion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import discussion.model.Comment;
import discussion.model.Post;
import discussion.model.AppUser;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
   // List<Comment> findByPost(Post post);

    //List<Comment> findAllByUser(AppUser user);
}
