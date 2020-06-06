package discussion.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import discussion.dto.SignupConfirmEmail;
import discussion.dto.SignupRequest;
import discussion.exceptions.AppExceptionMessage;
import discussion.model.AppUser;
import discussion.model.SignupVerificationToken;
import discussion.repository.AppUserRepository;
import discussion.repository.SignupVerificationTokenRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
	private final PasswordEncoder passwordEncoder;
	
	private final AppUserRepository appUserRepository;
	
	private final SignupVerificationTokenRepository signupVerificationTokenRepository;
	
	private final MailService mailService;
	
	public void signup(SignupRequest signupRequest) {
		AppUser appUser=new AppUser();
		appUser.setUsername(signupRequest.getUsername());
		appUser.setEmail(signupRequest.getEmail());
		appUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		appUser.setCreated(Instant.now());
		appUser.setEnabled(false);
		
		appUserRepository.save(appUser);
		String signupToken=generateSignupVerificationToken(appUser);
		
		mailService.sendMail(new SignupConfirmEmail("Please activate your account",
				signupRequest.getEmail(),"ThankYou for signing in discussion app, "
						+"please click on the link below to activate your account "
						+ "http://localhost:8080/api/auth/accountVerification/" + signupToken));
	}

	private String generateSignupVerificationToken(AppUser appUser) {
		String signupToken=UUID.randomUUID().toString();
		
		SignupVerificationToken signupVerificationToken=new SignupVerificationToken();
		signupVerificationToken.setSignupToken(signupToken);
		signupVerificationToken.setUser(appUser);
		
		signupVerificationTokenRepository.save(signupVerificationToken);
		
		return signupToken;
	}

	public void accountVerification(String verificationToken) {
		Optional<SignupVerificationToken> token=
				signupVerificationTokenRepository.findBySignupToken(verificationToken);
				token.orElseThrow(()-> new AppExceptionMessage("Invalid verification token"));
		
		enableAppUser(token.get());
	}

	private void enableAppUser(SignupVerificationToken signupVerificationToken) {
		String username=signupVerificationToken.getUser().getUsername();
		AppUser appUser=appUserRepository.findByUsername(username)
				.orElseThrow(()->new AppExceptionMessage("User not found :"+username));
		appUser.setEnabled(true);
		appUserRepository.save(appUser);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
