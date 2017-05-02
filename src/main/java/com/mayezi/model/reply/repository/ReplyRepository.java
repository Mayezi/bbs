package com.mayezi.model.reply.repository;

import com.mayezi.model.reply.entity.Reply;
import com.mayezi.model.topic.entity.Topic;
import com.mayezi.model.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mayezi
 * Date: 2017-04-12
 * Time: 11:33
 * All rights reserved.
 */
@Repository
public interface ReplyRepository extends JpaRepository<Reply,Integer>{
    List<Reply> findByTopicOrderByLikeNumAscReplyTimeAsc(Topic topic);
    void deleteByTopic(Topic topic);
    void deleteByUser(User user);
    Page<Reply> findByUser(User user, Pageable pageable);
}
