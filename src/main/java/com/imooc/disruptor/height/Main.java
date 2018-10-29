package com.imooc.disruptor.height;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws Exception {

        //构建一个线程池，用于提交任务
        ExecutorService executorService1 = Executors.newFixedThreadPool(4);
        ExecutorService executorService2 = Executors.newFixedThreadPool(4);

        //构建Disruptor
        Disruptor<Trade> disruptor = new Disruptor<Trade>(
                new EventFactory<Trade>() {
                    @Override
                    public Trade newInstance() {
                        return new Trade();
                    }
                },
                1024*1024,
                executorService2,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy());

        //把消费者设置到Disruptor中handleEventsWith

        //串行操作
//        disruptor.handleEventsWith(new Handler1())
//                 .handleEventsWith(new Handler2())
//                 .handleEventsWith(new Handler3());

        //并行操作
        disruptor.handleEventsWith(new Handler1(),new Handler2(),new Handler3());
//        disruptor.handleEventsWith(new Handler2());
//        disruptor.handleEventsWith(new Handler3());

        //启动disruptor，启动之后就会有一个返回值。
        RingBuffer<Trade> ringBuffer = disruptor.start();
        long begin = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(1);

        executorService1.submit(new TradePushlisher(latch,disruptor));
        latch.await(); //往下面走

        disruptor.shutdown();
        executorService1.shutdown();
        executorService2.shutdown();

        System.out.println("总耗时："+(System.currentTimeMillis()-begin));
    }
}
