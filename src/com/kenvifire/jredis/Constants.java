package com.kenvifire.jredis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hannahzhang on 15/4/8.
 */
public class Constants {


    /* Error codes */
    public static final int REDIS_OK = 0;
    public static final int REDIS_ERR = -1;

    /* Static server configuration */
    public static final int REDIS_DEFAULT_HZ = 10; /* Time interrupt calls/sec. */
    public static final int REDIS_MIN_HZ = 1;
    public static final int REDIS_MAX_HZ = 500;
    public static final int REDIS_SERVERPORT = 6379; /* TCP port */
    public static final int REDIS_TCP_BACKLOG = 511; /* TCP listen backlog */
    public static final int REDIS_MAXIDLETIME = 0; /* default client timeout: infinite */
    public static final int REDIS_DEFAULT_DBNUM = 16;
    public static final int REDIS_CONFIGLINE_MAX = 1024;
    public static final int REDIS_DBCRON_DBS_PER_CALL = 16;
    public static final int REDIS_MAX_WRITE_PER_EVENT = (1024 * 64);
    public static final int REDIS_SHARED_SELECT_CMDS = 10;
    public static final int REDIS_SHARED_INTEGERS = 10000;
    public static final int REDIS_SHARED_BULKHDR_LEN = 32;
    public static final int REDIS_MAX_LOGMSG_LEN = 1024; /* Default maximum length of syslog messages */
    public static final int REDIS_AOF_REWRITE_PERC = 100;
    public static final int REDIS_AOF_REWRITE_MIN_SIZE = (64 * 1024 * 1024);
    public static final int REDIS_AOF_REWRITE_ITEMS_PER_CMD = 64;
    public static final int REDIS_SLOWLOG_LOG_SLOWER_THAN = 10000;
    public static final int REDIS_SLOWLOG_MAX_LEN = 128;
    public static final int REDIS_MAX_CLIENTS = 10000;
    public static final int REDIS_AUTHPASS_MAX_LEN = 512;
    public static final int REDIS_DEFAULT_SLAVE_PRIORITY = 100;
    public static final int REDIS_REPL_TIMEOUT = 60;
    public static final int REDIS_REPL_PING_SLAVE_PERIOD = 10;
    public static final int REDIS_RUN_ID_SIZE = 40;
    public static final int REDIS_EOF_MARK_SIZE = 40;
    public static final int REDIS_DEFAULT_REPL_BACKLOG_SIZE = (1024 * 1024); /* 1mb */
    public static final int REDIS_DEFAULT_REPL_BACKLOG_TIME_LIMIT = (60 * 60); /* 1 hour */
    public static final int REDIS_REPL_BACKLOG_MIN_SIZE = (1024 * 16); /* 16k */
    public static final int REDIS_BGSAVE_RETRY_DELAY = 5; /* Wait a few secs before trying again. */
    public static final String REDIS_DEFAULT_PID_FILE = "/var/run/redis.pid";
    public static final String REDIS_DEFAULT_SYSLOG_IDENT = "redis";
    public static final String REDIS_DEFAULT_CLUSTER_CONFIG_FILE = "nodes.conf";
    public static final int REDIS_DEFAULT_DAEMONIZE = 0;
    public static final int REDIS_DEFAULT_UNIX_SOCKET_PERM = 0;
    public static final int REDIS_DEFAULT_TCP_KEEPALIVE = 0;
    public static final String REDIS_DEFAULT_LOGFILE = "";
    public static final int REDIS_DEFAULT_SYSLOG_ENABLED = 0;
    public static final int REDIS_DEFAULT_STOP_WRITES_ON_BGSAVE_ERROR = 1;
    public static final int REDIS_DEFAULT_RDB_COMPRESSION = 1;
    public static final int REDIS_DEFAULT_RDB_CHECKSUM = 1;
    public static final String REDIS_DEFAULT_RDB_FILENAME = "dump.rdb";
    public static final int REDIS_DEFAULT_REPL_DISKLESS_SYNC = 0;
    public static final int REDIS_DEFAULT_REPL_DISKLESS_SYNC_DELAY = 5;
    public static final int REDIS_DEFAULT_SLAVE_SERVE_STALE_DATA = 1;
    public static final int REDIS_DEFAULT_SLAVE_READ_ONLY = 1;
    public static final int REDIS_DEFAULT_REPL_DISABLE_TCP_NODELAY = 0;
    public static final int REDIS_DEFAULT_MAXMEMORY = 0;
    public static final int REDIS_DEFAULT_MAXMEMORY_SAMPLES = 5;
    public static final String REDIS_DEFAULT_AOF_FILENAME = "appendonly.aof";
    public static final int REDIS_DEFAULT_AOF_NO_FSYNC_ON_REWRITE = 0;
    public static final int REDIS_DEFAULT_AOF_LOAD_TRUNCATED = 1;
    public static final int REDIS_DEFAULT_ACTIVE_REHASHING = 1;
    public static final int REDIS_DEFAULT_AOF_REWRITE_INCREMENTAL_FSYNC = 1;
    public static final int REDIS_DEFAULT_MIN_SLAVES_TO_WRITE = 0;
    public static final int REDIS_DEFAULT_MIN_SLAVES_MAX_LAG = 10;
    public static final int REDIS_IP_STR_LEN = 46; /* INET6_ADDRSTRLEN is 46, but we need to be sure */
    public static final int REDIS_PEER_ID_LEN = (REDIS_IP_STR_LEN + 32); /* Must be enough for ip:port */
    public static final int REDIS_BINDADDR_MAX = 16;
    public static final int REDIS_MIN_RESERVED_FDS = 32;
    public static final int REDIS_DEFAULT_LATENCY_MONITOR_THRESHOLD = 0;

    public static final int ACTIVE_EXPIRE_CYCLE_LOOKUPS_PER_LOOP = 20; /* Loopkups per loop. */
    public static final int ACTIVE_EXPIRE_CYCLE_FAST_DURATION = 1000; /* Microseconds */
    public static final int ACTIVE_EXPIRE_CYCLE_SLOW_TIME_PERC = 25; /* CPU max % for keys collection */
    public static final int ACTIVE_EXPIRE_CYCLE_SLOW = 0;
    public static final int ACTIVE_EXPIRE_CYCLE_FAST = 1;

    /* Instantaneous metrics tracking. */
    public static final int REDIS_METRIC_SAMPLES = 16; /* Number of samples per metric. */
    public static final int REDIS_METRIC_COMMAND = 0; /* Number of commands executed. */
    public static final int REDIS_METRIC_NET_INPUT = 1; /* Bytes read to network .*/
    public static final int REDIS_METRIC_NET_OUTPUT = 2; /* Bytes written to network. */
    public static final int REDIS_METRIC_COUNT = 3;

    /* Protocol and I/O related defines */
    public static final int REDIS_MAX_QUERYBUF_LEN = (1024 * 1024 * 1024); /* 1GB max query buffer. */
    public static final int REDIS_IOBUF_LEN = (1024 * 16); /* Generic I/O buffer size */
    public static final int REDIS_REPLY_CHUNK_BYTES = (16 * 1024); /* 16k output buffer */
    public static final int REDIS_INLINE_MAX_SIZE = (1024 * 64); /* Max size of inline reads */
    public static final int REDIS_MBULK_BIG_ARG = (1024 * 32);
    public static final int REDIS_LONGSTR_SIZE = 21; /* Bytes needed for long -> str */
    public static final int REDIS_AOF_AUTOSYNC_BYTES = (1024 * 1024 * 32); /* fdatasync every 32MB */
    /* When configuring the Redis eventloop, we setup it so that the total number
     * of file descriptors we can handle are server.maxclients + RESERVED_FDS + FDSET_INCR
     * that is our safety margin. */
    public static final int REDIS_EVENTLOOP_FDSET_INCR = (REDIS_MIN_RESERVED_FDS + 96);

    /* Hash table parameters */
    public static final int REDIS_HT_MINFILL = 10; /* Minimal hash table fill 10% */

    /* Command flags. Please check the command table defined in the redis.c file
     * for more information about the meaning of every flag. */
    public static final int REDIS_CMD_WRITE = 1; /* "w" flag */
    public static final int REDIS_CMD_READONLY = 2; /* "r" flag */
    public static final int REDIS_CMD_DENYOOM = 4; /* "m" flag */
    public static final int REDIS_CMD_NOT_USED_1 = 8; /* no longer used flag */
    public static final int REDIS_CMD_ADMIN = 16; /* "a" flag */
    public static final int REDIS_CMD_PUBSUB = 32; /* "p" flag */
    public static final int REDIS_CMD_NOSCRIPT = 64; /* "s" flag */
    public static final int REDIS_CMD_RANDOM = 128; /* "R" flag */
    public static final int REDIS_CMD_SORT_FOR_SCRIPT = 256; /* "S" flag */
    public static final int REDIS_CMD_LOADING = 512; /* "l" flag */
    public static final int REDIS_CMD_STALE = 1024; /* "t" flag */
    public static final int REDIS_CMD_SKIP_MONITOR = 2048; /* "M" flag */
    public static final int REDIS_CMD_ASKING = 4096; /* "k" flag */
    public static final int REDIS_CMD_FAST = 8192; /* "F" flag */

    /* Object types */
    public static final int REDIS_STRING = 0;
    public static final int REDIS_LIST = 1;
    public static final int REDIS_SET = 2;
    public static final int REDIS_ZSET = 3;
    public static final int REDIS_HASH = 4;

    /* Objects encoding. Some kind of objects like Strings and Hashes can be
     * internally represented in multiple ways. The 'encoding' field of the object
     * is set to one of this fields for this object. */
    public static final int REDIS_ENCODING_RAW = 0; /* Raw representation */
    public static final int REDIS_ENCODING_INT = 1; /* Encoded as integer */
    public static final int REDIS_ENCODING_HT = 2; /* Encoded as hash table */
    public static final int REDIS_ENCODING_ZIPMAP = 3; /* Encoded as zipmap */
    public static final int REDIS_ENCODING_LINKEDLIST = 4; /* Encoded as regular linked list */
    public static final int REDIS_ENCODING_ZIPLIST = 5; /* Encoded as ziplist */
    public static final int REDIS_ENCODING_INTSET = 6; /* Encoded as intset */
    public static final int REDIS_ENCODING_SKIPLIST = 7; /* Encoded as skiplist */
    public static final int REDIS_ENCODING_EMBSTR = 8; /* Embedded sds string encoding */
    public static final int REDIS_ENCODING_QUICKLIST = 9; /* Encoded as linked list of ziplists */

    /* Defines related to the dump file format. To store 32 bits lengths for short
     * keys requires a lot of space, so we check the most significant 2 bits of
     * the first byte to interpreter the length:
     *
     * 00|000000 => if the two MSB are 00 the len is the 6 bits of this byte
     * 01|000000 00000000 =>  01, the len is 14 byes, 6 bits + 8 bits of next byte
     * 10|000000 [32 bit integer] => if it's 10, a full 32 bit len will follow
     * 11|000000 this means: specially encoded object will follow. The six bits
     *           number specify the kind of object that follows.
     *           See the REDIS_RDB_ENC_* defines.
     *
     * Lengths up to 63 are stored using a single byte, most DB keys, and may
     * values, will fit inside. */
    public static final int REDIS_RDB_6BITLEN = 0;
    public static final int REDIS_RDB_14BITLEN = 1;
    public static final int REDIS_RDB_32BITLEN = 2;
    public static final int REDIS_RDB_ENCVAL = 3;
    public static final int REDIS_RDB_LENERR = 0xffffffff;

    /* When a length of a string object stored on disk has the first two bits
     * set, the remaining two bits specify a special encoding for the object
     * accordingly to the following defines: */
    public static final int REDIS_RDB_ENC_INT8 = 0; /* 8 bit signed integer */
    public static final int REDIS_RDB_ENC_INT16 = 1; /* 16 bit signed integer */
    public static final int REDIS_RDB_ENC_INT32 = 2; /* 32 bit signed integer */
    public static final int REDIS_RDB_ENC_LZF = 3; /* string compressed with FASTLZ */

    /* AOF states */
    public static final int REDIS_AOF_OFF = 0; /* AOF is off */
    public static final int REDIS_AOF_ON = 1; /* AOF is on */
    public static final int REDIS_AOF_WAIT_REWRITE = 2; /* AOF waits rewrite to start appending */

    /* Client flags */
    public static final int REDIS_SLAVE = (1 << 0); /* This client is a slave server */
    public static final int REDIS_MASTER = (1 << 1); /* This client is a master server */
    public static final int REDIS_MONITOR = (1 << 2); /* This client is a slave monitor, see MONITOR */
    public static final int REDIS_MULTI = (1 << 3); /* This client is in a MULTI context */
    public static final int REDIS_BLOCKED = (1 << 4); /* The client is waiting in a blocking operation */
    public static final int REDIS_DIRTY_CAS = (1 << 5); /* Watched keys modified. EXEC will fail. */
    public static final int REDIS_CLOSE_AFTER_REPLY = (1 << 6); /* Close after writing entire reply. */
    public static final int REDIS_UNBLOCKED = (1 << 7); /* This client was unblocked and is stored in
                                       server.unblocked_clients */
    public static final int REDIS_LUA_CLIENT = (1 << 8); /* This is a non connected client used by Lua */
    public static final int REDIS_ASKING = (1 << 9); /* Client issued the ASKING command */
    public static final int REDIS_CLOSE_ASAP = (1 << 10);/* Close this client ASAP */
    public static final int REDIS_UNIX_SOCKET = (1 << 11); /* Client connected via Unix domain socket */
    public static final int REDIS_DIRTY_EXEC = (1 << 12); /* EXEC will fail for errors while queueing */
    public static final int REDIS_MASTER_FORCE_REPLY = (1 << 13); /* Queue replies even if is master */
    public static final int REDIS_FORCE_AOF = (1 << 14); /* Force AOF propagation of current cmd. */
    public static final int REDIS_FORCE_REPL = (1 << 15); /* Force replication of current cmd. */
    public static final int REDIS_PRE_PSYNC = (1 << 16); /* Instance don't understand PSYNC. */
    public static final int REDIS_READONLY = (1 << 17); /* Cluster client is in read-only state. */
    public static final int REDIS_PUBSUB = (1 << 18); /* Client is in Pub/Sub mode. */

    /* Client block type (btype field in client structure)
     * if REDIS_BLOCKED flag is set. */
    public static final int REDIS_BLOCKED_NONE = 0; /* Not blocked, no REDIS_BLOCKED flag set. */
    public static final int REDIS_BLOCKED_LIST = 1; /* BLPOP & co. */
    public static final int REDIS_BLOCKED_WAIT = 2; /* WAIT for synchronous replication. */

    /* Client request types */
    public static final int REDIS_REQ_INLINE = 1;
    public static final int REDIS_REQ_MULTIBULK = 2;

    /* Client classes for client limits, currently used only for
     * the max-client-output-buffer limit implementation. */
    public static final int REDIS_CLIENT_TYPE_NORMAL = 0; /* Normal req-reply clients + MONITORs */
    public static final int REDIS_CLIENT_TYPE_SLAVE = 1; /* Slaves. */
    public static final int REDIS_CLIENT_TYPE_PUBSUB = 2; /* Clients subscribed to PubSub channels. */
    public static final int REDIS_CLIENT_TYPE_COUNT = 3;

    /* Slave replication state - from the point of view of the slave. */
    public static final int REDIS_REPL_NONE = 0; /* No active replication */
    public static final int REDIS_REPL_CONNECT = 1; /* Must connect to master */
    public static final int REDIS_REPL_CONNECTING = 2; /* Connecting to master */
    public static final int REDIS_REPL_RECEIVE_PONG = 3; /* Wait for PING reply */
    public static final int REDIS_REPL_TRANSFER = 4; /* Receiving .rdb from master */
    public static final int REDIS_REPL_CONNECTED = 5; /* Connected to master */

    /* Slave replication state - from the point of view of the master.
     * In SEND_BULK and ONLINE state the slave receives new updates
     * in its output queue. In the WAIT_BGSAVE state instead the server is waiting
     * to start the next background saving in order to send updates to it. */
    public static final int REDIS_REPL_WAIT_BGSAVE_START = 6; /* We need to produce a new RDB file. */
    public static final int REDIS_REPL_WAIT_BGSAVE_END = 7; /* Waiting RDB file creation to finish. */
    public static final int REDIS_REPL_SEND_BULK = 8; /* Sending RDB file to slave. */
    public static final int REDIS_REPL_ONLINE = 9; /* RDB file transmitted, sending just updates. */

    /* Synchronous read timeout - slave side */
    public static final int REDIS_REPL_SYNCIO_TIMEOUT = 5;

    /* List related stuff */
    public static final int REDIS_HEAD = 0;
    public static final int REDIS_TAIL = 1;

    /* Sort operations */
    public static final int REDIS_SORT_GET = 0;
    public static final int REDIS_SORT_ASC = 1;
    public static final int REDIS_SORT_DESC = 2;
    public static final int REDIS_SORTKEY_MAX = 1024;

    /* Log levels */
    public static final int REDIS_DEBUG = 0;
    public static final int REDIS_VERBOSE = 1;
    public static final int REDIS_NOTICE = 2;
    public static final int REDIS_WARNING = 3;
    public static final int REDIS_LOG_RAW = (1 << 10); /* Modifier to log without timestamp */
    public static final int REDIS_DEFAULT_VERBOSITY = REDIS_NOTICE;

    /* Supervision options */
    public static final int REDIS_SUPERVISED_NONE = 0;
    public static final int REDIS_SUPERVISED_AUTODETECT = 1;
    public static final int REDIS_SUPERVISED_SYSTEMD = 2;
    public static final int REDIS_SUPERVISED_UPSTART = 3;

    /* Anti-warning macro... */


    public static final int ZSKIPLIST_MAXLEVEL = 32; /* Should be enough for 2^32 elements */
    public static final float ZSKIPLIST_P = 0.25f; /* Skiplist P = 1/4 */

    /* Append only defines */
    public static final int AOF_FSYNC_NO = 0;
    public static final int AOF_FSYNC_ALWAYS = 1;
    public static final int AOF_FSYNC_EVERYSEC = 2;
    public static final int REDIS_DEFAULT_AOF_FSYNC = AOF_FSYNC_EVERYSEC;

    /* Zip structure related defaults */
    public static final int REDIS_HASH_MAX_ZIPLIST_ENTRIES = 512;
    public static final int REDIS_HASH_MAX_ZIPLIST_VALUE = 64;
    public static final int REDIS_SET_MAX_INTSET_ENTRIES = 512;
    public static final int REDIS_ZSET_MAX_ZIPLIST_ENTRIES = 128;
    public static final int REDIS_ZSET_MAX_ZIPLIST_VALUE = 64;

    /* List defaults */
    public static final int REDIS_LIST_MAX_ZIPLIST_SIZE = -2;
    public static final int REDIS_LIST_COMPRESS_DEPTH = 0;

    /* HyperLogLog defines */
    public static final int REDIS_DEFAULT_HLL_SPARSE_MAX_BYTES = 3000;

    /* Sets operations codes */
    public static final int REDIS_OP_UNION = 0;
    public static final int REDIS_OP_DIFF = 1;
    public static final int REDIS_OP_INTER = 2;

    /* Redis maxmemory strategies */
    public static final int REDIS_MAXMEMORY_VOLATILE_LRU = 0;
    public static final int REDIS_MAXMEMORY_VOLATILE_TTL = 1;
    public static final int REDIS_MAXMEMORY_VOLATILE_RANDOM = 2;
    public static final int REDIS_MAXMEMORY_ALLKEYS_LRU = 3;
    public static final int REDIS_MAXMEMORY_ALLKEYS_RANDOM = 4;
    public static final int REDIS_MAXMEMORY_NO_EVICTION = 5;
    public static final int REDIS_DEFAULT_MAXMEMORY_POLICY = REDIS_MAXMEMORY_NO_EVICTION;

    /* Scripting */
    public static final int REDIS_LUA_TIME_LIMIT = 5000; /* milliseconds */

    /* Units */
    public static final int UNIT_SECONDS = 0;
    public static final int UNIT_MILLISECONDS = 1;

    /* SHUTDOWN flags */
    public static final int REDIS_SHUTDOWN_SAVE = 1; /* Force SAVE on SHUTDOWN even if no save 
                                            points are configured. */
    public static final int REDIS_SHUTDOWN_NOSAVE = 2; /* Don't SAVE on SHUTDOWN. */

    /* Command call flags, see call() function */
    public static final int REDIS_CALL_NONE = 0;
    public static final int REDIS_CALL_SLOWLOG = 1;
    public static final int REDIS_CALL_STATS = 2;
    public static final int REDIS_CALL_PROPAGATE = 4;
    public static final int REDIS_CALL_FULL = (REDIS_CALL_SLOWLOG |REDIS_CALL_STATS|REDIS_CALL_PROPAGATE);

    /* Command propagation flags, see propagate() function */
    public static final int REDIS_PROPAGATE_NONE = 0;
    public static final int REDIS_PROPAGATE_AOF = 1;
    public static final int REDIS_PROPAGATE_REPL = 2;

    /* RDB active child save type. */
    public static final int REDIS_RDB_CHILD_TYPE_NONE = 0;
    public static final int REDIS_RDB_CHILD_TYPE_DISK = 1; /* RDB is written to disk. */
    public static final int REDIS_RDB_CHILD_TYPE_SOCKET = 2; /* RDB is written to slave socket. */

    /* Keyspace changes notification classes. Every class is associated with a
     * character for configuration purposes. */
    public static final int REDIS_NOTIFY_KEYSPACE = (1 << 0); /* K */
    public static final int REDIS_NOTIFY_KEYEVENT = (1 << 1); /* E */
    public static final int REDIS_NOTIFY_GENERIC = (1 << 2); /* g */
    public static final int REDIS_NOTIFY_STRING = (1 << 3); /* $ */
    public static final int REDIS_NOTIFY_LIST = (1 << 4); /* l */
    public static final int REDIS_NOTIFY_SET = (1 << 5); /* s */
    public static final int REDIS_NOTIFY_HASH = (1 << 6); /* h */
    public static final int REDIS_NOTIFY_ZSET = (1 << 7); /* z */
    public static final int REDIS_NOTIFY_EXPIRED = (1 << 8); /* x */
    public static final int REDIS_NOTIFY_EVICTED = (1 << 9); /* e */
    public static final int REDIS_NOTIFY_ALL = (REDIS_NOTIFY_GENERIC
            |REDIS_NOTIFY_STRING|REDIS_NOTIFY_LIST|REDIS_NOTIFY_SET|REDIS_NOTIFY_HASH|REDIS_NOTIFY_ZSET|REDIS_NOTIFY_EXPIRED|REDIS_NOTIFY_EVICTED); /* A */


    /* ----- Cluster Related ----------*/
    public static final int REDIS_CLUSTER_SLOTS = 16384;
    public static final int REDIS_CLUSTER_OK = 0; /* Everything looks ok */
    public static final int REDIS_CLUSTER_FAIL = 1; /* The cluster can't work */
    public static final int REDIS_CLUSTER_NAMELEN = 40; /* sha1 hex length */
    public static final int REDIS_CLUSTER_PORT_INCR = 10000; /* Cluster port = baseport + PORT_INCR */

    /* The following defines are amount of time, sometimes expressed as
     * multiplicators of the node timeout value (when ending with MULT). */
    public static final int REDIS_CLUSTER_DEFAULT_NODE_TIMEOUT = 15000;
    public static final int REDIS_CLUSTER_DEFAULT_SLAVE_VALIDITY = 10; /* Slave max data age factor. */
    public static final int REDIS_CLUSTER_DEFAULT_REQUIRE_FULL_COVERAGE = 1;
    public static final int REDIS_CLUSTER_FAIL_REPORT_VALIDITY_MULT = 2; /* Fail report validity. */
    public static final int REDIS_CLUSTER_FAIL_UNDO_TIME_MULT = 2; /* Undo fail if master is back. */
    public static final int REDIS_CLUSTER_FAIL_UNDO_TIME_ADD = 10; /* Some additional time. */
    public static final int REDIS_CLUSTER_FAILOVER_DELAY = 5; /* Seconds */
    public static final int REDIS_CLUSTER_DEFAULT_MIGRATION_BARRIER = 1;
    public static final int REDIS_CLUSTER_MF_TIMEOUT = 5000; /* Milliseconds to do a manual failover. */
    public static final int REDIS_CLUSTER_MF_PAUSE_MULT = 2; /* Master pause manual failover mult. */

    /* Redirection errors returned by getNodeByQuery(). */
    public static final int REDIS_CLUSTER_REDIR_NONE = 0; /* Node can serve the request. */
    public static final int REDIS_CLUSTER_REDIR_CROSS_SLOT = 1; /* Keys in different slots. */
    public static final int REDIS_CLUSTER_REDIR_UNSTABLE = 2; /* Keys in slot resharding. */
    public static final int REDIS_CLUSTER_REDIR_ASK = 3; /* -ASK redirection required. */
    public static final int REDIS_CLUSTER_REDIR_MOVED = 4; /* -MOVED redirection required. */


    /*----------Others ------------*/
    public static final int REDIS_LRU_BITS = 24;
    public static final int REDIS_LRU_CLOCK_MAX = ((1<<REDIS_LRU_BITS)-1); /* Max value of obj->lru */
    public static final int REDIS_LRU_CLOCK_RESOLUTION = 1000; /* LRU clock resolution in ms */


    /* ----------Redis command table -------*/
    public static final List<RedisCommand> redisCommandTable = new ArrayList<RedisCommand>();
}