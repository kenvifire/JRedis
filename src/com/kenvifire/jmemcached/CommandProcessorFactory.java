package com.kenvifire.jmemcached;

/**
 * Created by hannahzhang on 15/6/20.
 */
public class CommandProcessorFactory {

    public static ICommand getCommand(CommandEnum commandEnum, CommandParam param){
        switch (commandEnum){
            case SET:
            case ADD:
            case REPLACE:
            case PREPEND:
            case APPEND:
                return new StoreCommand();

            case GET:
            case GETS:
                return new GetCommand();

            case DELETE:
                return new DeleteCommand();

            case INCR:
            case DECR:
                return new NumericCommand();

            case TOUCH:
                return new DeleteCommand();

            case INVALID:
                default:
                return new InvalidCommand();
        }
    }
}
