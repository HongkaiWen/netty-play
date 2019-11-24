package com.github.hongkaiwen.netty.play.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * https://www.cnblogs.com/demingblog/p/9970772.html
 */
public class HttpServer {

  public static void main(String[] args) throws InterruptedException {

    NioEventLoopGroup boss = new NioEventLoopGroup();
    NioEventLoopGroup worker = new NioEventLoopGroup();
    try{
      ServerBootstrap server = new ServerBootstrap();
      server.group(boss, worker)
        .channel(NioServerSocketChannel.class)
        .childHandler(new HttpServerInitiallizer())
        .option(ChannelOption.SO_BACKLOG, 128)
        .childOption(ChannelOption.SO_KEEPALIVE, true);
      ChannelFuture f = server.bind(8080).sync();
      f.channel().closeFuture().sync();
    } finally {
      worker.shutdownGracefully();
      boss.shutdownGracefully();
    }
  }
}
