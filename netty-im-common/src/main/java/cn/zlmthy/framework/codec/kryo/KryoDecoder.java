package cn.zlmthy.framework.codec.kryo;

import cn.zlmthy.framework.codec.kryo.pool.KryoPool;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class KryoDecoder extends LengthFieldBasedFrameDecoder {

    private final KryoPool kryoPool;

    public KryoDecoder(final KryoPool kryoSerializationFactory) {
        super(10485760, 0, 4, 0, 4);
        this.kryoPool = kryoSerializationFactory;
    }

}
