package com.mayezi.model.security.entity;


import com.mayezi.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "permission")
@Data
@EqualsAndHashCode(callSuper = false)
public class Permission extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 6115536280861853643L;
    @Id
    @GeneratedValue
    private int id;

    //权限名称
    private String name;

    //权限描述
    private String description;

    //授权链接
    private String url;

    //父节点id
    private int pid;

    /**
     * 角色与权限的关联关系
     * mappedBy: 就是 Role.class 里的 Set<Permission> 的对象名
     */
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
}
