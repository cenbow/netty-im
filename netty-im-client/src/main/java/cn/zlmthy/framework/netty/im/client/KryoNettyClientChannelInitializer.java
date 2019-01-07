package cn.zlmthy.framework.netty.im.client;

import cn.zlmthy.framework.codec.kryo.KryoDecoder;
import cn.zlmthy.framework.codec.kryo.KryoEncoder;
import cn.zlmthy.framework.netty.im.client.handler.NettyClientDispatchHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import javax.annotation.Resource;

public class KryoNettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {



    @Override
    protected void initChannel(final SocketChannel ch) throws Exception {
        /*HTTP 服务的解码器*/
        ch.pipeline()
        /*HTTP 消息的合并处理*/
        .addLast(new HttpObjectAggregator(2048))
        .addLast("decoder", new KryoDecoder())
        .addLast("encoder", new KryoEncoder())
        .addLast("aggregator", new HttpObjectAggregator(1048576))
        /*
         * 压缩
         */
        .addLast("deflater", new HttpContentCompressor())
//        .addLast("encoder", new KryoEncoder(kryoSerializationFactory))
        /*HTTP 消息的合并处理*/
        .addLast(new HttpObjectAggregator(Integer.MAX_VALUE))
//        .addLast("decoder",new KryoDecoder(kryoSerializationFactory))
        .addLast(new NettyClientDispatchHandler());
    }
}
