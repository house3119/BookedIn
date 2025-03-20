package backend25.bookedin.web;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import backend25.bookedin.model.AppUser;
import backend25.bookedin.model.AppUserRepository;

/**
 * This class is used by spring security to authenticate and authorize user
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService  {
	
	//@Autowired
	AppUserRepository repository;
	
	// Constructor Injection
	public UserDetailServiceImpl(AppUserRepository appUserRepository) {
		this.repository = appUserRepository; 
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{   
		AppUser curruser = repository.findByUsernameIgnoreCase(username);
			UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPassword_hash(), 
					AuthorityUtils.createAuthorityList(curruser.getAccount_type().getAccount_type()));
			return user;
	}

} 
