package com.disruptor.netty.client;

import com.disruptor.netty.disruptor.MessageConsumer;
import com.disruptor.netty.disruptor.RingBufferWorkerPoolFactory;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyClientApplication.class, args);

        MessageConsumer[] consumers = new MessageConsumer[4];
        for (int i=0; i<consumers.length; i ++){
            MessageConsumer messageConsumer = new MessageConsumerImpl4Client("code:ClientId:" + i);
            consumers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstance().inirAndStart(
                ProducerType.MULTI,
                1024*1024,
                new BlockingWaitStrategy(),
                consumers);

        //建立链接 发送数据
        new NettyClinet().sendData();

    }
}
