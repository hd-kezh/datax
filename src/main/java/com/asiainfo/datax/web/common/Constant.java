package com.asiainfo.datax.web.common;

public class Constant
{
    public static final Integer TASK_SUCCESS_STATE = Integer.valueOf(1);
    public static final Integer TASK_FAIL_STATE = Integer.valueOf(2);
    public static final Integer TASK_RUNNING_STATE = Integer.valueOf(3);
    public static final Integer TASK_MANUAL_STATE = Integer.valueOf(4);
    public static final Integer TASK_INIT_STATE = Integer.valueOf(0);
    public static final String TASK_START_TYPE_NOW = "1";
    public static final String TASK_START_TYPE_DELAY = "2";
    public static final String TASK_START_TYPE_TIMING = "3";
    public static final String TASK_REMARK_SPECIAL = "2";
    public static final String SHELL_PATH = "/home/2ipoc1/dataxETL/shell";
    public static final String STOP_TASK_SHELL = "/home/2ipoc1/dataxETL/shell/task_stop.sh";
    public static final String START_TASK_SHELL = "/home/2ipoc1/dataxETL/shell/task_entry.sh";
    public static final String SUB_START_TASK_SHELL = "/home/2ipoc1/dataxETL/shell/task_start.sh";
    public static final String SPEED_TASK_SHELL = "/home/2ipoc1/dataxETL/shell/task_speed.sh";
    public static final String TIMMING_TASK_SHELL = "/home/2ipoc1/dataxETL/shell/task_timing.sh";
    public static final String TASK_LOG_PATH = "/home/2ipoc1/dataxETL/logs";
    public static final String TASK_START_LOG = "/home/2ipoc1/dataxETC/logs/start";
}
