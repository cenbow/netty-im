package cn.zlmthy.framework.netty.im.client.controller;

import cn.zlmthy.framework.netty.im.client.NettyClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/")
public class SendController {

    @Resource
    private NettyClient nettyClient;

    @RequestMapping(value = "/send")
    public Object send(String msg){
        nettyClient.send(msg);

        return "";
    }
}
