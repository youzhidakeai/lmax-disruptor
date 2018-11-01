package com.disruptor.netty.server;

import com.disruptor.netty.disruptor.MessageConsumer;
import com.disruptor.netty.disruptor.RingBufferWorkerPoolFactory;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);

        MessageConsumer[] consumers = new MessageConsumer[4];
        for (int i=0; i<consumers.length; i ++){
            MessageConsumer messageConsumer = new MessageConsumerImpl4Server("code:sessionId:" + i);
            consumers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstance().inirAndStart(
                ProducerType.MULTI,
                1024*1024,
                new YieldingWaitStrategy(),
                consumers);


        new NettyServer();
    }
}
