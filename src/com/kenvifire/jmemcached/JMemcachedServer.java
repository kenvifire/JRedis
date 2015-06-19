package com.kenvifire.jmemcached;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * Created by hannahzhang on 15/6/16.
 */
public class JMemcachedServer {

    private int port;


    public JMemcachedServer(int port) {
        this.port = port;
    }

    public void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        int numOfWorkers = 4;
        WorkerThread[] workerThreads = new WorkerThread[numOfWorkers];

        for(int i =0 ; i < numOfWorkers; i++){
            workerThreads[i] = new WorkerThread();
        }

        JMemcachedServiceHandler jMemcachedServiceHandler =
             new JMemcachedServiceHandler(workerThreads);

        try{


            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(jMemcachedServiceHandler);
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();

        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String [] args) throws Exception{
        JMemcachedServer server = new JMemcachedServer(12121);
        server.run();
    }
}
