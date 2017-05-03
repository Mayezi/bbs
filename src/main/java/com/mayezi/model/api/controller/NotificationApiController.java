package com.mayezi.model.api.controller;

import com.mayezi.common.BaseController;
import com.mayezi.exception.ApiException;
import com.mayezi.exception.ErrorCode;
import com.mayezi.exception.Result;
import com.mayezi.model.notification.service.NotificationService;
import com.mayezi.model.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/notification")
public class NotificationApiController extends BaseController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 查询当前用户未读的消息数量
     *
     * @return
     */
    @GetMapping("/notRead")
    public Result notRead(String token) throws ApiException {
        User user = getUser(token);
        if (user == null) throw new ApiException(ErrorCode.notLogin, "请先登录");
        return Result.success(notificationService.countByTargetUserAndRread(user, false));
    }

}
