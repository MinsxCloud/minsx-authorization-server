package com.minsx.authorization.repository.developer;

import com.minsx.authorization.entity.system.DevUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevUserRepository extends JpaRepository<DevUser,Integer>{

    DevUser findByAccessKey(String accessKey);


}
