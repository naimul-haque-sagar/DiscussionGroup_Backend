package discussion.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import discussion.exceptions.AppExceptionMessage;
import discussion.model.AppUser;
import discussion.model.AppUserGroup;
import discussion.repository.AppUserGroupRepository;
import discussion.repository.AppUserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
	private final AppUserRepository appUserRepository;
	
	private final AppUserGroupRepository appUserGroupRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser=appUserRepository.findByAppUsername(username)
				.orElseThrow(()->new AppExceptionMessage("Username not found"+username));
		
		List<AppUserGroup> list=appUserGroupRepository.findByAppUsername(username);
				
		
		return new User(appUser.getAppUsername(),appUser.getPassword(),appUser.isEnabled(),true,true,true,
				getAuthorities(list));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(List<AppUserGroup> list) {
		if(list==null) {
			return Collections.emptySet();
		}
		Set<SimpleGrantedAuthority> set=new HashSet<>();
		for(AppUserGroup x:list) {
			set.add(new SimpleGrantedAuthority(x.getAppUserGroup().toUpperCase()));
		}
		return set;
	}

}
