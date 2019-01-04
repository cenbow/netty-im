package cn.zlmthy.framework.netty.im.client;


import cn.zlmthy.framework.codec.kryo.pool.KryoPool;
import cn.zlmthy.framework.dto.Request;
import cn.zlmthy.framework.netty.im.client.handler.NettyClientDispatchHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

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
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new KryoNettyClientChannelInitializer());
        try {

            ChannelFuture future = bootstrap.connect(serverIp, servePort).sync();
            Request request = new Request();
            request.setReqNo(123123L);
            request.setTimestamp(System.currentTimeMillis());
            request.setData(msg);
            future.channel().writeAndFlush(request);
            System.out.println(future.isDone());
            // 等待直到连接中断
            future.channel().closeFuture().sync();
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Bean
    @Primary
    public KryoPool getKryoPool(){
        KryoPool pool = new KryoPool();
        pool.init();
        return pool;
    }
}
