package com.mayezi.model.notification.service;

import com.mayezi.common.BaseEntity;
import com.mayezi.common.config.SiteConfig;
import com.mayezi.model.notification.entity.Notification;
import com.mayezi.model.notification.entity.NotificationEnum;
import com.mayezi.model.notification.repository.NotificationRepository;
import com.mayezi.model.reply.entity.Reply;
import com.mayezi.model.topic.entity.Topic;
import com.mayezi.model.user.entity.User;
import com.mayezi.model.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Mayezi
 * Date: 2017-05-03
 * Time: 18:14
 * All rights reserved.
 */
@Service
@Transactional
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserService userService;

    /**
     * 保存通知
     * @param notification
     */
    public void save(Notification notification){
        notificationRepository.save(notification);
    }


    public void sendNotification(User user, Topic topic, String content, Reply reply){
//       给话题作者发送通知
        if (user.getId()!=topic.getUser().getId()){
            this.sendNotification(user,topic.getUser(), NotificationEnum.REPLY.name(),topic,content);
        }
        String pattern = null;
        List<String> atUsers = BaseEntity.fetchUsers(pattern,content);
        for(String u : atUsers){
            u = u.replace("@","").trim();
            if (!u.equals(user.getUsername())){
                User _user = userService.findByUsername(u);
                if (_user!=null){
                    this.sendNotification(user, _user, NotificationEnum.AT.name(), topic, content);
                }
            }
        }
    }

    /**
     * 发送通知
     * @param user
     * @param targetUser
     * @param action
     * @param topic
     * @param content
     */
    public void sendNotification(User user, User targetUser, String action, Topic topic, String content) {
        new Thread(() -> {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setTargetUser(targetUser);
            notification.setNotificationTime(new Date());
            notification.setTopic(topic);
            notification.setAction(action);
            notification.setContent(content);
            notification.setRread(false);
            save(notification);
        }).start();
    }

    /**
     * 根据用户查询通知
     *
     * @param p
     * @param size
     * @param targetUser
     * @param read
     * @return
     */
    public Page<Notification> findByTargetUserAndRread(int p, int size, User targetUser, Boolean read) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "rread"), new Sort.Order(Sort.Direction.DESC, "notificationTime"));
        Pageable pageable = new PageRequest(p - 1, size, sort);
        if (read == null) {
            return notificationRepository.findByTargetUser(targetUser, pageable);
        }
        return notificationRepository.findByTargetUserAndRread(targetUser, read, pageable);
    }

    /**
     * 根据用户查询已读/未读的通知
     *
     * @param targetUser
     * @param read
     * @return
     */
    public long countByTargetUserAndRread(User targetUser, boolean read) {
        return notificationRepository.countByTargetUserAndRread(targetUser, read);
    }

    /**
     * 根据阅读状态查询通知
     *
     * @param targetUser
     * @param read
     * @return
     */
    public List<Notification> findByTargetUserAndRread(User targetUser, boolean read) {
        return notificationRepository.findByTargetUserAndRread(targetUser, read);
    }

    /**
     * 批量更新通知的状态
     *
     * @param targetUser
     */
    public void updateByRread(User targetUser) {
        notificationRepository.updateByRread(targetUser);
    }

    /**
     * 删除用户的通知
     * @param user
     */
    public void deleteByUser(User user) {
        notificationRepository.deleteByUser(user);
    }

    /**
     * 删除目标用户的通知
     * @param user
     */
    public void deleteByTargetUser(User user) {
        notificationRepository.deleteByTargetUser(user);
    }

    public void deleteByTopic(Topic topic) {
        notificationRepository.deleteByTopic(topic);
    }
}
