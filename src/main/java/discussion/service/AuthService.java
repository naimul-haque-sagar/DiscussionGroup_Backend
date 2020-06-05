package discussion.service;

import java.time.Instant;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import discussion.dto.SignupRequest;
import discussion.model.AppUser;
import discussion.repository.AppUserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	private final PasswordEncoder passwordEncoder;
	
	private final AppUserRepository appUserRepository;
	
	@Transactional
	public void signup(SignupRequest signupRequest) {
		AppUser appUser=new AppUser();
		appUser.setUsername(signupRequest.getUsername());
		appUser.setEmail(signupRequest.getEmail());
		appUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		appUser.setCreated(Instant.now());
		appUser.setEnabled(false);
		
		appUserRepository.save(appUser);
	}
}
