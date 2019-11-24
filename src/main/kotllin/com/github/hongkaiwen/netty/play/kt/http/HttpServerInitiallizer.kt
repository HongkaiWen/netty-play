package com.github.hongkaiwen.netty.play.kt.http

import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpServerCodec

/**
 * http pipeline 初始化器
 */
class HttpServerInitiallizer : ChannelInitializer<Channel>() {

    override fun initChannel(ch: Channel?) {
        var pipeline = ch?.pipeline()
        pipeline?.addLast(HttpServerCodec())
        pipeline?.addLast(HttpObjectAggregator(1024*1024*64))
        pipeline?.addLast(HttpRequestHandler())
    }

}