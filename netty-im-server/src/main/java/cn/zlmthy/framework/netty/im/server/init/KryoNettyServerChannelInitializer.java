package cn.zlmthy.framework.netty.im.server.init;

import cn.zlmthy.framework.codec.kryo.KryoDecoder;
import cn.zlmthy.framework.codec.kryo.KryoEncoder;
import cn.zlmthy.framework.codec.kryo.pool.KryoPool;
import cn.zlmthy.framework.netty.im.server.handler.NettyServerDispatchHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class KryoNettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Resource
    private NettyServerDispatchHandler serverDispatchHandler;

    @Resource
    private KryoPool kryoSerializationFactory;

    @Override
    protected void initChannel(final SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new KryoEncoder(kryoSerializationFactory))
        /*HTTP 消息的合并处理*/
        .addLast(new HttpObjectAggregator(Integer.MAX_VALUE))
        .addLast(new KryoDecoder(kryoSerializationFactory))
        .addLast(serverDispatchHandler);
    }
}
