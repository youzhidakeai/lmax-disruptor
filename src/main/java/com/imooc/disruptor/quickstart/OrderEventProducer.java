package com.imooc.disruptor.quickstart;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kkc
 * Email: wochiyouzhi@gmail.com
 * Date: 2018-10-28
 * Time: 下午4:57
 */
public class OrderEventProducer {

    private RingBuffer<OrderEvent> orderEventRingBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
            this.orderEventRingBuffer = ringBuffer;
    }

    //投递数据
    public void sendData(ByteBuffer date){

        // 在生产者发送消息的时候, 首先 需要从我们的ringBuffer里面 获取一个可用的序号
        long sequence = orderEventRingBuffer.next();
        try {
            // 根据这个序号，找到具体的“OrderEvent”元素, 注意：此时获取的OrderEvent对象是一个没有被赋值的“空对象”
            OrderEvent event = orderEventRingBuffer.get(sequence);

            // 进行实际的赋值操作
            event.setValue(date.getLong(0));
        }finally {
            // 提交(发布)操作
            orderEventRingBuffer.publish(sequence);
        }
    }
}
