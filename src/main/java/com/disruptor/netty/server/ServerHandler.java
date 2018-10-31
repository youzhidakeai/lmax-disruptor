package com.disruptor.netty.server;

import com.disruptor.netty.common.TranslatorData;
import com.disruptor.netty.disruptor.MessageProducer;
import com.disruptor.netty.disruptor.RingBufferWorkerPoolFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {


    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        /*
        TranslatorData request = (TranslatorData)msg;
        System.err.println("Server端：ID："+request.getId()+
                "name= "+request.getName()+
                "Message= "+request.getMessage());
        //数据库持久化操作IO读写--->交给一个线程池去异步放入调用执行
        TranslatorData response = new TranslatorData();
        response.setId("id"+request.getId());
        response.setName("name"+request.getName());
        response.setMessage("message"+request.getMessage());

        ctx.writeAndFlush(response);
        */

        TranslatorData request = (TranslatorData)msg;

        //自己的应用服务应该有一个ID生成规则
        String producer = "code:sessiondId:001";

        MessageProducer messageProducer = RingBufferWorkerPoolFactory.getInstance().getMessageProducer("");
        messageProducer.onData(request,ctx);



    }
}
