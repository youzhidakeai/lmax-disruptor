package com.disruptor.netty.disruptor;

import com.disruptor.netty.common.TranslatorData;
import com.disruptor.netty.common.TranslatorDataWapper;
import com.imooc.disruptor.mulit.Order;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class RingBufferWorkerPoolFactory {

    private static class SingletonHolder{
        static final RingBufferWorkerPoolFactory  instance = new RingBufferWorkerPoolFactory();
    }

    private RingBufferWorkerPoolFactory(){

    }

    public static RingBufferWorkerPoolFactory getInstance(){
        return SingletonHolder.instance;
    }

    private static Map<String,MessageProducer> producers = new ConcurrentHashMap<>();

    private static Map<String,MessageConsumer> consumers = new ConcurrentHashMap<>();

    private RingBuffer<TranslatorDataWapper> ringBuffer;

    private WorkerPool<TranslatorDataWapper> workerPool;

    private SequenceBarrier sequenceBarrier;


    public void inirAndStart(ProducerType type,
                             int bufferSize,
                             WaitStrategy waitStrategy,
                             MessageConsumer[] messageConsumers){
        this.ringBuffer = RingBuffer.create(type,
                new EventFactory<TranslatorDataWapper>() {
                    @Override
                    public TranslatorDataWapper newInstance() {
                        return new TranslatorDataWapper();
                    }
                },
                bufferSize,
                waitStrategy);

        //设置序号栅栏
        this.sequenceBarrier = this.ringBuffer.newBarrier();

        //设置workpool
        this.workerPool = new WorkerPool<TranslatorDataWapper>(
                this.ringBuffer,
                this.sequenceBarrier,
                new EventExceptionHandler(),
                messageConsumers);

        //把所构建的消费者置入池中
        for (MessageConsumer mc :messageConsumers){
            this.consumers.put(mc.getConsumerId(),mc);
        }

        //添加sequences
        this.ringBuffer.addGatingSequences(this.workerPool.getWorkerSequences());

        //启动工作池
        this.workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()/2));

    }

    public MessageProducer getMessageProducer(String producerId){
        MessageProducer messageProducer = this.producers.get(producerId);
        if(null == messageProducer) {
            messageProducer = new MessageProducer(producerId, this.ringBuffer);
            this.producers.put(producerId, messageProducer);
        }
        return messageProducer;
    }

    static class EventExceptionHandler implements ExceptionHandler<TranslatorDataWapper>{

        @Override
        public void handleEventException(Throwable throwable, long l, TranslatorDataWapper translatorDataWapper) {

        }

        @Override
        public void handleOnStartException(Throwable throwable) {

        }

        @Override
        public void handleOnShutdownException(Throwable throwable) {

        }
    }




}
