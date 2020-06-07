package discussion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import discussion.model.Post;
import discussion.model.AppUser;
import discussion.model.Vote;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
   // Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, AppUser currentUser);
}
