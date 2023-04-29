package eu.myra.bw1058statsaddon.config;

public class ConfigPath
{
    private static final String MAIN_PATH = "StatsAddon";
    private static final String MAIN_CONFIG_PATH = MAIN_PATH + ".config";
    public static String PAPI_ENABLED = MAIN_CONFIG_PATH + ".using_placeholder_api";
    private static final String MAIN_CMD_PATH = MAIN_PATH + ".commands";
    private static final String STATS_CMD_PATH = MAIN_CMD_PATH + ".stats";
    private static final String ME_CMD_PATH = STATS_CMD_PATH + ".me";
    public static String ME_CMD_ENABLE = ME_CMD_PATH + ".enabled";
    public static String ME_CMD_MESSAGE = ME_CMD_PATH + ".message";
    public static String ME_CMD_INGAME = ME_CMD_PATH + ".ingame_only";
    private static final String OTHERS_CMD_PATH = STATS_CMD_PATH + ".others";
    public static String OTHERS_CMD_ENABLE = OTHERS_CMD_PATH + ".enabled";
    public static String OTHERS_CMD_OVERRIDES_ME = OTHERS_CMD_PATH + ".shows_me_instead";
    public static String OTHERS_CMD_MESSAGE = OTHERS_CMD_PATH + ".message";
    public static String OTHERS_CMD_NOPLAYER = OTHERS_CMD_PATH + ".player_not_found";
    public static String UNKNOWN_CMD_MESSAGE = MAIN_CMD_PATH + ".error_message";
    public static String DISABLED_CMD_MESSAGE = MAIN_CMD_PATH + ".disabled_message";
    private static final String RELOAD_CMD_PATH = MAIN_CMD_PATH + ".stats_reload";
    public static String RELOAD_CMD_PERM_NODE = RELOAD_CMD_PATH + ".permission_node";
    public static String RELOAD_CMD_SUCCESS = RELOAD_CMD_PATH + ".success";
    public static String RELOAD_CMD_NOPERMS = RELOAD_CMD_PATH + ".no_perms";
}
