package com.hengheng.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author lkj
 * @Date 2025/6/24 10:02
 * @Version 1.0
 * 客户端启动类
 */

public class Myclient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventExecutors  = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    //           使用匿名内部类初始化通道
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            //    添加客户端通道的处理器
                            socketChannel.pipeline().addLast(new MyclientHandler());
                        }
                    });
            System.out.println("客户端准备就绪，随时可以起飞~");
            //    连接服务端
            bootstrap.connect("127.0.0.1", 6666).sync();
        } finally {
            //关闭线程组
            eventExecutors.shutdownGracefully();
        }
    }
}

