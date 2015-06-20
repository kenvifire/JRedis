package com.kenvifire.jmemcached;

/**
 * Created by hannahzhang on 15/6/20.
 */
public class CommandResultConstants {

    public static final String STORED = "STORED\r\n";

    public static final String NOT_STORED = "NOT_STORED\r\n";

    public static final String EXISTS = "EXISTS\r\n";

    public static final String NOT_FOUND = "NOT_FOUND\r\n";

    public static final String END = "END\r\n";

    public static final String DELETED = "DELETED\r\n";

    public static final String VALUE = "%s\r\n";

    public static final String TOUCHED = "TOUCHED\r\n";

    public static final String ERROR = "ERROR\r\n";

    public static final String INVALID_NUMERIC = "CLIENT_ERROR invalid numeric delta argument\r\n";

    public static final String OBJECT_TOO_LARGE = "object too large for cache";

    public static final String SERVER_ERROR = "SERVER_ERROR %s\r\n";

    public static final String CLIENT_ERROR = "CLIENT_ERROR %s\r\n";

    public static final String BAD_CMD_FORMAT = "bad command line format";


}
