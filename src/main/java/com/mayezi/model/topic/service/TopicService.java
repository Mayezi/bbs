package com.mayezi.model.topic.service;

import com.mayezi.model.reply.service.ReplyService;
import com.mayezi.model.topic.entity.Topic;
import com.mayezi.model.topic.repository.TopicRepository;
import com.mayezi.model.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Created by Mayezi
 * Date: 2017-04-08
 * Time: 10:14
 * All rights reserved.
 */
@Service
@Transactional
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private ReplyService replyService;


    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    public Topic findById(int id) {
        return topicRepository.findOne(id);
    }

    /**
     * 删除话题
     *
     * @param id
     */
    public void deleteById(int id) {
        Topic topic = findById(id);
        //删除通知里提到的话题
//        notificationService.deleteByTopic(topic);
        //删除话题下面的回复
        replyService.deleteByTopic(topic);
        //删除话题
        topicRepository.delete(topic);
    }

    /**
     * 删除用户发的所有话题
     *
     * @param user
     */
    public void deleteByUser(User user) {
        topicRepository.deleteByUser(user);
    }

    /**
     * 分页查询话题列表
     *
     * @param p
     * @param size
     * @return
     */
    public Page<Topic> queryTopicByPage(int p, int size, String tab) {
        Sort sort = new Sort(
                new Sort.Order(Sort.Direction.DESC, "top"),
                new Sort.Order(Sort.Direction.DESC, "postTime"));
        Pageable pageable = new PageRequest(p - 1, size, sort);
        if (tab.equals("全部")) {
            return topicRepository.findAll(pageable);
        } else if (tab.equals("精华")) {
            return topicRepository.findByExcellent(true, pageable);
        } else if (tab.equals("等待回复")) {
            return topicRepository.findByReplyNum(0, pageable);
        } else {
            return topicRepository.findByTab(tab, pageable);
        }
    }


    /**
     * 增加回复数
     *
     * @param topicId
     */
    public void addOneReplyNum(int topicId) {
        Topic topic = findById(topicId);
        if (topic != null) {
            topic.setReplyNum(topic.getReplyNum() + 1);
            save(topic);
        }
    }

    /**
     * 减少回复数
     *
     * @param topicId
     */
    public void reduceOneReplyNum(int topicId) {
        Topic topic = findById(topicId);
        if (topic != null) {
            topic.setReplyNum(topic.getReplyNum() - 1);
            save(topic);
        }
    }

    /**
     * 查询用户的话题
     *
     * @param p
     * @param size
     * @param user
     * @return
     */
    public Page<Topic> findByUser(int p, int size, User user) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "postTime"));
        Pageable pageable = new PageRequest(p - 1, size, sort);
        return topicRepository.findByUser(user, pageable);
    }
}
