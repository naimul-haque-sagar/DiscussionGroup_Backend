package discussion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import discussion.model.DiscussionSubjects;

import java.util.Optional;

@Repository
public interface DiscussionSubjectsRepository extends JpaRepository<DiscussionSubjects, Long> {
	Optional<DiscussionSubjects> findBySubjectName(String subjectName);
}
