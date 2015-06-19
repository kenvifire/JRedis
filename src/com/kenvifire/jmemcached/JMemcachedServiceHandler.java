package com.kenvifire.jmemcached;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

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
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        try{
            StringBuilder cmdBuilder = new StringBuilder();
            boolean hasRn = false;
            while(in.isReadable()){
                char c = in.readChar();
                if(c == '\r'){
                    if(in.isReadable() && in.readChar() == '\n'){
                        hasRn = true;
                       break;
                    }
                }

                cmdBuilder.append(c);
            }
            CommandEnum command;
            if(!hasRn){
               command = CommandEnum.INVALID;
            }else{
                String[] commndLine = cmdBuilder.toString().split(" ");
                CommandEnum commandEnum = CommandEnum.parseCommand(commndLine[0]);

                switch (commandEnum){
                    case SET:
                        break;

                }
            }


        }finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
