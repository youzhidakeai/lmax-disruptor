package com.imooc.disruptor.quickstart;


import com.lmax.disruptor.EventHandler;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kkc
 * Email: wochiyouzhi@gmail.com
 * Date: 2018-10-28
 * Time: 下午4:28
 */
//消费者
public class OrderEventHandler implements EventHandler<OrderEvent> {


    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        Thread.sleep(Integer.MAX_VALUE);
        System.err.println("消费者: " + event.getValue());
    }

}