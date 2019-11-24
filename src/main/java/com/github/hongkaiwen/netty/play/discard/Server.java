package com.github.hongkaiwen.netty.play.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * https://netty.io/wiki/user-guide-for-4.x.html
 */
public class Server {

  public static void main(String[] args) throws Exception {
    NioEventLoopGroup boss = new NioEventLoopGroup();
    NioEventLoopGroup worker = new NioEventLoopGroup();

    try{
      ServerBootstrap server = new ServerBootstrap();
      server.group(boss, worker)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          public void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new DiscardHandler());
          }
        }).option(ChannelOption.SO_BACKLOG, 128)
        .childOption(ChannelOption.SO_KEEPALIVE, true);

      ChannelFuture f = server.bind(8080).sync();
      f.channel().closeFuture().sync();
    }finally {
      worker.shutdownGracefully();
      boss.shutdownGracefully();
    }
  }

}
