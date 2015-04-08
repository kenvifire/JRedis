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
    private int pid;
    private String configfile;
    private int hz;
    RedisDB db;
    Dict commands;
    Dict orig_commands;
    AeEventLoop el;
    Lock lrulock;
    int shutdown_asap;
    int activerehasing;
    String requirepass;
    String pidfile;
    int arch_bit;
    int cronloops;
    String runid;
    int sentinel_mode;

    /*Newworking*/
    int port;
    int tcp_backlog;
    List<String> bindaddr = new ArrayList<String>(Constants.REDIS_BINDADDR_MAX);
    int bindaddr_count;
    String unixsocket;
    int unixsocketperm;
    int[] ipfd = new int[Constants.REDIS_BINDADDR_MAX];
    int ipfd_count;
    int sofd;
    int[] cfd = new int[Constants.REDIS_BINDADDR_MAX];
    int cfd_count;
    List<RedisClient> redisClients = new ArrayList<RedisClient>();
    List<RedisClient> clients_to_close = new ArrayList<RedisClient>();
    List<Slaver> slavers = new ArrayList<Slaver>();
    List<Monitor> monitors = new ArrayList<Monitor>();
    RedisClient current_client;
    int clients_paused;
    long clients_pause_end_time;
    String neterror;
    Dict migrate_cached_sockets;
    long next_client_id;

    /*RDB / AOF loading information */
    boolean loading;
    long loading_total_bytes;
    long loading_loaded_bytes;
    long loading_start_time;
    long loading_process_events_interval_bytes;

    /* Fast pointers to often locked up command */
    RedisCommand delCommand,multiCommand,lpushCommand,
        lpopCommand,rpopCommand,sremCommand;

    /* Fields used only for stats */
    long stat_numcommands;
    long stat_numconnections;
    long stat_expiredkeys;
    long stat_evictedkeys;
    long stat_keyspace_hits;
    long stat_keyspace_misses;
    long stat_peak_memory;
    long stat_fork_time;
    long stat_rejected_conn;
    long stat_sync_full;
    long stat_sync_partial_ok;
    long stat_sync_partial_err;
    List<SlowLog> slowlog = new ArrayList<SlowLog>();
    long slowlog_entry_id;
    long slowlog_log_slower_than;
    long slowLong_max_len;
    long resident_set_size;
    long stat_net_input_bytes;
    long stat_net_output_bytes;

    class InstMetric{
        long last_sample_time;
        long last_sample_count;
        long[] samples = new long[Constants.REDIS_METRIC_SAMPLES];
        int idx;
    }

    InstMetric[] inst_metric = new InstMetric[Constants.REDIS_METRIC_SAMPLES];

    /* Configuration*/
    int verbosity;
    int maxidletime;
    int tcpkeepalive;
    int active_expire_enabled;
    int client_max_query_buf_len;
    int dbnum;
    int supervised;
    int supervised_mode;
    int daemonize;
    ClientBufferLimitsConfig[] client_obuf_limits =
            new ClientBufferLimitsConfig[Constants.REDIS_CLIENT_TYPE_COUNT];
    /* AOF persistence */
    int aof_state;
    int aof_fsync;
    String aof_filename;
    int aof_no_fsync_on_rewrite;
    int aof_rewrite_perc;
    long aof_rewrite_min_size;
    long aof_rewrite_base_size;
    long aof_current_size;
    int aof_rewrite_schedules;
    int aof_child_pid;
    List<AofRewriteBufBlocks> aof_rewrite_buf_blocks;
    SDS aof_buf;
    int aof_fd;
    int aof_selected_db;
    long aof_flush_postponed_start;
    long aof_last_fsync;
    long aof_rewrite_time_last;
    long aof_rewrite_time_start;
    int aof_lastbgrewrite_status;
    long aof_delayed_fsync;
    int aof_last_write_status;
    int aof_last_write_errno;
    int aof_load_truncated;

    /* AOF pipes used to communicate between parent and child during rewrite */

    int aof_pipe_write_data_to_child;
    int aof_pip_read_data_from_parent;
    int aof_pipe_write_ack_to_parent;
    int aof_pipe_read_ack_from_child;
    int aof_pipe_write_ack_to_child;
    int aof_pipe_read_ack_from_parent;
    int aof_stop_sending_diff;

    SDS aof_child_diff;

    /*RDB persistence */
    long dirty;
    long dirty_before_gbsave;
    long rdb_child_bpid;
    List<SavaParam> saveparams;
    int saveparamslen;
    String rdb_filename;
    int rdb_compression;
    int rdb_checksum;
    Date lastsave;
    Date lastgbsave_try;
    Date rdb_save_time_last;
    Date rdb_save_time_start;
    int rdb_child_type;
    int lastgbsave_status;
    int stop_writes_on_gbsave_err;
    int rdb_pip_write_result_to_parent;
    int rdb_pipe_read_result_from_child;

    /* Propagation of commands in AOF */
    List<RedisOp> also_propagate;
    /* Logging */
    String logfile;
    int syslog_enabled;
    String syslog_ident;
    int syslog_facility;
    /* Replication (master) */
    int slaveseldb;
    long master_repl_offset;
    int repl_ping_slave_period;
    String repl_backlog;
    long repl_backlog_size;
    long repl_backlog_histlen;
    long repl_backlog_idx;
    long repl_backlog_off;

    Date repl_backlog_time_limit;

    Date repl_no_slaves_since;

    int repl_min_slaves_to_write;
    int repl_min_slaves_max_lag;
    int repl_good_slaves_count;
    int repl_diskless_sync;
    int repl_diskless_sync_delay;

    /* Replication (slave) */

    String masterauth;
    String masterhost;
    int masterport;
    int repl_timeout;
    RedisClient master;
    RedisClient cached_master;
    int repl_syncio_timeout;
    int repl_state;
    long repl_transfer_size;
    long repl_transfer_read;
    long repl_transfer_last_fsync_off;
    int repl_transfer_s;
    int repl_transfer_fd;
    String repl_transfer_tmpfile;
    Date repl_transfer_lastio;
    int repl_serve_stale_data;
    int repl_slave_ro;
    Date repl_down_since;
    int repl_disable_tcp_nodelay;
    int slave_priority;
    String repl_master_runid;
    long repl_master_initial_offset;

    /* Replication script cache. */
    Dict repl_scriptcache_dict;
    List<ReplScriptCache> repl_scriptcache_fifo;
    int repl_scriptcache_size;

    /* Synchronous replication. */
    List<ClientsAwaitingAck> clients_awaiting_acks;
    int get_ack_from_slaves;
    /* Limits */
    int maxclients;
    long maxmemory;
    int maxmemory_policy;
    int maxmemory_samples;
    /* Blocked clients */
    int bpop_blocked_clients;
    List unblocked_clients;
    List ready_keys;

    /* Sort parameters - qsort_r()is only available under BSD
    so we have to take this state global, in order to pass it
    to sortCompare()
     */
    int sort_desc;
    int sort_appha;
    int sort_bypattern;
    int sort_store;

    /* Zip structure config, see redis.conf for more information */
    long hash_max_ziplist_entries;
    long hash_max_ziplist_value;
    long set_max_intset_entries;
    long zset_max_ziplist_entries;
    long zset_max_ziplist_values;
    long hll_sparse_max_bytes;

    /* List parameters */
    int list_max_ziplist_size;
    int list_compress_depth;

    /* time cache */
    Date unixtime;
    Date mstime;

    /* Pubsub */
    Dict pubsub_channels;
    List pubsub_patterns;
    int notify_keyspace_events;

    /* Cluster */
    int cluster_enabled;
    Date cluster_node_timeout;
    String cluster_configfile;
    ClusterState cluster;
    int cluster_migration_barrier;
    int cluster_slave_validity_factor;
    int cluster_require_full_coverage;

    /* Scripting */
    LuaState lua;
    RedisClient lua_client;
    RedisClient lua_caller;
    Dict lua_scripts;
    Date lua_time_limit;
    Date lua_time_start;
    int lua_write_dirty;
    int lua_random_dirty;
    int lua_timedout;
    int lua_kill;

    /* Latency monitor */
    long latency_monitor_threshold;
    Dict latency_events;

    /* Assert & bug reporting */
    String assert_failed;
    String assert_file;
    int assert_line;
    int bug_report_start;
    int watchdog_period;

    /* System hardward info */
    long system_memory_size;








}
