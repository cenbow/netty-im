package cn.zlmthy.framework;

import static org.junit.Assert.assertTrue;

import cn.zlmthy.framework.dto.Request;
import cn.zlmthy.framework.netty.im.client.KryoNettyClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Test
    public void send() {
        String msg = "hello world";
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioSocketChannel.class)
                .handler(new KryoNettyClientChannelInitializer());
        try {

            ChannelFuture future = bootstrap.connect("127.0.0.1", 9999).sync();
            if (future.channel().isOpen()){
                Request request = new Request();
                request.setReqNo(123123L);
                request.setTimestamp(System.currentTimeMillis());
                request.setData(msg);
                future.channel().writeAndFlush(request);
            }
            // 等待直到连接中断
            future.channel().closeFuture().sync();
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
