package com.example.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 杨帮东 (qq:397827222)
 * @version 1.0
 * @date 2020/01/04 23:03
 **/
public class StringClient {

    public static void main(String[] args) {
        EventLoopGroup client = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(client).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0,4,0,4));
                    pipeline.addLast(new LengthFieldPrepender(4));
                    pipeline.addLast(new StringEncoder(StandardCharsets.UTF_8));
                    pipeline.addLast(new StringDecoder(StandardCharsets.UTF_8));
                    pipeline.addLast(new SimpleChannelInboundHandler<String>() {
                        @Override
                        protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
                            System.out.println("收到来自服务端的消息:" + msg);
                            ctx.writeAndFlush(msg);
                        }

                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                            cause.printStackTrace();
                            ctx.close();
                        }

                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ctx.writeAndFlush("消息");
                        }
                    });
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8099).sync();
            channelFuture.channel().closeFuture().sync();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            client.shutdownGracefully();

        }
    }
}
