package com.jornadev.hroauth.services;

import com.jornadev.hroauth.entities.User;
import com.jornadev.hroauth.feignclients.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserFeignClient userFeignClient;

    public User findUserByEmail(String email) {
        User user = userFeignClient.findByEmail(email).getBody();
        if(user == null) {
            logger.error("User not found");
            throw new IllegalArgumentException("User not found");
        }
        logger.info("User found" + email);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userFeignClient.findByEmail(username).getBody();
        if(user == null) {
            logger.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        logger.info("User found" + username);
        return user;
    }
}
