package discussion.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import discussion.dto.SignupRequest;
import discussion.service.AuthService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping
	public void signup(@RequestBody SignupRequest signupRequest) {
		authService.signup(signupRequest);
	}
}
