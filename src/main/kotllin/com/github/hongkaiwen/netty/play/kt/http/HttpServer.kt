package com.github.hongkaiwen.netty.play.kt.http

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel

fun main(args: Array<String>) {
    var boss = NioEventLoopGroup()
    var worker = NioEventLoopGroup()
    try {
        var server = ServerBootstrap()
        server.group(boss, worker)
        server.channel(NioServerSocketChannel::class.java)
        server.childHandler(HttpServerInitiallizer())
        server.option(ChannelOption.SO_BACKLOG, 128)
        server.childOption(ChannelOption.SO_KEEPALIVE, true)
        var cf = server.bind(8888).sync()
        cf.channel().closeFuture().sync()
    }finally {
        boss.shutdownGracefully()
        worker.shutdownGracefully()
    }
}