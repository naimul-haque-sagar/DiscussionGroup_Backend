package discussion.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import discussion.dto.JwtResponse;
import discussion.dto.LoginRequest;
import discussion.dto.SignupConfirmEmail;
import discussion.dto.SignupRequest;
import discussion.exceptions.AppExceptionMessage;
import discussion.model.AppUser;
import discussion.model.AppUserGroup;
import discussion.model.SignupVerificationToken;
import discussion.repository.AppUserGroupRepository;
import discussion.repository.AppUserRepository;
import discussion.repository.SignupVerificationTokenRepository;
import discussion.security.JwtProvider;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
	private final PasswordEncoder passwordEncoder;
	
	private final AppUserRepository appUserRepository;
	
	private final SignupVerificationTokenRepository signupVerificationTokenRepository;
	
	private final MailService mailService;
	
	private final AppUserGroupRepository appUserGroupRepository;
	
	private final AuthenticationManager authenticationManager;
	
	private final JwtProvider jwtProvider;
	
	public void signup(SignupRequest signupRequest) {
		AppUser appUser=new AppUser();
		appUser.setAppUsername(signupRequest.getUsername());
		appUser.setEmail(signupRequest.getEmail());
		appUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		appUser.setCreated(Instant.now());
		appUser.setEnabled(false);
		
		appUserRepository.save(appUser);
		
		AppUserGroup appUserGroup=new AppUserGroup();
		appUserGroup.setAppUsername(signupRequest.getUsername());
		appUserGroup.setAppUserGroup("user");
		
		appUserGroupRepository.save(appUserGroup);
		
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
		signupVerificationToken.setAppUser(appUser);
		
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
		String username=signupVerificationToken.getAppUser().getAppUsername();
		AppUser appUser=appUserRepository.findByAppUsername(username)
				.orElseThrow(()->new AppExceptionMessage("User not found :"+username));
		appUser.setEnabled(true);
		appUserRepository.save(appUser);
	}

	public JwtResponse login(LoginRequest loginRequest) {
		Authentication authentication= authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
						loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwtToken=jwtProvider.generateJwtToken(authentication);
		return new JwtResponse(jwtToken,loginRequest.getUsername());
	}

	@Transactional
	public AppUser currentUser(){
		User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return appUserRepository.findByAppUsername(user.getUsername())
				.orElseThrow(()->new UsernameNotFoundException("User name not found"+user.getUsername()));
	}
	
}
