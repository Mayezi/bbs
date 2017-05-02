package com.mayezi.model.user;

import com.mayezi.model.user.entity.User;
import com.mayezi.model.user.respository.UserRepository;
import com.mayezi.model.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;


/**
 * Created by Mayezi
 * Date: 2017-04-05
 * Time: 17:33
 * All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class UserServiceTest {

    @Autowired
    UserService userService;


    @Test
    public void query(){
        for (int i = 0; i < 5; i++) {
        System.out.println(userService.findById(i+6));
        }
    }
}
