package br.com.zaqueucavalcante.ecommercespringjava.resources;

import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.EmailDTO;
import br.com.zaqueucavalcante.ecommercespringjava.security.JWTUtil;
import br.com.zaqueucavalcante.ecommercespringjava.security.UserSecurity;
import br.com.zaqueucavalcante.ecommercespringjava.services.AuthService;
import br.com.zaqueucavalcante.ecommercespringjava.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	private final JWTUtil jwtUtil;
	private final AuthService authService;

	public AuthResource(JWTUtil jwtUtil, AuthService authService) {
		this.jwtUtil = jwtUtil;
		this.authService = authService;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSecurity user = UserService.authenticated();
		assert user != null;
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PostMapping(value = "/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) {
		authService.sendNewPassword(emailDTO.getEmail());
		return ResponseEntity.noContent().build();
	}

}
