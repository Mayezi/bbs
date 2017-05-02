package com.mayezi.model.security.entity;


import com.mayezi.common.BaseEntity;
import com.mayezi.model.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1731751869655444193L;

    @Id
    @GeneratedValue
    private int id;

    //权限标识
    private String name;

    //权限描述
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    //角色与权限的关联关系
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "role_permission",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")}
    )
    private Set<Permission> permissions = new HashSet<>();

}
