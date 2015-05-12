package com.kenvifire.jredis;

import sun.jvm.hotspot.utilities.Assert;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import static com.kenvifire.jredis.Constants.*;
import static com.kenvifire.jredis.RedisCommandProc.*;
import static com.kenvifire.jredis.RedisGetKeysProc.*;

/**
 * Created by hannahzhang on 15/4/8.
 */
public class Server {
    public static void main(String[] args) {
        RedisServer server = RedisServer.getInstance();

        server.sentinel_mode = checkForSentinelMode(args);
        initServerConfig(server);

        if(server.sentinel_mode){
            SentinelState sentinel = SentinelState.getSentinelState();
            initSentinelConfig();
            initSentinel();
        }

        if(args.length >= 1) {
            int j = 1;
            String options = "";
            String configfile = null;

            if ("-v".equals(args[0])
                    || "--version".equals(args[1]))
                version();

            if ("--help".equals(args[0]) || "-h".equals(args[0])) {
                usage();
            }
            if ("--test-memory".equals(args[9])) {
                if (args.length == 2) {
                    throw new RedisRuntimeException("Unsupport operation");
                } else {
                    System.err.printf("Please specify the amount of memory to test in megabytes.\n");
                    System.err.printf("Example: ./redis-server --test-memory 4096\n\n");
                    System.exit(1);
                }

            }

            /* First arguments is the config file name? */

            if(args[j].charAt(0) != '-' || args[j].charAt(1) != '-'){
                configfile = args[j++];
            }

            while (j != args.length){
                if(args[j].charAt(0) == '-' && args[j].charAt(1) == '1'){
                    if(options.length() > 0) {
                        options = options + '\n';
                    }
                    options = options + args[j].substring(2);
                    options = options + " ";
                }else{
                    options
                }
            }

        }



    }

    public static boolean checkForSentinelMode(String[] args) {
        if (args[0].indexOf("redis-sentinel") > 0) return true;

        for (int j = 1; j < args.length; j++) {
            if ("--sentinel".equals(args[j])) return true;
        }
        return false;
    }

    static void version(){
        System.out.println(String.format("Redis server v=%s sha=%s:%d malloc=%s bits=%d build=%x\n",
                RedisVersion.REDIS_VERSION,Release.redisGitSHA1(),
                Integer.valueOf(Release.redisGitDirty().trim()),
                "libc",32,Release.redisBuildId()));
        System.exit(0);
    }

    public static void usage(){
        System.err.printf("Usage: ./redis-server [/path/to/redis.conf] [options]\n");
        System.err.printf("       ./redis-server - (read config from stdin)\n");
        System.err.printf("       ./redis-server -v or --version\n");
        System.err.printf("       ./redis-server -h or --help\n");
        System.err.printf("       ./redis-server --test-memory <megabytes>\n\n");
        System.err.printf("Examples:\n");
        System.err.printf("       ./redis-server (run the server with default conf)\n");
        System.err.printf("       ./redis-server /etc/redis/6379.conf\n");
        System.err.printf("       ./redis-server --port 7777\n");
        System.err.printf("       ./redis-server --port 7777 --slaveof 127.0.0.1 8888\n");
        System.err.printf("       ./redis-server /etc/myredis.conf --loglevel verbose\n\n");
        System.err.printf("Sentinel mode:\n");
        System.err.printf("       ./redis-server /etc/sentinel.conf --sentinel\n");
        System.exit(1);
    }

    public static void initServerConfig(RedisServer server) {
        int j;

        //getRandomHexChars(server.runid,REDIS_RUN_ID_SIZE);
        server.runid = Utils.getRandHexChars(REDIS_RUN_ID_SIZE);
        server.configfile = null;
        server.hz = REDIS_DEFAULT_HZ;
        //server.runid[REDIS_RUN_ID_SIZE] = '\0';
        //server.arch_bits = (sizeof(long) == 8) ? 64 : 32;
        server.port = REDIS_SERVERPORT;
        server.tcp_backlog = REDIS_TCP_BACKLOG;
        server.bindaddr_count = 0;
        server.unixsocket = null;
        server.unixsocketperm = REDIS_DEFAULT_UNIX_SOCKET_PERM;
        server.ipfd_count = 0;
        server.sofd = -1;
        server.dbnum = REDIS_DEFAULT_DBNUM;
        server.verbosity = REDIS_DEFAULT_VERBOSITY;
        server.maxidletime = REDIS_MAXIDLETIME;
        server.tcpkeepalive = REDIS_DEFAULT_TCP_KEEPALIVE;
        server.active_expire_enabled = 1;
        server.client_max_query_buf_len = REDIS_MAX_QUERYBUF_LEN;
        server.saveparams = null;
        server.loading = false;
        server.logfile = new String(REDIS_DEFAULT_LOGFILE);
        server.syslog_enabled = REDIS_DEFAULT_SYSLOG_ENABLED;
        server.syslog_ident = new String(REDIS_DEFAULT_SYSLOG_IDENT);
        //server.syslog_facility = LOG_LOCAL0;
        server.syslog_facility = 16<<3;
        server.daemonize = REDIS_DEFAULT_DAEMONIZE;
        server.supervised = 0;
        server.supervised_mode = REDIS_SUPERVISED_NONE;
        server.aof_state = REDIS_AOF_OFF;
        server.aof_fsync = REDIS_DEFAULT_AOF_FSYNC;
        server.aof_no_fsync_on_rewrite = REDIS_DEFAULT_AOF_NO_FSYNC_ON_REWRITE;
        server.aof_rewrite_perc = REDIS_AOF_REWRITE_PERC;
        server.aof_rewrite_min_size = REDIS_AOF_REWRITE_MIN_SIZE;
        server.aof_rewrite_base_size = 0;
        server.aof_rewrite_scheduled = 0;
        server.aof_last_fsync = Calendar.getInstance().getTimeInMillis();
        server.aof_rewrite_time_last = -1;
        server.aof_rewrite_time_start = -1;
        server.aof_lastbgrewrite_status = REDIS_OK;
        server.aof_delayed_fsync = 0;
        server.aof_fd = -1;
        server.aof_selected_db = -1; /* Make sure the first time will not match */
        server.aof_flush_postponed_start = 0;
        server.aof_rewrite_incremental_fsync = REDIS_DEFAULT_AOF_REWRITE_INCREMENTAL_FSYNC;
        server.aof_load_truncated = REDIS_DEFAULT_AOF_LOAD_TRUNCATED;
        server.pidfile = null;
        server.rdb_filename = new String(REDIS_DEFAULT_RDB_FILENAME);
        server.aof_filename = new String(REDIS_DEFAULT_AOF_FILENAME);
        server.requirepass = null;
        server.rdb_compression = REDIS_DEFAULT_RDB_COMPRESSION;
        server.rdb_checksum = REDIS_DEFAULT_RDB_CHECKSUM;
        server.stop_writes_on_bgsave_err = REDIS_DEFAULT_STOP_WRITES_ON_BGSAVE_ERROR;
        server.activerehashing = REDIS_DEFAULT_ACTIVE_REHASHING;
        server.notify_keyspace_events = 0;
        server.maxclients = REDIS_MAX_CLIENTS;
        server.bpop_blocked_clients = 0;
        server.maxmemory = REDIS_DEFAULT_MAXMEMORY;
        server.maxmemory_policy = REDIS_DEFAULT_MAXMEMORY_POLICY;
        server.maxmemory_samples = REDIS_DEFAULT_MAXMEMORY_SAMPLES;
        server.hash_max_ziplist_entries = REDIS_HASH_MAX_ZIPLIST_ENTRIES;
        server.hash_max_ziplist_value = REDIS_HASH_MAX_ZIPLIST_VALUE;
        server.list_max_ziplist_size = REDIS_LIST_MAX_ZIPLIST_SIZE;
        server.list_compress_depth = REDIS_LIST_COMPRESS_DEPTH;
        server.set_max_intset_entries = REDIS_SET_MAX_INTSET_ENTRIES;
        server.zset_max_ziplist_entries = REDIS_ZSET_MAX_ZIPLIST_ENTRIES;
        server.zset_max_ziplist_value = REDIS_ZSET_MAX_ZIPLIST_VALUE;
        server.hll_sparse_max_bytes = REDIS_DEFAULT_HLL_SPARSE_MAX_BYTES;
        server.shutdown_asap = 0;
        server.repl_ping_slave_period = REDIS_REPL_PING_SLAVE_PERIOD;
        server.repl_timeout = REDIS_REPL_TIMEOUT;
        server.repl_min_slaves_to_write = REDIS_DEFAULT_MIN_SLAVES_TO_WRITE;
        server.repl_min_slaves_max_lag = REDIS_DEFAULT_MIN_SLAVES_MAX_LAG;
        server.cluster_enabled = 0;
        server.cluster_node_timeout = REDIS_CLUSTER_DEFAULT_NODE_TIMEOUT;
        server.cluster_migration_barrier = REDIS_CLUSTER_DEFAULT_MIGRATION_BARRIER;
        server.cluster_slave_validity_factor = REDIS_CLUSTER_DEFAULT_SLAVE_VALIDITY;
        server.cluster_require_full_coverage = REDIS_CLUSTER_DEFAULT_REQUIRE_FULL_COVERAGE;
        server.cluster_configfile = new String(REDIS_DEFAULT_CLUSTER_CONFIG_FILE);
        server.lua_caller = null;
        server.lua_time_limit = REDIS_LUA_TIME_LIMIT;
        server.lua_client = null;
        server.lua_timedout = 0;
        server.migrate_cached_sockets = Dict.dictCreate(new MigrateCacheDictType(), null);
        server.next_client_id = 1; /* Client IDs, start from 1 .*/
        server.loading_process_events_interval_bytes = (1024 * 1024 * 2);

        server.lruclock = getLRUClock();
        server.saveparams = null;
        server.saveparamslen = 0;

        server.appendServerSaveParams(60 * 60, 1);  /* save after 1 hour and 1 change */
        server.appendServerSaveParams(300, 100);  /* save after 5 minutes and 100 changes */
        server.appendServerSaveParams(60, 10000); /* save after 1 minute and 10000 changes */
    /* Replication related */
        server.masterauth = null;
        server.masterhost = null;
        server.masterport = 6379;
        server.master = null;
        server.cached_master = null;
        server.repl_master_initial_offset = -1;
        server.repl_state = REDIS_REPL_NONE;
        server.repl_syncio_timeout = REDIS_REPL_SYNCIO_TIMEOUT;
        server.repl_serve_stale_data = REDIS_DEFAULT_SLAVE_SERVE_STALE_DATA;
        server.repl_slave_ro = REDIS_DEFAULT_SLAVE_READ_ONLY;
        server.repl_down_since = 0; /* Never connected, repl is down since EVER. */
        server.repl_disable_tcp_nodelay = REDIS_DEFAULT_REPL_DISABLE_TCP_NODELAY;
        server.repl_diskless_sync = REDIS_DEFAULT_REPL_DISKLESS_SYNC;
        server.repl_diskless_sync_delay = REDIS_DEFAULT_REPL_DISKLESS_SYNC_DELAY;
        server.slave_priority = REDIS_DEFAULT_SLAVE_PRIORITY;
        server.master_repl_offset = 0;

    /* Replication partial resync backlog */
        server.repl_backlog = null;
        server.repl_backlog_size = REDIS_DEFAULT_REPL_BACKLOG_SIZE;
        server.repl_backlog_histlen = 0;
        server.repl_backlog_idx = 0;
        server.repl_backlog_off = 0;
        server.repl_backlog_time_limit = REDIS_DEFAULT_REPL_BACKLOG_TIME_LIMIT;
        server.repl_no_slaves_since = Calendar.getInstance().getTimeInMillis();

    /* Client output buffer limits */
        for (j = 0; j < REDIS_CLIENT_TYPE_COUNT; j++)
            server.client_obuf_limits[j] = Config.clientBufferLimitsDefaults[j];

    /* Double constants initialization */
//        R_Zero = 0.0;
//        R_PosInf = 1.0/R_Zero;
//        R_NegInf = -1.0/R_Zero;
//        R_Nan = R_Zero/R_Zero;

    /* Command table -- we initiialize it here as it is part of the
     * initial configuration, since command names may be changed via
     * redis.conf using the rename-command directive. */
        server.commands = Dict.dictCreate(new CommandTableDictType(), null);
        server.orig_commands = Dict.dictCreate(new CommandTableDictType(), null);
        populateCommandTable();
        server.delCommand = lookupCommandByCString("del");
        server.multiCommand = lookupCommandByCString("multi");
        server.lpushCommand = lookupCommandByCString("lpush");
        server.lpopCommand = lookupCommandByCString("lpop");
        server.rpopCommand = lookupCommandByCString("rpop");

    /* Slow log */
        server.slowlog_log_slower_than = REDIS_SLOWLOG_LOG_SLOWER_THAN;
        server.slowLog_max_len = REDIS_SLOWLOG_MAX_LEN;

    /* Latency monitor */
        server.latency_monitor_threshold = REDIS_DEFAULT_LATENCY_MONITOR_THRESHOLD;

    /* Debugging */
        server.assert_failed = "<no assertion failed>";
        server.assert_file = "<no file>";
        server.assert_line = 0;
        server.bug_report_start = 0;
        server.watchdog_period = 0;

    }

    public static int getLRUClock() {
        return (int)(Calendar.getInstance().getTimeInMillis()/Long.valueOf(REDIS_LRU_CLOCK_RESOLUTION)) & REDIS_LRU_CLOCK_MAX;
    }

    static void populateCommandTable() {
        int j;

        for (j = 0; j < redisCommandTable.size(); j++) {
            RedisCommand c = redisCommandTable.get(j);
            String f = c.getSflags();
            int retval1, retval2;
            int i =0;
            while(i < f.length()) {
                switch(f.charAt(i)) {
                    case 'w': c.setFlag(c.getFlag() | REDIS_CMD_WRITE ); break;
                    case 'r': c.setFlag(c.getFlag()| REDIS_CMD_READONLY); break;
                    case 'm': c.setFlag(c.getFlag()| REDIS_CMD_DENYOOM); break;
                    case 'a': c.setFlag(c.getFlag()| REDIS_CMD_ADMIN); break;
                    case 'p': c.setFlag(c.getFlag()| REDIS_CMD_PUBSUB); break;
                    case 's': c.setFlag(c.getFlag()| REDIS_CMD_NOSCRIPT); break;
                    case 'R': c.setFlag(c.getFlag()| REDIS_CMD_RANDOM); break;
                    case 'S': c.setFlag(c.getFlag()| REDIS_CMD_SORT_FOR_SCRIPT); break;
                    case 'l': c.setFlag(c.getFlag()| REDIS_CMD_LOADING); break;
                    case 't': c.setFlag(c.getFlag()| REDIS_CMD_STALE); break;
                    case 'M': c.setFlag(c.getFlag()| REDIS_CMD_SKIP_MONITOR); break;
                    case 'k': c.setFlag(c.getFlag()| REDIS_CMD_ASKING); break;
                    case 'F': c.setFlag(c.getFlag()| REDIS_CMD_FAST); break;
                    default: throw new RedisRuntimeException("Unsupported command flag");
                }
                i++;
            }

            retval1 = RedisServer.getInstance().commands.dictAdd(c.getName(),c);
            //dictAdd(server.commands, sdsnew(c->name), c);
        /* Populate an additional dictionary that will be unaffected
         * by rename-command statements in redis.conf. */
            retval2 = RedisServer.getInstance().orig_commands.dictAdd(c.getName(),c);
            // dictAdd(server.orig_commands, sdsnew(c->name), c);
            boolean result = (retval1 == Dict.DICT_OK) && (retval2 == Dict.DICT_OK);
        }
    }
    static  RedisCommand lookupCommandByCString(String s){
        return (RedisCommand)RedisServer.getInstance().commands.dictFetchValue(s);
    }

    static void initSentinelConfig(){
        RedisServer.getInstance().port = Sentinel.REDIS_SENTINEL_PORT;


    }

    static void initSentinel(){
        int j;

        RedisServer.getInstance().commands.dictEmpty(null);

        for(int i =0; i< Sentinel.sentinelcmds.size(); i++){
            RedisCommand cmd = Sentinel.sentinelcmds.get(i);
            RedisServer.getInstance().commands.dictAdd(cmd.getName(),cmd);
        }

        SentinelState sentinel = SentinelState.getSentinelState();
        sentinel.current_epoch = 0;
        sentinel.masters = Dict.dictCreate(InstancesDictType.getInstancesDictType(),null);
        sentinel.tilt = 0;
        sentinel.titl_start_time = 0;
        sentinel.previous_time = System.currentTimeMillis();
        sentinel.running_scripts = 0;
        sentinel.scripts_queue = new ArrayList();
        sentinel.announce_ip = null;
        sentinel.announce_port = 0;



    }

    private static List<RedisCommand> redisCommandTable = new ArrayList<RedisCommand>();

    static {

        redisCommandTable.add(new RedisCommand("get",getCommand,2,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("set",setCommand,-3,"wm",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("setnx",setnxCommand,3,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("setex",setexCommand,4,"wm",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("psetex",psetexCommand,4,"wm",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("append",appendCommand,3,"wm",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("strlen",strlenCommand,2,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("del",delCommand,-2,"w",0,null,1,-1,1,0,0));
        redisCommandTable.add(new RedisCommand("exists",existsCommand,2,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("setbit",setbitCommand,4,"wm",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("getbit",getbitCommand,3,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("setrange",setrangeCommand,4,"wm",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("getrange",getrangeCommand,4,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("substr",getrangeCommand,4,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("incr",incrCommand,2,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("decr",decrCommand,2,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("mget",mgetCommand,-2,"r",0,null,1,-1,1,0,0));
        redisCommandTable.add(new RedisCommand("rpush",rpushCommand,-3,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("lpush",lpushCommand,-3,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("rpushx",rpushxCommand,3,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("lpushx",lpushxCommand,3,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("linsert",linsertCommand,5,"wm",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("rpop",rpopCommand,2,"wF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("lpop",lpopCommand,2,"wF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("brpop",brpopCommand,-3,"ws",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("brpoplpush",brpoplpushCommand,4,"wms",0,null,1,2,1,0,0));
        redisCommandTable.add(new RedisCommand("blpop",blpopCommand,-3,"ws",0,null,1,-2,1,0,0));
        redisCommandTable.add(new RedisCommand("llen",llenCommand,2,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("lindex",lindexCommand,3,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("lset",lsetCommand,4,"wm",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("lrange",lrangeCommand,4,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("ltrim",ltrimCommand,4,"w",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("lrem",lremCommand,4,"w",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("rpoplpush",rpoplpushCommand,3,"wm",0,null,1,2,1,0,0));
        redisCommandTable.add(new RedisCommand("sadd",saddCommand,-3,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("srem",sremCommand,-3,"wF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("smove",smoveCommand,4,"wF",0,null,1,2,1,0,0));
        redisCommandTable.add(new RedisCommand("sismember",sismemberCommand,3,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("scard",scardCommand,2,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("spop",spopCommand,-2,"wRsF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("srandmember",srandmemberCommand,-2,"rR",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("sinter",sinterCommand,-2,"rS",0,null,1,-1,1,0,0));
        redisCommandTable.add(new RedisCommand("sinterstore",sinterstoreCommand,-3,"wm",0,null,1,-1,1,0,0));
        redisCommandTable.add(new RedisCommand("sunion",sunionCommand,-2,"rS",0,null,1,-1,1,0,0));
        redisCommandTable.add(new RedisCommand("sunionstore",sunionstoreCommand,-3,"wm",0,null,1,-1,1,0,0));
        redisCommandTable.add(new RedisCommand("sdiff",sdiffCommand,-2,"rS",0,null,1,-1,1,0,0));
        redisCommandTable.add(new RedisCommand("sdiffstore",sdiffstoreCommand,-3,"wm",0,null,1,-1,1,0,0));
        redisCommandTable.add(new RedisCommand("smembers",sinterCommand,2,"rS",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("sscan",sscanCommand,-3,"rR",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zadd",zaddCommand,-4,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zincrby",zincrbyCommand,4,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zrem",zremCommand,-3,"wF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zremrangebyscore",zremrangebyscoreCommand,4,"w",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zremrangebyrank",zremrangebyrankCommand,4,"w",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zremrangebylex",zremrangebylexCommand,4,"w",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zunionstore",zunionstoreCommand,-4,"wm",0,zunionInterGetKeys,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("zinterstore",zinterstoreCommand,-4,"wm",0,zunionInterGetKeys,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("zrange",zrangeCommand,-4,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zrangebyscore",zrangebyscoreCommand,-4,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zrevrangebyscore",zrevrangebyscoreCommand,-4,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zrangebylex",zrangebylexCommand,-4,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zrevrangebylex",zrevrangebylexCommand,-4,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zcount",zcountCommand,4,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zlexcount",zlexcountCommand,4,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zrevrange",zrevrangeCommand,-4,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zcard",zcardCommand,2,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zscore",zscoreCommand,3,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zrank",zrankCommand,3,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zrevrank",zrevrankCommand,3,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("zscan",zscanCommand,-3,"rR",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("hset",hsetCommand,4,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("hsetnx",hsetnxCommand,4,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("hget",hgetCommand,3,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("hmset",hmsetCommand,-4,"wm",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("hmget",hmgetCommand,-3,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("hincrby",hincrbyCommand,4,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("hincrbyfloat",hincrbyfloatCommand,4,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("hdel",hdelCommand,-3,"wF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("hlen",hlenCommand,2,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("hkeys",hkeysCommand,2,"rS",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("hvals",hvalsCommand,2,"rS",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("hgetall",hgetallCommand,2,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("hexists",hexistsCommand,3,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("hscan",hscanCommand,-3,"rR",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("incrby",incrbyCommand,3,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("decrby",decrbyCommand,3,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("incrbyfloat",incrbyfloatCommand,3,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("getset",getsetCommand,3,"wm",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("mset",msetCommand,-3,"wm",0,null,1,-1,2,0,0));
        redisCommandTable.add(new RedisCommand("msetnx",msetnxCommand,-3,"wm",0,null,1,-1,2,0,0));
        redisCommandTable.add(new RedisCommand("randomkey",randomkeyCommand,1,"rR",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("select",selectCommand,2,"rlF",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("move",moveCommand,3,"wF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("rename",renameCommand,3,"w",0,null,1,2,1,0,0));
        redisCommandTable.add(new RedisCommand("renamenx",renamenxCommand,3,"wF",0,null,1,2,1,0,0));
        redisCommandTable.add(new RedisCommand("expire",expireCommand,3,"wF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("expireat",expireatCommand,3,"wF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("pexpire",pexpireCommand,3,"wF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("pexpireat",pexpireatCommand,3,"wF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("keys",keysCommand,2,"rS",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("scan",scanCommand,-2,"rR",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("dbsize",dbsizeCommand,1,"rF",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("auth",authCommand,2,"rsltF",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("ping",pingCommand,-1,"rtF",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("echo",echoCommand,2,"rF",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("save",saveCommand,1,"ars",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("bgsave",bgsaveCommand,1,"ar",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("bgrewriteaof",bgrewriteaofCommand,1,"ar",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("shutdown",shutdownCommand,-1,"arlt",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("lastsave",lastsaveCommand,1,"rRF",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("type",typeCommand,2,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("multi",multiCommand,1,"rsF",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("exec",execCommand,1,"sM",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("discard",discardCommand,1,"rsF",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("sync",syncCommand,1,"ars",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("psync",syncCommand,3,"ars",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("replconf",replconfCommand,-1,"arslt",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("flushdb",flushdbCommand,1,"w",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("flushall",flushallCommand,1,"w",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("sort",sortCommand,-2,"wm",0,sortGetKeys,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("info",infoCommand,-1,"rlt",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("monitor",monitorCommand,1,"ars",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("ttl",ttlCommand,2,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("pttl",pttlCommand,2,"rF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("persist",persistCommand,2,"wF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("slaveof",slaveofCommand,3,"ast",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("role",roleCommand,1,"lst",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("debug",debugCommand,-2,"as",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("config",configCommand,-2,"art",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("subscribe",subscribeCommand,-2,"rpslt",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("unsubscribe",unsubscribeCommand,-1,"rpslt",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("psubscribe",psubscribeCommand,-2,"rpslt",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("punsubscribe",punsubscribeCommand,-1,"rpslt",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("publish",publishCommand,3,"pltrF",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("pubsub",pubsubCommand,-2,"pltrR",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("watch",watchCommand,-2,"rsF",0,null,1,-1,1,0,0));
        redisCommandTable.add(new RedisCommand("unwatch",unwatchCommand,1,"rsF",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("cluster",clusterCommand,-2,"ar",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("restore",restoreCommand,-4,"wm",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("restore-asking",restoreCommand,-4,"wmk",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("migrate",migrateCommand,-6,"w",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("asking",askingCommand,1,"r",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("readonly",readonlyCommand,1,"rF",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("readwrite",readwriteCommand,1,"rF",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("dump",dumpCommand,2,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("object",objectCommand,3,"r",0,null,2,2,2,0,0));
        redisCommandTable.add(new RedisCommand("client",clientCommand,-2,"rs",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("eval",evalCommand,-3,"s",0,evalGetKeys,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("evalsha",evalShaCommand,-3,"s",0,evalGetKeys,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("slowlog",slowlogCommand,-2,"r",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("script",scriptCommand,-2,"rs",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("time",timeCommand,1,"rRF",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("bitop",bitopCommand,-4,"wm",0,null,2,-1,1,0,0));
        redisCommandTable.add(new RedisCommand("bitcount",bitcountCommand,-2,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("bitpos",bitposCommand,-3,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("wait",waitCommand,3,"rs",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("command",commandCommand,0,"rlt",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("pfselftest",pfselftestCommand,1,"r",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("pfadd",pfaddCommand,-2,"wmF",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("pfcount",pfcountCommand,-2,"r",0,null,1,1,1,0,0));
        redisCommandTable.add(new RedisCommand("pfmerge",pfmergeCommand,-2,"wm",0,null,1,-1,1,0,0));
        redisCommandTable.add(new RedisCommand("pfdebug",pfdebugCommand,-3,"w",0,null,0,0,0,0,0));
        redisCommandTable.add(new RedisCommand("latency",latencyCommand,-2,"arslt",0,null,0,0,0,0,0));
    }



}
