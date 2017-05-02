package com.mayezi.model.user.respository;

import com.mayezi.model.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by Mayezi
 * Date: 2017-04-05
 * Time: 16:35
 * All rights reserved.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findOne(int id);
    User findByUsername(String username);
    User findByToken(String token);
    User findByEmail(String email);
}
