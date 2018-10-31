package com.disruptor.netty.client;

import com.disruptor.netty.common.TranslatorData;
import com.disruptor.netty.disruptor.MessageProducer;
import com.disruptor.netty.disruptor.RingBufferWorkerPoolFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       /*
        try{

            TranslatorData response = (TranslatorData)msg;
            System.err.println("Client 端： id= "+response.getId()
                                +"name = "+response.getName()
                                +"message = "+response.getMessage()
            );
        }finally {
            //一定要释放缓存
            ReferenceCountUtil.release(msg);
        }
        */

        TranslatorData response = (TranslatorData)msg;
        String producerId = "code:seesionId:002";
        MessageProducer messageProducer = RingBufferWorkerPoolFactory.getInstance().getMessageProducer(producerId);
        messageProducer.onData(response, ctx);

    }
}
