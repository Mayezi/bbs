package com.mayezi.model.notification.repository;

import com.mayezi.model.notification.entity.Notification;
import com.mayezi.model.topic.entity.Topic;
import com.mayezi.model.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mayezi
 * Date: 2017-05-03
 * Time: 18:07
 * All rights reserved.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Page<Notification> findByTargetUser(User targetUser, Pageable pageable);

    Page<Notification> findByTargetUserAndRread(User targetUser, boolean read, Pageable pageable);

    List<Notification> findByTargetUserAndRread(User targetUser, boolean read);

    long countByTargetUserAndRread(User targetUser, boolean read);

    @Modifying
    @Query("update Notification n set n.rread=true where n.targetUser=?1")
    void updateByRread(User targetUser);

    void deleteByTargetUser(User User);

    void deleteByUser(User user);

    void deleteByTopic(Topic topic);
}
