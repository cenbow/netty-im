package cn.zlmthy.framework.netty.im.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class NettyImServerApplication {
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        SpringApplication.run(NettyImServerApplication.class, args);
    }


}
