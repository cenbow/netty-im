package cn.zlmthy.framework.netty.im.client;


import cn.zlmthy.framework.dto.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Log4j2
@Component
@PropertySource(value = "classpath:client.properties")
public class NettyClient {

    @Value("${netty.client.boss.group.threads}")
    private int bossGroupThreads;

    @Value("${netty.client.worker.group.threads}")
    private int workerGroupThreads;

    @Value("${netty.client.backlog.size}")
    private int backlogSize;

    @Value("${netty.client.port}")
    private int port;

    @Value("${netty.server.ip}")
    private String serverIp;

    @Value("${netty.server.port}")
    private int servePort;

    public void send(String msg) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioSocketChannel.class)
                .handler(new KryoNettyClientChannelInitializer());
        try {

            ChannelFuture future = bootstrap.connect(serverIp, servePort).sync();
            log.info("connection success!");
            if (future.channel().isOpen()){
                log.info("channel is open!");
                Request request = new Request();
                request.setReqNo(123123L);
                request.setTimestamp(System.currentTimeMillis());
                request.setData(msg);
                boolean success = future.channel().writeAndFlush(request).isSuccess();
                log.info("send to server {}",success);
            }
            // 等待直到连接中断
            future.channel().closeFuture().sync();
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
