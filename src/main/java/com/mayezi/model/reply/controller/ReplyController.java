package com.mayezi.model.reply.controller;

import com.mayezi.common.BaseController;
import com.mayezi.model.reply.entity.Reply;
import com.mayezi.model.reply.service.ReplyService;
import com.mayezi.model.topic.entity.Topic;
import com.mayezi.model.topic.service.TopicService;
import com.mayezi.model.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Mayezi
 * Date: 2017-04-12
 * Time: 14:46
 * All rights reserved.
 */
@Controller
@RequestMapping("/reply")
public class ReplyController extends BaseController{

    @Autowired
    private TopicService topicService;
    @Autowired
    private ReplyService replyService;

    /**
     * 保存回复
     *
     * @param topicId
     * @param content
     * @return
     */
    @PostMapping("/save")
    public String save(Integer topicId, String content, HttpServletResponse response) {
        if(getUser().isLocked()) return renderText(response, "你的帐户已经被禁用，不能进行此项操作");
        if (topicId != null) {
            Topic topic = topicService.findById(topicId);
            if (topic != null) {
                User user = getUser();
                Reply reply = new Reply();
                reply.setUser(user);
                reply.setTopic(topic);
                reply.setReplyTime(new Date());
                reply.setLikeNum(0);
                reply.setContent(content);
                replyService.save(reply);

                //回复+1
                topic.setReplyNum(topic.getReplyNum() + 1);
                topicService.save(topic);

                //给话题作者发送通知
                /*if (user.getId() != topic.getUser().getId()) {
                    notificationService.sendNotification(getUser(), topic.getUser(), NotificationEnum.REPLY.name(), topic, content, reply.getEditor());
                }*/
                //给At用户发送通知
                /*String pattern = null;
                if(siteConfig.getEditor().equals("wangeditor")) pattern = "\">[^\\s]+</a>?";
                List<String> atUsers = BaseEntity.fetchUsers(pattern, content);
                for (String u : atUsers) {
                    if(siteConfig.getEditor().equals("markdown")) {
                        u = u.replace("@", "").trim();
                    } else if(siteConfig.getEditor().equals("wangeditor")) {
                        u = u.replace("\">@", "").replace("</a>", "").trim();
                    }
                    if (!u.equals(user.getUsername())) {
                        User _user = userService.findByUsername(u);
                        if (_user != null) {
                            notificationService.sendNotification(user, _user, NotificationEnum.AT.name(), topic, content, reply.getEditor());
                        }
                    }
                }*/
                return redirect(response, "/topic/" + topicId);
            }
        }
        return redirect(response, "/");
    }

}
