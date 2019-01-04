package cn.zlmthy.framework.netty.im.server.enums;


import cn.zlmthy.framework.netty.im.server.init.KryoNettyServerChannelInitializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zengliming
 */
public enum SerializeType {

    /**
     * 序列化方式
     */
    Kryo(KryoNettyServerChannelInitializer.class);

    private final Class<? extends ChannelInitializer<SocketChannel>> serverChannelInitializer;

    private SerializeType(final Class<? extends ChannelInitializer<SocketChannel>> serverChannelInitializer) {
        this.serverChannelInitializer = serverChannelInitializer;
    }

    public Class<? extends ChannelInitializer<SocketChannel>> getServerChannelInitializer() {
        return serverChannelInitializer;
    }

}
