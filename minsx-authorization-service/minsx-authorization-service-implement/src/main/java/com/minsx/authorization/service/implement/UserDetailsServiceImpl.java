package com.minsx.authorization.service.implement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.minsx.authorization.entity.base.auth.Auth;
import com.minsx.authorization.entity.base.auth.Group;
import com.minsx.authorization.entity.base.auth.Role;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.minsx.authorization.entity.system.User;
import com.minsx.authorization.repository.UserRepository;

/**
 * UserDetailsServiceImpl
 * created by Joker on 2017年11月1日
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	private final Log logger = LogFactory.getLog(this.getClass());

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		if (user == null) {
            logger.info(String.format("the user [%s] does not exist", username));
            throw new UsernameNotFoundException("the user does not exist");
        }
		Set<GrantedAuthority> authorities = new HashSet<>();
		List<Group> userGroups = user.getGroups();
		userGroups.forEach(userGroup -> {
			List<Role> roles = userGroup.getRoles();
			roles.forEach(role -> {
				List<Auth> auths = role.getAuths();
				auths.forEach(auth -> {
					authorities.add(new SimpleGrantedAuthority(auth.getType()+":"+auth.getValue()));
				});
			});
		});
		user.setAuthorities(authorities);
		return user;
	}
}
