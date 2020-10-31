package br.com.zaqueucavalcante.ecommercespringjava.services;

import br.com.zaqueucavalcante.ecommercespringjava.security.UserSecurity;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

	public static UserSecurity authenticated() {
		try {
			return (UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
