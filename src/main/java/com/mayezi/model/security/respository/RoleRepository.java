package com.mayezi.model.security.respository;

import com.mayezi.model.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findOne(int id);

    List<Role> findAll();

    void delete(int id);
}
