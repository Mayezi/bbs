package com.mayezi.model.topic.entity;

import com.mayezi.common.BaseEntity;
import com.mayezi.model.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mayezi
 * Date: 2017-04-08
 * Time: 10:01
 * All rights reserved.
 */
@Entity
@Table(name = "topic")
@Data
@EqualsAndHashCode(callSuper = false)
public class Topic extends BaseEntity implements Serializable{

    private static final long serialVersionUID = -328093155620499324L;

    @Id
    @GeneratedValue
    private int id;
//板块
    @Column(nullable = false)
    private String tab;

    @Column(unique = true,nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    private Date postTime;

    private Date modifyTime;
//是否置顶
    private boolean top;
//是否精华
    private boolean excellent;
//浏览量
    @Column(nullable = false)
    private int viewNum;

    @Column(nullable = false)
    private int replyNum;
//是否被锁定
    private boolean locked;
//与用户的关系
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
