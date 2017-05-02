package com.mayezi.model.notification.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mayezi.model.topic.entity.Topic;
import com.mayezi.model.user.entity.User;
import com.mayezi.util.Constants;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mayezi
 * Date: 2017-04-27
 * Time: 16:22
 * All rights reserved.
 */
@Entity
@Table(name = "notification")
@Data
public class Notification implements Serializable{
    private static final long serialVersionUID = -7439621290480991956L;
    @Id
    @GeneratedValue
    private int id;
    //通知是否已读
    private boolean read;

    //发起通知用户
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    //要通知用户
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "target_user_id")
    private User targetUser;

    @Column(name = "in_time")
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private Date inTime;

    //通知动作
    private String action;

    //关联的话题
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "topic_id")
    private Topic topic;

    //通知内容（冗余字段）
    @Column(columnDefinition = "text")
    private String content;
}
