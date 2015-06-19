package com.kenvifire.jmemcached;


/**
 * Created by hannahzhang on 15/6/16.
 */
public enum CommandEnum {
    SET("set"),
    ADD("add"),
    REPLACE("replace"),
    APPEND("append"),
    PREPEND("prepend"),
    GET("get"),
    GETS("gets"),
    DELETE("delete"),
    INCR("incr"),
    DECR("decr"),
    TOUCH("touch"),
    INVALID("");


    private String command;

    private CommandEnum(String command) {
        this.command = command;
    }

    public static CommandEnum parseCommand(String command) {

        for(CommandEnum commandEnum : values()){

            if(commandEnum.command.equals(command)){
                return commandEnum;
            }
        }
        return null;
    }

}
