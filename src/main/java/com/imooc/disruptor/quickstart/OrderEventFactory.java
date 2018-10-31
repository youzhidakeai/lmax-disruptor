package com.imooc.disruptor.quickstart;

import com.lmax.disruptor.EventFactory;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kkc
 * Email: wochiyouzhi@gmail.common
 * Date: 2018-10-28
 * Time: 下午4:25
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {

    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();  //为了返回空的数据对象(Event)
    }
}
