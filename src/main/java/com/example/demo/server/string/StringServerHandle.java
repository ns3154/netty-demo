package com.example.demo.server.string;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 杨帮东 (qq:397827222)
 * @version 1.0
 * @date 2020/01/04 22:54
 **/
public class StringServerHandle extends SimpleChannelInboundHandler<String> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("收到来自客户端的消息:" + msg);
        ctx.writeAndFlush(msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();;
        ctx.close();
    }
}
