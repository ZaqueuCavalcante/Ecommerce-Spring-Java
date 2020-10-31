package br.com.zaqueucavalcante.ecommercespringjava.services;

import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import br.com.zaqueucavalcante.ecommercespringjava.entities.users.UserProfile;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.ClientRepository;
import br.com.zaqueucavalcante.ecommercespringjava.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

	private final ClientRepository clientRepository;

	public UserDetailServiceImplementation(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

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
