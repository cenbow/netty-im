package cn.zlmthy.framework.netty.im.server.init;

import cn.zlmthy.framework.codec.kryo.KryoDecoder;
import cn.zlmthy.framework.codec.kryo.KryoEncoder;
import cn.zlmthy.framework.netty.im.server.handler.NettyServerDispatchHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.stereotype.Component;

public class KryoNettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(final SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast("decoder", new KryoDecoder())
                .addLast("encoder", new KryoEncoder())
                .addLast(new NettyServerDispatchHandler());
    }
}
