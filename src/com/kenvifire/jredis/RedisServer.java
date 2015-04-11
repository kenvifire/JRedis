package com.kenvifire.jredis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * Created by hannahzhang on 15/4/8.
 */
public class RedisServer {

    /*General*/
    public int pid;
    public String configfile;
    public int hz;
    public RedisDB db;
    public Dict commands;
    public Dict orig_commands;
    public AeEventLoop el;
    public Lock lrulock;
    public int shutdown_asap;
    public int activerehashing;
    public String requirepass;
    public String pidfile;
    public int arch_bit;
    public int cronloops;
    public String runid;
    public boolean sentinel_mode;

    /*Newworking*/
    public int port;
    public int tcp_backlog;
    public List<String> bindaddr = new ArrayList<String>(Constants.REDIS_BINDADDR_MAX);
    public int bindaddr_count;
    public String unixsocket;
    public int unixsocketperm;
    public int[] ipfd = new int[Constants.REDIS_BINDADDR_MAX];
    public int ipfd_count;
    public int sofd;
    public int[] cfd = new int[Constants.REDIS_BINDADDR_MAX];
    public int cfd_count;
    public List<RedisClient> redisClients = new ArrayList<RedisClient>();
    public List<RedisClient> clients_to_close = new ArrayList<RedisClient>();
    public List<Slaver> slavers = new ArrayList<Slaver>();
    public List<Monitor> monitors = new ArrayList<Monitor>();
    public RedisClient current_client;
    public int clients_paused;
    public long clients_pause_end_time;
    public String neterror;
    public Dict migrate_cached_sockets;
    public long next_client_id;

    /*RDB / AOF loading information */
    public boolean loading;
    public long loading_total_bytes;
    public long loading_loaded_bytes;
    public long loading_start_time;
    public long loading_process_events_interval_bytes;

    /* Fast pointers to often locked up command */
    public RedisCommand delCommand, multiCommand, lpushCommand,
            lpopCommand, rpopCommand, sremCommand;

    /* Fields used only for stats */
    public long stat_numcommands;
    public long stat_numconnections;
    public long stat_expiredkeys;
    public long stat_evictedkeys;
    public long stat_keyspace_hits;
    public long stat_keyspace_misses;
    public long stat_peak_memory;
    public long stat_fork_time;
    public long stat_rejected_conn;
    public long stat_sync_full;
    public long stat_sync_partial_ok;
    public long stat_sync_partial_err;
    public List<SlowLog> slowlog = new ArrayList<SlowLog>();
    public long slowlog_entry_id;
    public long slowlog_log_slower_than;
    public long slowLog_max_len;
    public long resident_set_size;
    public long stat_net_input_bytes;
    public long stat_net_output_bytes;

    class InstMetric {
        long last_sample_time;
        long last_sample_count;
        long[] samples = new long[Constants.REDIS_METRIC_SAMPLES];
        int idx;
    }

    public InstMetric[] inst_metric = new InstMetric[Constants.REDIS_METRIC_SAMPLES];

    /* Configuration*/
    public int verbosity;
    public int maxidletime;
    public int tcpkeepalive;
    public int active_expire_enabled;
    public int client_max_query_buf_len;
    public int dbnum;
    public int supervised;
    public int supervised_mode;
    public int daemonize;
    public ClientBufferLimitsConfig[] client_obuf_limits =
            new ClientBufferLimitsConfig[Constants.REDIS_CLIENT_TYPE_COUNT];
    /* AOF persistence */
    public int aof_state;
    public int aof_fsync;
    public String aof_filename;
    public int aof_no_fsync_on_rewrite;
    public int aof_rewrite_perc;
    public long aof_rewrite_min_size;
    public long aof_rewrite_base_size;
    public long aof_current_size;
    public int aof_rewrite_scheduled;
    public int aof_child_pid;
    public List<AofRewriteBufBlocks> aof_rewrite_buf_blocks;
    public SDS aof_buf;
    public int aof_fd;
    public int aof_selected_db;
    public long aof_flush_postponed_start;
    public long aof_last_fsync;
    public long aof_rewrite_time_last;
    public long aof_rewrite_time_start;
    public int aof_lastbgrewrite_status;
    public long aof_delayed_fsync;
    public int aof_last_write_status;
    public int aof_last_write_errno;
    public int aof_load_truncated;

    /* AOF pipes used to communicate between parent and child during rewrite */

    public int aof_pipe_write_data_to_child;
    public int aof_pip_read_data_from_parent;
    public int aof_pipe_write_ack_to_parent;
    public int aof_pipe_read_ack_from_child;
    public int aof_pipe_write_ack_to_child;
    public int aof_pipe_read_ack_from_parent;
    public int aof_stop_sending_diff;

    public SDS aof_child_diff;

    /*RDB persistence */
    public long dirty;
    public long dirty_before_gbsave;
    public long rdb_child_bpid;
    public List<SavaParam> saveparams;
    public int saveparamslen;
    public String rdb_filename;
    public int rdb_compression;
    public int rdb_checksum;
    public Date lastsave;
    public Date lastgbsave_try;
    public Date rdb_save_time_last;
    public Date rdb_save_time_start;
    public int rdb_child_type;
    public int lastgbsave_status;
    public int stop_writes_on_bgsave_err;
    public int rdb_pip_write_result_to_parent;
    public int rdb_pipe_read_result_from_child;

    /* Propagation of commands in AOF */
    public List<RedisOp> also_propagate;
    /* Logging */
    public String logfile;
    public int syslog_enabled;
    public String syslog_ident;
    public int syslog_facility;
    /* Replication (master) */
    public int slaveseldb;
    public long master_repl_offset;
    public int repl_ping_slave_period;
    public String repl_backlog;
    public long repl_backlog_size;
    public long repl_backlog_histlen;
    public long repl_backlog_idx;
    public long repl_backlog_off;

    public long repl_backlog_time_limit;

    public Date repl_no_slaves_since;

    public int repl_min_slaves_to_write;
    public int repl_min_slaves_max_lag;
    public int repl_good_slaves_count;
    public int repl_diskless_sync;
    public int repl_diskless_sync_delay;

    /* Replication (slave) */

    public String masterauth;
    public String masterhost;
    public int masterport;
    public int repl_timeout;
    public RedisClient master;
    public RedisClient cached_master;
    public int repl_syncio_timeout;
    public int repl_state;
    public long repl_transfer_size;
    public long repl_transfer_read;
    public long repl_transfer_last_fsync_off;
    public int repl_transfer_s;
    public int repl_transfer_fd;
    public String repl_transfer_tmpfile;
    public Date repl_transfer_lastio;
    public int repl_serve_stale_data;
    public int repl_slave_ro;
    public long repl_down_since;
    public int repl_disable_tcp_nodelay;
    public int slave_priority;
    public String repl_master_runid;
    public long repl_master_initial_offset;

    /* Replication script cache. */
    public Dict repl_scriptcache_dict;
    public List<ReplScriptCache> repl_scriptcache_fifo;
    public int repl_scriptcache_size;

    /* Synchronous replication. */
    public List<ClientsAwaitingAck> clients_awaiting_acks;
    public int get_ack_from_slaves;
    /* Limits */
    public int maxclients;
    public long maxmemory;
    public int maxmemory_policy;
    public int maxmemory_samples;
    /* Blocked clients */
    public int bpop_blocked_clients;
    public List unblocked_clients;
    public List ready_keys;

    /* Sort parameters - qsort_r()is only available under BSD
    so we have to take this state global, in order to pass it
    to sortCompare()
     */
    public int sort_desc;
    public int sort_appha;
    public int sort_bypattern;
    public int sort_store;

    /* Zip structure config, see redis.conf for more information */
    public    long hash_max_ziplist_entries;
    public    long hash_max_ziplist_value;
    public    long set_max_intset_entries;
    public    long zset_max_ziplist_entries;
    public    long zset_max_ziplist_value;
    public    long hll_sparse_max_bytes;

    /* List parameters */
    public    int list_max_ziplist_size;
    public    int list_compress_depth;

    /* time cache */
    public    Date unixtime;
    public    Date mstime;

    /* Pubsub */
    public    Dict pubsub_channels;
    public    List pubsub_patterns;
    public    int notify_keyspace_events;

    /* Cluster */
    public    int cluster_enabled;
    public    Date cluster_node_timeout;
    public    String cluster_configfile;
    public    ClusterState cluster;
    public    int cluster_migration_barrier;
    public    int cluster_slave_validity_factor;
    public    int cluster_require_full_coverage;

    /* Scripting */
    public    LuaState lua;
    public    RedisClient lua_client;
    public    RedisClient lua_caller;
    public    Dict lua_scripts;
    public    long lua_time_limit;
    public    Date lua_time_start;
    public    int lua_write_dirty;
    public    int lua_random_dirty;
    public    int lua_timedout;
    public     int lua_kill;

    /* Latency monitor */
    public long latency_monitor_threshold;
    public Dict latency_events;

    /* Assert & bug reporting */
    public    String assert_failed;
    public    String assert_file;
    public    int assert_line;
    public    int bug_report_start;
    public    int watchdog_period;

    /* System hardward info */
    public long system_memory_size;

    public static final RedisServer redisServerInstance = new RedisServer();

    private RedisServer() {

    }


    public static RedisServer getInstance() {
        return redisServerInstance;
    }


}
