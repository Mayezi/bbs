package com.mayezi.model.user.service;

import com.mayezi.model.user.entity.User;
import com.mayezi.model.user.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Mayezi
 * Date: 2017-04-05
 * Time: 16:51
 * All rights reserved.
 */
@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    /**
     * 根据ID查找用户是否存在
     * @param id
     * @return
     */
    public User findById(Integer id){
        return userRepository.findOne(id);
    }
    /**
     * 根据用户名查找用户是否存在
     * @param username
     * @return
     */
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User findByUserEmail(String email){
        return userRepository.findByEmail(email);
    }
    /**
     * 保存用户
     * @param user
     */
    public void saveUser(User user){
        userRepository.save(user);
    }

    /**
     * 更新用户信息
     * @param user
     */
    public void updateUser(User user){
        userRepository.save(user);
    }

    /**
     * 分页查询用户列表
     * @param p
     * @param size
     * @return
     */
    public Page<User> pageUser(int p,int size){
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"initTime"));
        Pageable pageable = new PageRequest(p-1,size,sort);
        return userRepository.findAll(pageable);
    }

    /**
     * 禁用账户
     * @param id
     */
    public void blockUser(Integer id){
        User user = findById(id);
        user.setLocked(true);
        updateUser(user);
    }

    /**
     * 解禁账户
     * @param id
     */
    public void unblockUser(Integer id){
        User user = findById(id);
        user.setLocked(false);
        updateUser(user);
    }

    /**
     * 根据令牌查找账户
     * @param token
     * @return
     */
    public User findByToken(String token){
        return userRepository.findByToken(token);
    }
}
