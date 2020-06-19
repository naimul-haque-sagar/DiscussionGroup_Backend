package discussion.repository;

import discussion.model.AppUser;
import discussion.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import discussion.model.Choice;

import java.util.Optional;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    Optional<Choice> findByPostAndAppUser(Post post, AppUser currentUser);
}
