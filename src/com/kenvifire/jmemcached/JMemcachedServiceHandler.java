package com.kenvifire.jmemcached;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

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
            boolean noreply = false;
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

            noreply = cmdBuilder.toString().contains("noreplay");

            CommandEnum command = CommandEnum.INVALID;
            CacheItem cacheItem;
            CommandParam param = new CommandParam();

            if(!hasRn){
               command = CommandEnum.INVALID;
            }else{
                String[] commandLine = cmdBuilder.toString().split(" ");
                CommandEnum commandEnum = CommandEnum.parseCommand(commandLine[0]);

                switch (commandEnum){
                    case SET:
                    case ADD:
                    case REPLACE:
                    case APPEND:
                    case PREPEND:
                            param.setKey(commandLine[1]);
                            cacheItem = parseItem(commandLine, false);
                        break;
                    case GET:
                    case GETS:
                            param.setKey(commandLine[1]);
                            param.setKeys(parseKeys(commandLine));
                        break;

                    default:
                        break;
                }


            }

            ICommand actualCommand = CommandProcessorFactory.getCommand(command,param);
            ICommandResult result = actualCommand.process();
            ctx.write(result.resultValue());

        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private CacheItem parseItem(String[] commandLine, boolean withCAS){
        CacheItem cacheItem = new CacheItem();
        try{
            cacheItem.setKey(commandLine[1]);
            cacheItem.setFlags(Integer.parseInt(commandLine[2]));
            cacheItem.setExpTime(Long.parseLong(commandLine[3]));
            cacheItem.setBytes(Integer.parseInt(commandLine[4]));

            if(withCAS){
                cacheItem.setCasUnique(Long.parseLong(commandLine[5]));
            }
            return cacheItem;
        }catch (Exception e){
            e.printStackTrace();

            return null;
        }

    }

    private List<String> parseKeys(String[] commandLine){
        List<String> keys = new ArrayList<>();

        for(int i=1; i<commandLine.length; i++){
            keys.add(commandLine[i]);
        }
        return keys;
    }
}
