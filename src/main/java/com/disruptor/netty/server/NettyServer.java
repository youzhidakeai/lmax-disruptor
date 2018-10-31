package com.disruptor.netty.server;

import com.disruptor.netty.common.MarshallingCodeCFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

public class NettyServer {

    public NettyServer(){

        //1.创建两个工作线程组：一个用于接受网络请求的线程组，另一个用于实际处理业务的线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            //netty构建工作
            serverBootstrap.group(bossGroup,workGroup).channel(
                    NioServerSocketChannel.class).option(
                    ChannelOption.SO_BACKLOG,1024 ).option(
                    //表示缓存区动态调配（自适应）
                    ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    //缓冲区 池化操作
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {

                            //解码
                            sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            //解码
                            sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());

                            //转换数据
                            sc.pipeline().addLast(new ServerHandler());
                        }
                    });
            //绑定端口，同步等待请求链接
           ChannelFuture channelFuture =  serverBootstrap.bind(8765).sync();

            System.err.println("Server startup......");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //释放线程组,优雅停机
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            System.err.println("Server Shutdown......");
        }


    }

}
