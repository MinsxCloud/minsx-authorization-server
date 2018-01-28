package com.minsx.authorization.service.implement;

import com.minsx.authorization.api.UserService;
import com.minsx.authorization.entity.developer.DevUser;
import com.minsx.authorization.entity.ordinary.Auth;
import com.minsx.authorization.entity.ordinary.Group;
import com.minsx.authorization.entity.ordinary.Role;
import com.minsx.authorization.entity.ordinary.User;
import com.minsx.authorization.repository.developer.DevUserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * UserDetailsServiceImpl
 * created by Joker on 2017年11月1日
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    DevUserRepository devUserRepository;


    private final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails;
        User user = userService.getUserByUsernameOrEmailOrPhone(username);
        if (user == null) {
            DevUser devUser = devUserRepository.findByAccessKey(username);
            if (devUser == null) {
                logger.info(String.format("the user [%s] does not exist", username));
                throw new UsernameNotFoundException("the user does not exist");
            } else {
                Set<GrantedAuthority> authorities = new HashSet<>();
                devUser.setAuthorities(authorities);
                userDetails = devUser;
            }
        } else {
            Set<GrantedAuthority> authorities = new HashSet<>();
            List<Group> userGroups = user.getGroups();
            userGroups.forEach(userGroup -> {
                List<Role> roles = userGroup.getRoles();
                roles.forEach(role -> {
                    List<Auth> auths = role.getAuths();
                    auths.forEach(auth -> {
                        authorities.add(new SimpleGrantedAuthority(auth.getType() + ":" + auth.getValue()));
                    });
                });
            });
            user.setAuthorities(authorities);
            userDetails = user;
        }
        return userDetails;
    }
}
