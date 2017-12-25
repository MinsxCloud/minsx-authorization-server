package com.minsx.authorization.api;

import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> register(String username,String password);

    ResponseEntity<?> getEmailCode(String username);
}
