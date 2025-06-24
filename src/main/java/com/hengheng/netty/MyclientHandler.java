package com.hengheng.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @Author lkj
 * @Date 2025/6/24 10:05
 * @Version 1.0
 */
public class MyclientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发送消息到服务端
        ctx.writeAndFlush(Unpooled.copiedBuffer("歪比巴卜~茉莉~Are you good~马来西亚~", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收服务端发送过来的消息
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("收到服务端" + ctx.channel().remoteAddress() + "的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * @description taskQueue任务队列
     * @param
     * @author  lkj
     * @date  2025/6/24
     * @return
     */
    //@Override
    //public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    //    //接收服务端发送过来的消息
    //    ctx.channel().eventLoop().execute(new Runnable() {
    //        @Override
    //        public void run() {
    //            try {
    //                Thread.sleep(1000);
    //                System.out.println("长时间的业务处理");
    //            } catch (InterruptedException e) {
    //                throw new RuntimeException(e);
    //            }
    //        }
    //    });
    //}
}
