package com.example.demo.server.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.http.*;
import java.nio.charset.StandardCharsets;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 杨帮东 (qq:397827222)
 * @version 1.0
 * @date 2020/01/04 02:06
 **/
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("http 的response", StandardCharsets.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.ACCEPT_CHARSET, "UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes() + "");
            ctx.writeAndFlush(response);

        }
    }
}