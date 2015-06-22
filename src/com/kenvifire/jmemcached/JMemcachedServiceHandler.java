package com.kenvifire.jmemcached;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

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
            boolean noReply = false;
            boolean hasData = true;
            while(in.isReadable()){
                char c = (char)in.readByte();
                System.out.print(c);
                if(c == '\r'){
                    if(in.isReadable() && (char)in.readByte() == '\n'){
                        hasRn = true;
                       break;
                    }
                }

                cmdBuilder.append(c);
            }

            noReply = cmdBuilder.toString().contains("noreplay");

            CommandEnum command = CommandEnum.INVALID;
            CacheItem cacheItem;
            CommandParam param = new CommandParam();
            String errorMsg;

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
                            if(commandLine.length != 5 || commandLine.length != 6) {
                                param.setKey(commandLine[1]);
                                cacheItem = parseItem(commandLine, false);

                                if (cacheItem == null) {
                                    param.setErrorType(ErrorType.CLIENT_ERROR);
                                    break;
                                }

                                int bytes = param.getBytes();

                                if(bytes > MemcachedConstants.MAX_DATA_LEN){
                                    param.setErrorType(ErrorType.SERVER_ERROR);
                                    param.setErrorMsg(CommandResultConstants.OBJECT_TOO_LARGE);
                                    break;
                                }else if(bytes < 0){
                                    param.setErrorType(ErrorType.CLIENT_ERROR);
                                    param.setErrorMsg(CommandResultConstants.BAD_CMD_FORMAT);
                                    break;
                                }


                            }else{
                                commandEnum = CommandEnum.INVALID;
                            }
                        break;
                    case GET:
                    case GETS:
                            if(commandLine.length >= 2) {
                                param.setKey(commandLine[1]);
                                param.setKeys(parseKeys(commandLine));
                            }else{
                                commandEnum = CommandEnum.INVALID;
                            }
                        break;
                    case DELETE:
                            if(commandLine.length == 2 || commandLine.length == 3){
                               param.setKey(commandLine[1]);
                            }else {
                                commandEnum = CommandEnum.INVALID;
                            }
                    case INCR:
                    case DECR:
                            if(commandLine.length == 3 || commandLine.length == 4){
                                param.setKey(commandLine[1]);
                                try {
                                    long value = Long.parseLong(commandLine[2]);
                                    param.setValue(value);
                                }catch (NumberFormatException e){
                                    errorMsg = CommandResultConstants.INVALID_NUMERIC;
                                    commandEnum = CommandEnum.INVALID;
                                }
                            }else{
                                commandEnum = CommandEnum.INVALID;
                            }
                        break;
                    case TOUCH:
                            if(commandLine.length == 3 || commandLine.length == 4){
                                param.setKey(commandLine[1]);
                                try{
                                    long expTime = Long.parseLong(commandLine[2]);
                                }catch (NumberFormatException e){
                                    //TODO
                                    commandEnum = CommandEnum.INVALID;
                                }

                            }

                    default:
                        break;
                }


            }

            ICommand actualCommand = CommandProcessorFactory.getCommand(command,param);


            ICommandResult result = actualCommand.process();



            String resultMsg = result.resultValue();
            final ByteBuf resultBuf = ctx.alloc().buffer(resultMsg.getBytes().length);
            resultBuf.writeBytes(resultMsg.getBytes());
            ctx.write(resultBuf);
            ctx.flush();

            if(result.getErrorType() == ErrorType.SERVER_ERROR){
                ctx.close();
            }

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
