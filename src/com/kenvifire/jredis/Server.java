package com.kenvifire.jredis;

import java.util.Calendar;

import static com.kenvifire.jredis.Constants.*;

/**
 * Created by hannahzhang on 15/4/8.
 */
public class Server {
    public static void main(String[] args) {
        RedisServer server = RedisServer.getInstance();

        server.sentinel_mode = checkForSentinelMode(args);
        initServerConfig(server);


    }

    public static boolean checkForSentinelMode(String[] args) {
        if (args[0].indexOf("redis-sentinel") > 0) return true;

        for (int j = 1; j < args.length; j++) {
            if ("--sentinel".equals(args[j])) return true;
        }
        return false;
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
        server.cluster_configfile = zstrdup(REDIS_DEFAULT_CLUSTER_CONFIG_FILE);
        server.lua_caller = null;
        server.lua_time_limit = REDIS_LUA_TIME_LIMIT;
        server.lua_client = null;
        server.lua_timedout = 0;
        server.migrate_cached_sockets = dictCreate( & migrateCacheDictType, NULL);
        server.next_client_id = 1; /* Client IDs, start from 1 .*/
        server.loading_process_events_interval_bytes = (1024 * 1024 * 2);

        server.lruclock = getLRUClock();
        resetServerSaveParams();

        appendServerSaveParams(60 * 60, 1);  /* save after 1 hour and 1 change */
        appendServerSaveParams(300, 100);  /* save after 5 minutes and 100 changes */
        appendServerSaveParams(60, 10000); /* save after 1 minute and 10000 changes */
    /* Replication related */
        server.masterauth = NULL;
        server.masterhost = NULL;
        server.masterport = 6379;
        server.master = NULL;
        server.cached_master = NULL;
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
            server.client_obuf_limits[j] = clientBufferLimitsDefaults[j];

    /* Double constants initialization */
//        R_Zero = 0.0;
//        R_PosInf = 1.0/R_Zero;
//        R_NegInf = -1.0/R_Zero;
//        R_Nan = R_Zero/R_Zero;

    /* Command table -- we initiialize it here as it is part of the
     * initial configuration, since command names may be changed via
     * redis.conf using the rename-command directive. */
        server.commands = dictCreate( & commandTableDictType, NULL);
        server.orig_commands = dictCreate( & commandTableDictType, NULL);
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


}
