package cn.zlmthy.framework.netty.im.client;

import cn.zlmthy.framework.codec.kryo.KryoDecoder;
import cn.zlmthy.framework.codec.kryo.KryoEncoder;
import cn.zlmthy.framework.netty.im.client.handler.NettyClientDispatchHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class KryoNettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {



    @Override
    protected void initChannel(final SocketChannel ch) throws Exception {
        /*HTTP 服务的解码器*/
        ch.pipeline()
        .addLast("decoder", new KryoDecoder())
        .addLast("encoder", new KryoEncoder())
        .addLast(new NettyClientDispatchHandler());
    }
}
