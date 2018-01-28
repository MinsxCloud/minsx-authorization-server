package com.minsx.authorization.repository.user;

import com.minsx.authorization.entity.ordinary.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 * Created by Joker on 2017/8/30.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	
	User findByUserName(String userName);

	User findByEmail(String email);

	User findByPhone(String phone);

}
