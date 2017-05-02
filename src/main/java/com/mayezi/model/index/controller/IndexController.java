package com.mayezi.model.index.controller;

import com.mayezi.common.BaseController;
import com.mayezi.common.config.SiteConfig;
import com.mayezi.model.topic.entity.Topic;
import com.mayezi.model.topic.service.TopicService;
import com.mayezi.model.user.entity.User;
import com.mayezi.model.user.service.UserService;
import com.mayezi.util.PageWrapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mayezi
 * Date:2017
 * Time:14:25
 * All rights reserved.
 */
@Controller
/* @RestController
Spring4 之后新加的注解,原来返回json需要@ResponseBody配合@Controller,现在一个顶俩*/

public class IndexController extends BaseController {

    Logger logger = Logger.getLogger(IndexController.class);
    @Autowired
    private TopicService topicService;
    @Autowired
    private UserService userService;
    @Autowired
    private SiteConfig siteConfig;


    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/")
    public String index(String tab, Integer p, Model model) {
        String sectionName = tab;
        if (StringUtils.isEmpty(tab)) tab = "全部";
        if (tab.equals("全部") || tab.equals("等待回复") || tab.equals("精品帖")) {
            sectionName = "版块";
        }
        PageWrapper<Topic> page = new PageWrapper<>(topicService.queryTopicByPage(p == null ? 1 : p, siteConfig.getPageSize(), tab), "/");
        model.addAttribute("page", page);
        model.addAttribute("tab", tab);
        model.addAttribute("sectionName", sectionName);
        model.addAttribute("user", getUser());
        return render("/index");
    }


    /**
     * 进入登录页
     *
     * @return
     */
    @GetMapping(value = "/login")
    public String toLogin(String s, Model model, HttpServletResponse response) {
        if (getUser() != null) {
            logger.info("user: " +getUser().getUsername()+" login success.");
            return redirect(response, "/");
        }
        model.addAttribute("s", s);
        logger.info("user login failed");
        return render("/login");
    }

    /**
     * 进入注册页面
     *
     * @return
     */
    @GetMapping("/register")
    public String toRegister(HttpServletResponse response) {
        if (getUser() != null) {
            return redirect(response, "/");
        }
        return render("/register");
    }

    /**
     * 注册验证
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public String register(String username, String password, HttpServletResponse response, Model model) {
        User user = userService.findByUsername(username);
        if (user != null) {
            model.addAttribute("errors", "用户名已经被注册");
        } else if (StringUtils.isEmpty(username)) {
            model.addAttribute("errors", "用户名不能为空");
        } else if (StringUtils.isEmpty(password)) {
            model.addAttribute("errors", "密码不能为空");
        } else {
            user = new User();
            user.setUsername(username);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setInitTime(new Date());
            user.setLocked(false);
            user.setToken(UUID.randomUUID().toString());
            userService.saveUser(user);
            return redirect(response, "/login?s=reg");
        }
        return render("/register");
    }

 }
