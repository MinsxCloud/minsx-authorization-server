package com.minsx.authorization.repository.base;

import com.minsx.authorization.entity.base.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RoleRepository
 * Created by Joker on 2017/8/30.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{

}
