package com.disruptor.netty.client;

import com.disruptor.netty.common.TranslatorData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
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
    }
}
