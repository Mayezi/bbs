package com.mayezi.model.reply.service;

import com.mayezi.model.reply.entity.Reply;
import com.mayezi.model.reply.repository.ReplyRepository;
import com.mayezi.model.topic.entity.Topic;
import com.mayezi.model.topic.service.TopicService;
import com.mayezi.model.user.entity.User;
import com.mayezi.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mayezi
 * Date: 2017-04-12
 * Time: 14:34
 * All rights reserved.
 */
@Service
@Transactional
public class ReplyService {
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private TopicService topicService;

    public Reply findById(int id) {
        return replyRepository.findOne(id);
    }

    public void save(Reply reply) {
        replyRepository.save(reply);
    }

    public Map delete(int id, User user) {
        Map map = new HashMap();
        Reply reply = findById(id);
        if (reply != null && user.getId() == reply.getUser().getId()) {
            map.put("topicId", reply.getTopic().getId());
            topicService.reduceOneReplyNum(reply.getTopic().getId());
            replyRepository.delete(id);
        }
        return map;
    }

    /**
     * 删除用户发布的所有回复
     *
     * @param user
     */
    public void deleteByUser(User user) {
        replyRepository.deleteByUser(user);
    }

    /**
     * 根据话题删除回复
     *
     * @param topic
     */
    public void deleteByTopic(Topic topic) {
        replyRepository.deleteByTopic(topic);
    }

    /**
     * 赞
     *
     * @param userId
     * @param replyId
     */
    public Reply like(int userId, int replyId) {
        Reply reply = findById(replyId);
        if (reply != null) {
            String upIds = reply.getUpIds();
            if(upIds == null) upIds = Constants.COMMA;
            if(!upIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
                reply.setLikeNum(reply.getLikeNum() + 1);
                reply.setUpIds(upIds + userId + Constants.COMMA);
                save(reply);
            }
        }
        return reply;
    }

    /**
     * 取消赞
     *
     * @param userId
     * @param replyId
     */
    public Reply cancelUp(int userId, int replyId) {
        Reply reply = findById(replyId);
        if (reply != null) {
            String upIds = reply.getUpIds();
            if(upIds == null) upIds = Constants.COMMA;
            if(upIds.contains(Constants.COMMA + userId + Constants.COMMA)) {
                reply.setLikeNum(reply.getLikeNum() - 1);
                upIds = upIds.replace(Constants.COMMA + userId + Constants.COMMA, Constants.COMMA);
                reply.setUpIds(upIds);
                int count = reply.getLikeNum();
                reply.setLikeNum(count > 0 ? count : 0);
                save(reply);
            }
        }
        return reply;
    }


    /**
     * 根据话题查询回复列表
     *
     * @param topic
     * @return
     */
    public List<Reply> findByTopic(Topic topic) {
        return replyRepository.findByTopicOrderByLikeNumAscReplyTimeAsc(topic);
    }

    /**
     * 分页查询回复列表
     *
     * @param p
     * @param size
     * @return
     */
    public Page<Reply> page(int p, int size) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "replyTime"));
        Pageable pageable = new PageRequest(p - 1, size, sort);
        return replyRepository.findAll(pageable);
    }

    /**
     * 查询用户的回复列表
     *
     * @param p
     * @param size
     * @param user
     * @return
     */
    public Page<Reply> findByUser(int p, int size, User user) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "replyTime"));
        Pageable pageable = new PageRequest(p - 1, size, sort);
        return replyRepository.findByUser(user, pageable);
    }
}
