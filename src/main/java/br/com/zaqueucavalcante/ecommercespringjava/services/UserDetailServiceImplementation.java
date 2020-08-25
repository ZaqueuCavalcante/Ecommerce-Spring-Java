package br.com.zaqueucavalcante.ecommercespringjava.services;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Client;
import br.com.zaqueucavalcante.ecommercespringjava.entities.enums.UserProfile;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.ClientRepository;
import br.com.zaqueucavalcante.ecommercespringjava.security.UserSecurity;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

	private ClientRepository clientRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		Client client = clientRepository.findByEmail(userEmail);
		if (client == null) {
			throw new UsernameNotFoundException(userEmail);
		}
		Long id = client.getId();
		String email = client.getEmail();
		String password = client.getPassword();
		Set<UserProfile> authorities = client.getProfiles();
		UserSecurity userSecurity = new UserSecurity(id, email, password, authorities);
		return userSecurity;
	}
}
