package com.minsx.authorization.repository;

import com.minsx.authorization.entity.base.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RoleRepository
 * Created by Joker on 2017/8/30.
 */
public interface RoleRepository extends JpaRepository<Role,Integer>{

}
