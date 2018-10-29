package com.imooc.disruptor.height;

import com.lmax.disruptor.EventHandler;

public class Handler3 implements EventHandler<Trade> {

    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println("handler 3 : NAME: "
                + event.getName()
                + ", ID: "
                + event.getId()
                + ", PRICE: "
                + event.getPrice()
                + " INSTANCE : " + event.toString());
    }
}
