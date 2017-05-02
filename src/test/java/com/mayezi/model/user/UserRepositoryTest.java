package com.mayezi.model.user;

import com.mayezi.model.user.entity.User;
import com.mayezi.model.user.respository.UserRepository;
import com.mayezi.model.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.UUID;


/**
 * Created by Mayezi
 * Date: 2017-04-05
 * Time: 17:33
 * All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Test
    public void proxy() {
        System.out.println(userRepository.getClass());
    }


    @Test
    public void save() {
        User user = new User();
        user.setUsername("mayezi");
        user.setPassword(new BCryptPasswordEncoder().encode("9527"));
        user.setEmail("1090318353@qq.com");
        user.setLocked(false);
        user.setToken(UUID.randomUUID().toString());
        user.setInitTime(new Date());
        userRepository.save(user);
        System.out.println(user);
    }

    @Test
    public void query() {
        System.out.println(userService.findByUsername("mayezi"));
    }
}
