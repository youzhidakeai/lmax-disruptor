package com.imooc.disruptor.mulit;

import com.lmax.disruptor.WorkHandler;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements WorkHandler<Order> {

    private String consumerId;

    private static AtomicInteger count = new AtomicInteger(0);

    private Random random = new Random();

    public Consumer(String consumerId){
        this.consumerId = consumerId;
    }

    @Override
    public void onEvent(Order order) throws Exception {
        Thread.sleep(1 * random.nextInt(5));
        System.err.println("当前消费者："+this.consumerId+", 消费信息ID："+order.getId());
        count.incrementAndGet();
    }

    public int getCount(){
        return count.get();
    }
}
