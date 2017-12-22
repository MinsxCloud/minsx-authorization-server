package com.minsx.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.minsx.authorization.entity.User;

/**
 * UserRepository
 * Created by Joker on 2017/8/30.
 */
@Component
public interface UserRepository extends JpaRepository<User,Integer>{
	
	User findById(Integer id);
	
	User findByUserName(String userName);
	

}
