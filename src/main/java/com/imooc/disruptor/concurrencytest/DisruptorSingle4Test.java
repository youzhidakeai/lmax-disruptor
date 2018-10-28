package com.imooc.disruptor.concurrencytest;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

public class DisruptorSingle4Test {

    public static void main(String[] args) {
        int ringBufferSize = 65536;
        final Disruptor<Data> disruptor = new Disruptor<Data>(
                 new EventFactory<Data>() {
					public Data newInstance() {
						return new Data();
					}
				},
                ringBufferSize,
                Executors.newSingleThreadExecutor(),
                ProducerType.SINGLE, 
                //new BlockingWaitStrategy()
                new YieldingWaitStrategy()
        		);

        DataConsumer consumer = new DataConsumer();
        //消费数据
        disruptor.handleEventsWith(consumer);
        disruptor.start();
        new Thread(new Runnable() {

            public void run() {
                RingBuffer<Data> ringBuffer = disruptor.getRingBuffer();
                for (long i = 0; i < Constants.EVENT_NUM_OHM; i++) {
                    long seq = ringBuffer.next();
                    Data data = ringBuffer.get(seq);
                    data.setId(i);
                    data.setName("c" + i);
                    ringBuffer.publish(seq);
                }
            }
        }).start();
    }
}