package com.mayezi.model.reply.entity;

import com.mayezi.model.topic.entity.Topic;
import com.mayezi.model.user.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mayezi
 * Date: 2017-04-12
 * Time: 11:25
 * All rights reserved.
 */
@Table(name = "reply")
@Entity
@Data
public class Reply implements Serializable {

    private static final long serialVersionUID = -7131539332531730470L;

    @Id
    @GeneratedValue
    private int id;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    private Date replyTime;

    @Column(nullable = false)
    private int likeNum;

    @ManyToOne
    @JoinColumn(nullable = false, name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(columnDefinition = "text")
    private String upIds;
}
