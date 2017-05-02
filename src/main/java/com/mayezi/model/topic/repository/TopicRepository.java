package com.mayezi.model.topic.repository;

import com.mayezi.model.topic.entity.Topic;
import com.mayezi.model.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mayezi
 * Date: 2017-04-08
 * Time: 10:12
 * All rights reserved.
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic,Integer>{
    Topic findOne(int id);

    void delete(int id);

    Page<Topic> findByTab(String tab, Pageable pageable);

    Page<Topic> findByUser(User user, Pageable pageable);

    void deleteByUser(User user);

    Page<Topic> findByExcellent(boolean b, Pageable pageable);

    Page<Topic> findByReplyNum(int i, Pageable pageable);

    Page<Topic> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
