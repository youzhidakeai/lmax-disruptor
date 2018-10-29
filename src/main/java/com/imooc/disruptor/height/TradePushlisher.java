package com.imooc.disruptor.height;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class TradePushlisher implements Runnable {

    private CountDownLatch latch;
    private Disruptor<Trade> disruptor;
    private static int PUBLISH_COUNT = 10;


    public TradePushlisher(CountDownLatch latch, Disruptor<Trade> disruptor) {
        this.disruptor = disruptor;
        this.latch = latch;
    }

    @Override
    public void run() {
        TradeEventTranslator eventTranslator = new TradeEventTranslator();
        //多次提交
        for (int i = 0; i<PUBLISH_COUNT;i++) {
            //新的提交任务的方式
            disruptor.publishEvent(eventTranslator);
        }
        latch.countDown();
    }
}

class TradeEventTranslator implements EventTranslator<Trade> {

    private Random random = new Random();

    @Override
    public void translateTo(Trade trade, long sequence) {
        this.generateTrade(trade);
    }

    private void generateTrade(Trade trade) {
        trade.setPrice(random.nextDouble() * 9999);
    }

}
