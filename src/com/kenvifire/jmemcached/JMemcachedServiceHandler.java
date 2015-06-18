package com.kenvifire.jmemcached;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

/**
 * Created by hannahzhang on 15/6/15.
 */
public class JMemcachedServiceHandler extends ChannelHandlerAdapter{
    private WorkerThread[] workerThreads;
    private int lastWorker = 0;

    public JMemcachedServiceHandler(WorkerThread[] workerThreads){
        this.workerThreads = workerThreads;
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        lastWorker = (lastWorker + 1) % workerThreads.length;
        WorkerThread workerThread = workerThreads[lastWorker];
        //TODO dispatch task


    }

}
