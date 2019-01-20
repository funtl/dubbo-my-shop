package com.funtl.myshop.web.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.funtl.myshop.service.content.api.ContentConsumerService;
import com.funtl.myshop.service.user.api.UserConsumerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "router")
public class RouterController {

    @Value(value = "${services.ports.user}")
    private String userPort;

    @Value(value = "${services.ports.content}")
    private String contentPort;

    @Reference(version = "${services.versions.user.v1}")
    private UserConsumerService userConsumerService;

    @Reference(version = "${services.versions.content.v1}")
    private ContentConsumerService contentConsumerService;

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String user(String path) {
        // 远程调用
        userConsumerService.info();
        return getRequest(userPort, path);
    }

    @RequestMapping(value = "content", method = RequestMethod.GET)
    public String content(String path) {
        // 远程调用
        contentConsumerService.info();
        return getRequest(contentPort, path);
    }

    /**
     * 获取请求地址
     *
     * @param serverPort 服务器端口
     * @param path       访问路径
     * @return
     */
    private String getRequest(String serverPort, String path) {
        // 本端是否为消费端，这里会返回true
        boolean isConsumerSide = RpcContext.getContext().isConsumerSide();
        if (isConsumerSide) {
            // 获取最后一次调用的提供方 IP 地址
            String serverIP = RpcContext.getContext().getRemoteHost();
            return String.format("redirect:http://%s:%s%s", serverIP, serverPort, path);
        }

        return null;
    }
}
