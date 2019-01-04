package cn.zlmthy.framework.netty.im.server.handler;

import cn.zlmthy.framework.dto.Request;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyServerDispatchHandler extends SimpleChannelInboundHandler<Request> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request o) throws Exception {
        log.info(o.toString());
        channelHandlerContext.writeAndFlush("hello world");
    }
}
