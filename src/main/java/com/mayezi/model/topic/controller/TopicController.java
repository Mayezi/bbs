package com.mayezi.model.topic.controller;

import com.mayezi.common.BaseController;
import com.mayezi.common.config.SiteConfig;
import com.mayezi.model.reply.entity.Reply;
import com.mayezi.model.reply.service.ReplyService;
import com.mayezi.model.topic.entity.Topic;
import com.mayezi.model.topic.service.TopicService;
import com.mayezi.model.user.entity.User;
import com.mayezi.util.PageWrapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by Mayezi
 * Date: 2017-04-07
 * Time: 13:45
 * All rights reserved.
 */
@Controller
@RequestMapping("/topic")
public class TopicController extends BaseController {

    Logger logger = Logger.getLogger(TopicController.class);

    @Autowired
    private TopicService topicService;

    @Autowired
    private SiteConfig siteConfig;

    @Autowired
    private ReplyService replyService;


    /**
     * 创建帖子
     *
     * @param response
     * @return
     */
    @GetMapping("/add")
    public String topicAdd(HttpServletResponse response,Model model) {
        if (getUser().isLocked()) {
            return renderText(response, "您的账户被禁，不能进行此操作");
        }
        model.addAttribute("user",getUser());
        return render("/topic/add");
    }

    @GetMapping("/{id}/edit")
    public String topicEdit(@PathVariable Integer id, HttpServletResponse response, Model model){
        Topic topic = topicService.findById(id);
        if (topic!=null){
            model.addAttribute("topic",topic);
            return  render("/topic/edit");
        }else{
            return renderText(response,"帖子不存在");
        }
    }

    /**
     * 更新话题
     *
     * @param title
     * @param content
     * @return
     */
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Integer id, String tab, String title, String content, HttpServletResponse response) {
        Topic topic = topicService.findById(id);
        User user = getUser();
        if (topic.getUser().getId() == user.getId()) {
            if(!siteConfig.getSections().contains(tab)) throw new IllegalArgumentException("版块不存在");
            topic.setTab(tab);
            topic.setTitle(title);
            topic.setContent(content);
            topicService.save(topic);
            return redirect(response, "/topic/" + topic.getId());
        } else {
            return renderText(response, "非法操作");
        }
    }

    @GetMapping("/detail")
    public String topicDetail() {
        return render("/topic/detail");
    }

    /**
     * 保存话题
     *
     * @param tab      板块名称
     * @param title
     * @param content
     * @param model
     * @param response
     * @return
     */
    @PostMapping("/save")
    public String topicSave(String tab, String title, String content, Model model, HttpServletResponse response) {
        if (getUser().isLocked()) {
            return renderText(response, "您的账户被禁，不能进行此操作");
        }
        String errors;
        if (StringUtils.isEmpty(title)) {
            errors = "标题不能为空";
        } else if (!siteConfig.getSections().contains(tab)) {
            errors = "板块不存在";
        } else {
            User user = getUser();
            Topic topic = new Topic();
            topic.setTab(tab);
            topic.setTitle(title);
            topic.setPostTime(new Date());
            topic.setContent(content);
            topic.setViewNum(0);
            topic.setReplyNum(0);
            topic.setUser(user);
            topic.setExcellent(false);
            topic.setTop(false);
            topic.setLocked(false);
            topicService.save(topic);
            return redirect(response, "/topic/" + topic.getId());
        }
        logger.info("post data into topic in error");
        model.addAttribute("errors", errors);
        return render("/topic/add");
    }

    /**
     * 帖子详情
     *
     * @param id
     * @param response
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String topicDetail(@PathVariable Integer id, HttpServletResponse response, Model model) {
        if (id != null) {
            Topic topic = topicService.findById(id);
            topic.setViewNum(topic.getViewNum() + 1);
            topicService.save(topic);
            List<Reply> replies = replyService.findByTopic(topic);
            model.addAttribute("topic", topic);
            model.addAttribute("replies", replies);
            model.addAttribute("user", getUser());
            model.addAttribute("author", topic.getUser());
            model.addAttribute("otherTopics", topicService.findByUser(1, 7, topic.getUser()));
            return render("/topic/detail");
        } else {
            return renderText(response, "话题不存在");
        }
    }

    /**
     * 全部帖子
     *
     * @param tab
     * @param p
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String topicIndex(String tab, Integer p, Model model) {
        String sectionName = tab;
        if (StringUtils.isEmpty(tab)) tab = "全部";
        if (tab.equals("全部") || tab.equals("等待回复") || tab.equals("精品帖") || tab.equals("我的帖子")) {
            sectionName = "版块";
        }
        PageWrapper<Topic> page = new PageWrapper<>(topicService.queryTopicByPage(p == null ? 1 : p, siteConfig.getPageSize(), tab), "/");
        model.addAttribute("page", page);
        model.addAttribute("tab", tab);
        model.addAttribute("sectionName", sectionName);
        model.addAttribute("user", getUser());
        return render("/topic/index");
    }
}

