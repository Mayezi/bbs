package com.mayezi.model.security.service;

import com.mayezi.model.security.entity.Role;
import com.mayezi.model.security.respository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 查询所有的角色
     *
     * @return
     */
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /**
     * 删除角色
     *
     * @param id
     */
    public void deleteById(Integer id) {
        roleRepository.delete(id);
    }

    /**
     * 根据id查找角色
     *
     * @param id
     * @return
     */
    public Role findById(int id) {
        return roleRepository.findOne(id);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

}
