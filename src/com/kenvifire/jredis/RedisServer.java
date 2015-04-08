package com.kenvifire.jredis;

import java.util.ArrayList;
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
    Stirng pidfile;
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





}
