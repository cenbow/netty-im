package cn.zlmthy.framework.codec.kryo.pool;

import cn.zlmthy.framework.dto.Request;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import de.javakaffee.kryoserializers.*;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.lang.reflect.InvocationHandler;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

final class PooledKryoFactory extends BasePooledObjectFactory<Kryo> {

    @Override
    public Kryo create() throws Exception {
        return createKryo();
    }

    @Override
    public PooledObject<Kryo> wrap(Kryo kryo) {
        return new DefaultPooledObject<Kryo>(kryo);
    }

    private Kryo createKryo() {
        Kryo kryo = new KryoReflectionFactorySupport() {

            @Override
            public Serializer<?> getDefaultSerializer(@SuppressWarnings("rawtypes") final Class clazz) {
                if (EnumMap.class.isAssignableFrom(clazz)) {
                    return new EnumMapSerializer();
                }
                if (SubListSerializers.ArrayListSubListSerializer.canSerialize(clazz) || SubListSerializers.JavaUtilSubListSerializer.canSerialize(clazz)) {
                    return SubListSerializers.createFor(clazz);
                }
                return super.getDefaultSerializer(clazz);
            }
        };
        kryo.setRegistrationRequired(false);
        kryo.register(Request.class);
        kryo.register(Arrays.asList("").getClass(), new ArraysAsListSerializer());
        kryo.register(GregorianCalendar.class, new GregorianCalendarSerializer());
        kryo.register(InvocationHandler.class, new JdkProxySerializer());
        kryo.register(BigDecimal.class, new DefaultSerializers.BigDecimalSerializer());
        kryo.register(BigInteger.class, new DefaultSerializers.BigIntegerSerializer());
        kryo.register(Pattern.class, new RegexSerializer());
        kryo.register(BitSet.class, new BitSetSerializer());
        kryo.register(URI.class, new URISerializer());
        kryo.register(UUID.class, new UUIDSerializer());
        UnmodifiableCollectionsSerializer.registerSerializers(kryo);
        SynchronizedCollectionsSerializer.registerSerializers(kryo);

        kryo.register(HashMap.class);
        kryo.register(ArrayList.class);
        kryo.register(LinkedList.class);
        kryo.register(HashSet.class);
        kryo.register(TreeSet.class);
        kryo.register(Hashtable.class);
        kryo.register(Date.class);
        kryo.register(Calendar.class);
        kryo.register(ConcurrentHashMap.class);
        kryo.register(SimpleDateFormat.class);
        kryo.register(GregorianCalendar.class);
        kryo.register(Vector.class);
        kryo.register(BitSet.class);
        kryo.register(StringBuffer.class);
        kryo.register(StringBuilder.class);
        kryo.register(Object.class);
        kryo.register(Object[].class);
        kryo.register(String[].class);
        kryo.register(byte[].class);
        kryo.register(char[].class);
        kryo.register(int[].class);
        kryo.register(float[].class);
        kryo.register(double[].class);
        UnmodifiableCollectionsSerializer.registerSerializers(kryo);
        return kryo;
    }
}
