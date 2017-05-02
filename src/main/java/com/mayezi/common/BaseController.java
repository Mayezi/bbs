package com.mayezi.common;

import com.mayezi.common.config.SiteConfig;
import com.mayezi.model.user.entity.User;
import com.mayezi.model.user.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseController {

    Logger logger = Logger.getLogger(BaseController.class);
    @Autowired
    private SiteConfig siteConfig;
    @Autowired
    private UserService userService;

    /**
     * 带参重定向
     *
     * @param path
     * @return
     */
    protected String redirect(String path) {
        return "redirect:" + path;
    }

    /**
     * 不带参重定向
     *
     * @param response
     * @param path
     * @return
     */
    protected String redirect(HttpServletResponse response, String path) {
        try {
            response.sendRedirect(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 渲染页面
     *
     * @param path 前面必须要加上 /
     * @return
     */
    protected String render(String path) {
        return siteConfig.getTheme() + path;
    }


    protected String renderText(HttpServletResponse response, String msg) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Security用户
     *
     * @return
     */
    protected UserDetails getSecurityUser() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean b = o instanceof UserDetails;
        if (b) {

            logger.info("Acquire Security User: "+o.toString());
            return (UserDetails) o;
        }
        return null;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    protected User getUser() {
        UserDetails userDetails = getSecurityUser();
        if (userDetails != null) {
            logger.info("getUser: "+userService.findByUsername(userDetails.getUsername()));
            return userService.findByUsername(userDetails.getUsername());
        }
        return null;
    }
    /**
     * 获取用户信息
     * @param token
     * @return
     */
    protected User getUser(String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        } else {
            return userService.findByToken(token);
        }
    }
}
