package discussion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import discussion.model.SignupVerificationToken;

import java.util.Optional;

@Repository
public interface SignupVerificationTokenRepository extends JpaRepository<SignupVerificationToken, Long> {
    Optional<SignupVerificationToken> findBySignupToken(String verificationToken);
}
