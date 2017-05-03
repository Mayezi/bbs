package com.mayezi.model.api.controller;


import com.mayezi.common.BaseController;
import com.mayezi.common.config.SiteConfig;
import com.mayezi.exception.ApiException;
import com.mayezi.exception.Result;
import com.mayezi.model.reply.service.ReplyService;
import com.mayezi.model.topic.service.TopicService;
import com.mayezi.model.user.entity.User;
import com.mayezi.model.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/user")
public class UserApiController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private ReplyService replyService;

    @Autowired
    private SiteConfig siteConfig;

    /**
     * 用户首页接口
     *
     * @param username
     * @return
     * @throws ApiException
     */
    @GetMapping("/{username}")
    public Result profile(@PathVariable String username) throws ApiException {
        User user = userService.findByUsername(username);
        if (user == null) throw new ApiException("用户名不存在");
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("topicPage", topicService.findByUser(1, 7, user));
        map.put("replyPage", replyService.findByUser(1, 7, user));
        return Result.success(map);
    }

    /**
     * 用户发布的所有话题
     *
     * @param username
     * @return
     */
    @GetMapping("/{username}/topics")
    public Result topics(@PathVariable String username, Integer p) throws ApiException {
        User user = userService.findByUsername(username);
        if (user == null) throw new ApiException("用户名不存在");
        Page page = topicService.findByUser(p == null ? 1 : p, siteConfig.getPageSize(), user);
        return Result.success(page);
    }

    /**
     * 用户发布的所有回复
     *
     * @param username
     * @return
     */
    @GetMapping("/{username}/replies")
    public Result replies(@PathVariable String username, Integer p) throws ApiException {
        User user = userService.findByUsername(username);
        if (user == null) throw new ApiException("用户名不存在");
        Page page = replyService.findByUser(p == null ? 1 : p, siteConfig.getPageSize(), user);
        return Result.success(page);
    }

}
