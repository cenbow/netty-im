package cn.zlmthy.framework.netty.im.server;


import cn.zlmthy.framework.netty.im.server.init.KryoNettyServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@PropertySource(value = "classpath:server.properties")
public class NettyServer{

    @Value("${netty.server.boss.group.threads}")
    private int bossGroupThreads;

    @Value("${netty.server.worker.group.threads}")
    private int workerGroupThreads;

    @Value("${netty.server.backlog.size}")
    private int backlogSize;

    @Value("${netty.server.port}")
    private int port;

    private Channel channel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @PostConstruct
    public void start() {
        bossGroup = new NioEventLoopGroup(bossGroupThreads);
        workerGroup = new NioEventLoopGroup(workerGroupThreads);
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
//                .option(ChannelOption.SO_BACKLOG, backlogSize)
//                .childOption(ChannelOption.SO_KEEPALIVE, true)
//                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new KryoNettyServerChannelInitializer());
        try {
            channel = serverBootstrap.bind(port).sync().channel();
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @PreDestroy
    public void stop() {
        if (null == channel) {
            return;
//            throw new ServerStopException();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
        bossGroup = null;
        workerGroup = null;
        channel = null;
    }
}
