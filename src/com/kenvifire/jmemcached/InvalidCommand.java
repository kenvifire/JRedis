package com.kenvifire.jmemcached;

/**
 * Created by hannahzhang on 15/6/20.
 */
public class InvalidCommand implements ICommand{
    @Override
    public ICommandResult process() {
        return new ICommandResult() {
            @Override
            public String resultValue() {
                return CommandResultConstants.ERROR;
            }
        };
    }
}
