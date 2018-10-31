package com.disruptor.netty.server;

import com.disruptor.netty.common.TranslatorData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {


    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        TranslatorData request = (TranslatorData)msg;
        System.err.println("Server端：ID："+request.getId()+
                "name= "+request.getName()+
                "Message= "+request.getMessage());

        TranslatorData response = new TranslatorData();
        response.setId("id"+request.getId());
        response.setName("name"+request.getName());
        response.setMessage("message"+request.getMessage());

        ctx.writeAndFlush(response);
    }
}
