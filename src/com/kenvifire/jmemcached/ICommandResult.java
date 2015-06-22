package com.kenvifire.jmemcached;

/**
 * Created by hannahzhang on 15/6/20.
 */
public interface ICommandResult {
   public String resultValue();
   public ErrorType getErrorType();
}
