package com.mayezi.model.topic;

import com.mayezi.model.topic.entity.Topic;
import com.mayezi.model.topic.repository.TopicRepository;
import com.mayezi.model.user.entity.User;
import com.mayezi.model.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by Mayezi
 * Date: 2017-04-08
 * Time: 10:39
 * All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class TopicRepositoryTest {
    @Autowired
    private TopicRepository topicRepository;

    @Test
    public void TopicSave(){
        Topic topic = new Topic();
        for (int i = 0; i < 6; i++) {
            topic.setTab("精华");
            topic.setTitle("我就是想发个帖子");
            topic.setContent("里面我就写一句话又怎样");
            topic.setViewNum(0);
            topic.setReplyNum(0);
            topic.setPostTime(new Date());
            topicRepository.save(topic);
        }
    }


}
