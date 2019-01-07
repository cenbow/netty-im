package cn.zlmthy.framework.codec.kryo;

import cn.zlmthy.framework.dto.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class KryoEncoder extends MessageToByteEncoder<Request> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Request object, ByteBuf out) throws Exception {
        KryoSerializer.serialize(object, out);
        ctx.flush();
    }

}