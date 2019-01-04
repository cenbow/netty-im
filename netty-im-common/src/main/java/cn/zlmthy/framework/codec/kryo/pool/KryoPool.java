package cn.zlmthy.framework.codec.kryo.pool;

import cn.zlmthy.framework.codec.kryo.KryoSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import javax.annotation.PostConstruct;
import java.io.IOException;


public class KryoPool {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    private KyroFactory kyroFactory;

    private static final int maxTotal = 100;

    private int minIdle=0;

    private long maxWaitMillis=600;

    private long minEvictableIdleTimeMillis = 60;

    public void init() {
        kyroFactory = new KyroFactory(maxTotal, minIdle, maxWaitMillis, minEvictableIdleTimeMillis);
    }

    public void encode(final ByteBuf out, final Object message) throws IOException {
        ByteBufOutputStream bout = new ByteBufOutputStream(out);
        bout.write(LENGTH_PLACEHOLDER);
        KryoSerialization kryoSerialization = new KryoSerialization(kyroFactory);
        kryoSerialization.serialize(bout, message);
    }

    public Object decode(final ByteBuf in) throws IOException {
        KryoSerialization kryoSerialization = new KryoSerialization(kyroFactory);
        return kryoSerialization.deserialize(new ByteBufInputStream(in));
    }
}
