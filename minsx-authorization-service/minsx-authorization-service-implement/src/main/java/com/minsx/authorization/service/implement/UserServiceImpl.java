package com.minsx.authorization.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minsx.authorization.entity.User;
import com.minsx.authorization.repository.UserRepository;
import com.minsx.authorization.api.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	public User findUserById(Integer id) {
		return userRepository.findById(id);
	}

	public User findUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

}
