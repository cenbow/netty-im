package cn.zlmthy.framework.netty.im.server.handler;

import cn.zlmthy.framework.dto.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NettyServerDispatchHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("client connected!");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object request) throws Exception {
        log.info("accept msg");
        System.out.println(((Request)request).getReqNo());
    }
}
