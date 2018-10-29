package com.imooc.disruptor.height;

import com.lmax.disruptor.EventHandler;

public class Handler4 implements EventHandler<Trade> {
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println("handler 4 : SET PRICE");
        Thread.sleep(1000);
        event.setPrice(17.0);
    }
}
