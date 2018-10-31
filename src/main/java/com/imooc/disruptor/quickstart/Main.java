package com.imooc.disruptor.quickstart;


import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kkc
 * Email: wochiyouzhi@gmail.common
 * Date: 2018-10-28
 * Time: 下午4:35
 */
public class Main {

    public static void main(String[] args) {

        //准备参数
        OrderEventFactory orderEventFactory = new OrderEventFactory();
        int ringBufferSize = 4;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        /**
         * 1 eventFactory: 消息(event)工厂对象
         * 2 ringBufferSize: 容器的长度
         * 3 executor: 线程池(建议使用自定义线程池) RejectedExecutionHandler
         * 4 ProducerType: 单生产者 还是 多生产者
         * 5 waitStrategy: 等待策略
         */
        //实例化一个disruptor对象
        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(orderEventFactory,
                ringBufferSize,
                executor,
                ProducerType.SINGLE,
                new BlockingWaitStrategy());

        //添加消费者的监听(disruptor与消费者的一个关联关系)
        disruptor.handleEventsWith(new OrderEventHandler());

        //启动disruptor
        disruptor.start();

        //获取实际存储数据的容器: RingBuffer
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        //创建生产者
        OrderEventProducer  producer = new  OrderEventProducer(ringBuffer);
        //字节长度
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (long i = 0;i < 100; i ++ ){
            byteBuffer.putLong(0,i);//每一次for循环，都会把i替换到第0个位置，
            producer.sendData(byteBuffer);
        }

        //结束
        disruptor.shutdown();
        executor.shutdown();
    }
}
