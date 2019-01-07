package cn.zlmthy.framework.netty.im.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.log4j.Log4j2;

/**
 * @author zengliming
 */
@Log4j2
@ChannelHandler.Sharable
public class NettyClientDispatchHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        log.info("client handle");
        log.info(o.toString());
    }

}
