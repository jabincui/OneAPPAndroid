package com.oneapp.oneappandroidapp.util;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyClient {
    EventLoopGroup group = new NioEventLoopGroup();
    public void start() throws Exception {
        try {
            //bossGroup只处理连接请求，真正和客户端处理业务是workerGroup完成
            System.out.println("开始启动netty Client");
            //创建服务器端的启动对象
            //注意刻划断使用的不是serverBootstrap而是BootStrap
            Bootstrap bootstrap = new Bootstrap(); // (1)
            bootstrap.group(group); // 设置线程组
            bootstrap.channel(NioSocketChannel.class); // 使用NioSocketChannel作为客户端的通道实现
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    //ch.pipeline().addLast("Sdecoder", new StringDecoder());
                    //ch.pipeline().addLast("Sencoder", new StringEncoder());
                    ch.pipeline().addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
                    //每隔4s检查一下是否有写事件，如果没有就触发 handler 中的 userEventTriggered(ChannelHandlerContext ctx, Object evt)逻辑
                    ch.pipeline().addLast("encoder", new ObjectEncoder());
                    ch.pipeline().addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                    ch.pipeline().addLast(new NettyClientHandler());//加入自己的业务处理handler
                }
            });
            System.out.println("配置完成");
            // Start the client.
            ChannelFuture cf = bootstrap.connect("47.93.49.119", 8889).sync(); //连接服务端8880

            System.out.println("========" + cf.channel().localAddress() + "========");//这里输出的是client的端口

            //监听是否成功
            if (cf.isSuccess()) {
                System.out.println("nettyClient启动成功");
            } else {
                System.out.println("nettyClient启动失败");
            }

            cf.channel().closeFuture().sync();
        } catch (Exception e){
            group.shutdownGracefully();
        }
        finally {
            group.shutdownGracefully();
        }

    }


}

