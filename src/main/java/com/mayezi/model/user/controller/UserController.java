package com.mayezi.model.user.controller;

import com.mayezi.common.BaseController;
import com.mayezi.common.config.SiteConfig;
import com.mayezi.model.reply.service.ReplyService;
import com.mayezi.model.topic.service.TopicService;
import com.mayezi.model.user.entity.User;
import com.mayezi.model.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Mayezi
 * Date: 2017-04-05
 * Time: 17:11
 * All rights reserved.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private SiteConfig siteConfig;
    @Autowired
    private UserService userService;

    /**
     * 用户个人中心
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}")
    public String userInfo(@PathVariable String username, Model model) {
        User currentUser = userService.findByUsername(username);
        if (currentUser != null) {
            model.addAttribute("user", getUser());
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("topicPage", topicService.findByUser(1, 7, currentUser));
            model.addAttribute("replyPage", replyService.findByUser(1, 7, currentUser));
            model.addAttribute("pageTitle", currentUser.getUsername() + "|个人中心");
        } else {
            model.addAttribute("pageTitle", "用户未找到");
        }
        return render("/user/index");
    }
    /**
     * 用户个人主页
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/home")
    public String userHome(@PathVariable String username, Model model) {
        User currentUser = userService.findByUsername(username);
        if (currentUser != null) {
            model.addAttribute("user", getUser());
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("topicPage", topicService.findByUser(1, 7, currentUser));
            model.addAttribute("replyPage", replyService.findByUser(1, 7, currentUser));
            model.addAttribute("pageTitle", currentUser.getUsername() + "|个人主页");
        } else {
            model.addAttribute("pageTitle", "用户未找到");
        }
        return render("/user/home");
    }


    /**
     * 个人设置
     * @param model
     * @return
     */
    @GetMapping("/setting")
    public String userSet(Model model) {
        model.addAttribute("user",getUser());
        return render("/user/set");
    }

    @GetMapping("/message")
    public String userMessage() {
        return render("/user/message");
    }

    @PostMapping("/update")
    public String userUpdate(String email, String user_url, String signature, Model model, HttpServletResponse response){
        User user = getUser();
        user.setEmail(email);
        user.setSignature(signature);
        user.setUser_url(user_url);
        userService.saveUser(user);
        model.addAttribute("user",getUser());
        return redirect(response,"/user/"+user.getUsername()+"/home");
    }
}
