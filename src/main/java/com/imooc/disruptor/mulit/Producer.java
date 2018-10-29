package com.imooc.disruptor.mulit;

import com.lmax.disruptor.RingBuffer;

public class Producer {

    private RingBuffer<Order> ringBuffer;

    public Producer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(String uuid) {
       long sequence =  ringBuffer.next();
        try{
            Order order = ringBuffer.get(sequence);
            order.setId(uuid);
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
