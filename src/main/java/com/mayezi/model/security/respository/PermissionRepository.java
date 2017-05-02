package com.mayezi.model.security.respository;

import com.mayezi.model.security.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Permission findOne(int id);

    List<Permission> findByPidGreaterThan(int pid);

    List<Permission> findByPid(int pid);

    void deleteByPid(int pid);
}
