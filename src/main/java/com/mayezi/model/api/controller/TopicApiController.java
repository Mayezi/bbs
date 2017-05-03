package com.mayezi.model.api.controller;


import com.mayezi.common.BaseController;
import com.mayezi.common.config.SiteConfig;
import com.mayezi.exception.ApiException;
import com.mayezi.exception.Result;
import com.mayezi.model.reply.entity.Reply;
import com.mayezi.model.reply.service.ReplyService;
import com.mayezi.model.topic.entity.Topic;
import com.mayezi.model.topic.service.TopicService;
import com.mayezi.model.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/topic")
public class TopicApiController extends BaseController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private ReplyService replyService;

    @Autowired
    private SiteConfig siteConfig;

    /**
     * 话题详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result detail(String token, @PathVariable Integer id) throws ApiException {
        Topic topic = topicService.findById(id);
        if (topic == null) throw new ApiException("话题不存在");
        Map<String, Object> map = new HashMap<>();
        //浏览量+1
        topic.setViewNum(topic.getViewNum() + 1);
        topicService.save(topic);//更新话题数据
        List<Reply> replies = replyService.findByTopic(topic);
        map.put("topic", topic);
        map.put("replies", replies);
        map.put("author", topic.getUser());
        return Result.success(map);
    }

    /**
     * 保存话题
     *
     * @param title
     * @param content
     * @param tab
     * @param token
     * @return
     * @throws ApiException
     */
    @PostMapping("/save")
    public Result save(String title, String content, String tab, String token, String editor) throws ApiException {
        User user = getUser(token);
        if (user == null) throw new ApiException("用户不存在");
        if (user.isLocked()) throw new ApiException("你的帐户已经被禁用了，不能进行此项操作");

        if (StringUtils.isEmpty(title)) throw new ApiException("标题不能为空");
        if (title.length() > 120) throw new ApiException("标题不能超过120个字");
        if (!siteConfig.getSections().contains(tab)) throw new ApiException("版块不存在");
        if (StringUtils.isEmpty(editor)) editor = "markdown";
        if (!editor.equals("markdown") || !editor.equals("wangeditor")) editor = "markdown";

        Topic topic = new Topic();
        topic.setTab(tab);
        topic.setTitle(title);
        topic.setContent(content);
        topic.setPostTime(new Date());
        topic.setViewNum(0);
        topic.setUser(user);
        topic.setExcellent(false);
        topic.setTop(false);
        topic.setLocked(false);
        topicService.save(topic);

        return Result.success(topic);
    }
}
