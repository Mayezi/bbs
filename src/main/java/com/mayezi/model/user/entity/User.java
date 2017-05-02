package com.mayezi.model.user.entity;

import com.mayezi.common.BaseEntity;
import com.mayezi.model.security.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mayezi
 * Date: 2017-04-05
 * Time: 16:12
 * All rights reserved.
 */
@Data
@Entity
@Table(name = "user")
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 6673591629497554799L;

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String avater;

    private String email;

    private String signature;

    private String user_url;
    private Date initTime;

    private String token;
    //是否被锁定
    private boolean locked;
    //用户与角色的关联关系
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();
}
