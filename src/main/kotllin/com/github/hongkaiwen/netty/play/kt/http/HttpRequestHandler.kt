package com.github.hongkaiwen.netty.play.kt.http

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.*
import io.netty.handler.codec.http.HttpUtil.is100ContinueExpected
import io.netty.util.CharsetUtil

class HttpRequestHandler : SimpleChannelInboundHandler<FullHttpRequest>() {

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: FullHttpRequest?) {
        if(is100ContinueExpected(msg)) ctx?.write(DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE))

        var uri = msg?.uri()

        var responsse = DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer("your uri is $uri", CharsetUtil.UTF_8))

        responsse.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8")

        ctx?.writeAndFlush(responsse)?.addListener(ChannelFutureListener.CLOSE)
    }

}