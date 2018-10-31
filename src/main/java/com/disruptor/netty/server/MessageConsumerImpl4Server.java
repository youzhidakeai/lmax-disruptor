package com.disruptor.netty.server;

import com.disruptor.netty.common.TranslatorData;
import com.disruptor.netty.common.TranslatorDataWapper;
import com.disruptor.netty.disruptor.MessageConsumer;
import io.netty.channel.ChannelHandlerContext;

public class MessageConsumerImpl4Server extends MessageConsumer {

    public MessageConsumerImpl4Server(String consumerId) {
        super(consumerId);
    }

    public void onEvent(TranslatorDataWapper event) throws Exception {
        TranslatorData request = event.getData();
        ChannelHandlerContext ctx = event.getCtx();

        //业务处理逻辑:
        System.err.println("Sever端: id= " + request.getId()
                + ", name= " + request.getName()
                + ", message= " + request.getMessage());

        //回送响应信息:
        TranslatorData response = new TranslatorData();
        response.setId("resp: " + request.getId());
        response.setName("resp: " + request.getName());
        response.setMessage("resp: " + request.getMessage());
        //写出response响应信息:
        ctx.writeAndFlush(response);
    }
}
