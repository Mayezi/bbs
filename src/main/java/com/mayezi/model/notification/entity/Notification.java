package com.mayezi.model.notification.entity;

import com.mayezi.common.BaseEntity;
import com.mayezi.model.topic.entity.Topic;
import com.mayezi.model.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mayezi
 * Date: 2017-05-03
 * Time: 17:54
 * All rights reserved.
 */
@Entity
@Table(name = "notification")
@Data
@EqualsAndHashCode(callSuper = false)
public class Notification extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3402121220491183312L;
    @Id
    @GeneratedValue
    private int id;
    //是否已读
    private boolean rread;
    //    通知发起用户
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    //通知接受用户
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "target_user_id")
    private User targetUser;
    //通知时间
    private Date notificationTime;
    //    通知动作
    private String action;
    //    关联帖子
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "topic_id")
    private Topic topic;
    //    通知消息内容
    @Column(columnDefinition = "text")
    private String content;
}
