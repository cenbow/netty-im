package cn.zlmthy.framework.netty.im.server.init;

import cn.zlmthy.framework.codec.kryo.KryoDecoder;
import cn.zlmthy.framework.codec.kryo.KryoEncoder;
import cn.zlmthy.framework.codec.kryo.pool.KryoPool;
import cn.zlmthy.framework.netty.im.server.handler.NettyServerDispatchHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class KryoNettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Resource
    private KryoPool kryoSerializationFactory;

    @Override
    protected void initChannel(final SocketChannel ch) throws Exception {
        /*HTTP 服务的解码器*/
        ch.pipeline().addLast(new HttpServerCodec())
        /*HTTP 消息的合并处理*/
        .addLast(new HttpObjectAggregator(2048))
        /* http-request解码器
         * http服务器端对request解码
         */
        .addLast("decoder", new HttpRequestDecoder())
        /*
         * http-response解码器
         * http服务器端对response编码
         */
        .addLast(new HttpResponseDecoder())
        .addLast("aggregator", new HttpObjectAggregator(1048576))
        /*
         * 压缩
         */
        .addLast("deflater", new HttpContentCompressor())
        .addLast(new KryoEncoder(kryoSerializationFactory))
        /*HTTP 消息的合并处理*/
        .addLast(new HttpObjectAggregator(Integer.MAX_VALUE))
        .addLast(new KryoDecoder(kryoSerializationFactory))
        .addLast(new NettyServerDispatchHandler());
    }
}
