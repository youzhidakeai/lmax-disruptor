package com.imooc.disruptor.height;

import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.EventHandler;

public class Handler1 implements EventHandler<Trade>, WorkHandler<Trade> {
        //EventHandler
        public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
            this.onEvent(event);
        }

        //WorkHandler
        public void onEvent(Trade event) throws Exception {
            System.err.println("handler 1 : SET NAME");
            Thread.sleep(1000);
            event.setName("H1");
        }

    }
