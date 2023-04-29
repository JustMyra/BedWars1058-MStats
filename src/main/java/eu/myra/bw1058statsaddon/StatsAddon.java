package eu.myra.bw1058statsaddon;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import eu.myra.bw1058statsaddon.cmd.ReloadCommand;
import eu.myra.bw1058statsaddon.cmd.StatsCommand_NoPAPI;
import eu.myra.bw1058statsaddon.cmd.StatsCommand_PAPI;
import eu.myra.bw1058statsaddon.config.ConfigPath;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class StatsAddon extends JavaPlugin
{
    private static BedWars api;

    private static ConfigManager cfg;

    private boolean loaded = false;

    @Override
    public void onEnable()
    {
        // Check if bw plugin is loaded
        if (Bukkit.getPluginManager().getPlugin("BedWars1058") == null)
        {
            this.getLogger().severe("I can't run without BedWars1058 Plugin!");
            this.setEnabled(false);
            return;
        }

        try
        {
            Class.forName("com.andrei1058.bedwars.api.BedWars");
        }
        catch (final Exception ex)
        {
            this.getLogger().severe("Your BedWars1058 version is outdated. Please download the latest version!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        api = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();

        // Setup configuration
        cfg = new ConfigManager(this, "config", "plugins/BedWars1058/Addons/MStats");

        setupConfiguration();

        this.loaded = true;

        final CommandExecutor statsExecutor;

        // Register commands
        if (cfg.getBoolean(ConfigPath.PAPI_ENABLED))
        {
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)
            {
                this.getLogger().severe("Conflict found: You have PlaceholderAPI enabled on config but you don't have the plugin installed. So, PlaceholderAPI support has NOT been loaded.");
                statsExecutor = new StatsCommand_NoPAPI();
            }
            else
            {
                statsExecutor = new StatsCommand_PAPI();
            }
        }
        else
        {
            statsExecutor = new StatsCommand_NoPAPI();
        }

        Bukkit.getPluginCommand("stats").setExecutor(statsExecutor);
        Bukkit.getPluginCommand("statsreload").setExecutor(new ReloadCommand());
    }

    @Override
    public void onDisable()
    {
        if (this.loaded)
        {
            this.getLogger().info("Thank you for using our add-ons! <3");
        }
    }

    private static void setupConfiguration()
    {
        final YamlConfiguration yml = cfg.getYml();
        yml.options().header("This is a BedWars1058 mini-game add-on by https://github.com/JustMyra\n" +
                             "Please note, 'using_placeholder_api' requires server restart for changes to be applied.\n" +
                             "Also, placeholders are currently supported only by 'commands.stats.me.message' and 'commands.stats.others.message'.");

        yml.addDefault(ConfigPath.PAPI_ENABLED, false);

        yml.addDefault(ConfigPath.ME_CMD_ENABLE, true);
        yml.addDefault(ConfigPath.ME_CMD_MESSAGE,
                    "§bShowing your stats\n" +
                    "§bKills: §e{kills}\n" +
                    "§bFinal Kills: §e{final_kills}\n" +
                    "§bDeaths: §e{deaths}\n" +
                    "§bFinal Deaths: §e{final_deaths}\n" +
                    "§bWins: §e{wins}\n" +
                    "§bLosses: §e{losses}\n" +
                    "§bTotal Games: §e{total_games}\n" +
                    "§bBeds Destroyed: §e{beds_broken}\n" +
                    "§b|--- your.server.com ---|");
        yml.addDefault(ConfigPath.ME_CMD_INGAME, "§cThis command can be used only ingame. Use /stats <player> instead.");
        yml.addDefault(ConfigPath.OTHERS_CMD_ENABLE, true);
        yml.addDefault(ConfigPath.OTHERS_CMD_OVERRIDES_ME, false);
        yml.addDefault(ConfigPath.OTHERS_CMD_MESSAGE,
                    "§bStats for player §e{player}\n" +
                    "§bKills: §e{kills}\n" +
                    "§bFinal Kills: §e{final_kills}\n" +
                    "§bDeaths: §e{deaths}\n" +
                    "§bFinal Deaths: §e{final_deaths}\n" +
                    "§bWins: §e{wins}\n" +
                    "§bLosses: §e{losses}\n" +
                    "§bTotal Games: §e{total_games}\n" +
                    "§bBeds Destroyed: §e{beds_broken}\n" +
                    "§b|--- your.server.com ---|");
        yml.addDefault(ConfigPath.OTHERS_CMD_NOPLAYER, "§bPlayer §e{player} §bis not online.");
        yml.addDefault(ConfigPath.RELOAD_CMD_PERM_NODE, "bw.mstats.reload");
        yml.addDefault(ConfigPath.RELOAD_CMD_SUCCESS, "§aConfiguration reloaded successfully.");
        yml.addDefault(ConfigPath.RELOAD_CMD_NOPERMS, "§cYou don't have enough permissions to run this command.");
        yml.addDefault(ConfigPath.DISABLED_CMD_MESSAGE, "§cThis command is disabled.");
        yml.addDefault(ConfigPath.UNKNOWN_CMD_MESSAGE, "§cUnknown command or wrong syntax.");

        yml.options().copyDefaults(true);
        cfg.save();
    }

    public static ConfigManager getCfg()
    {
        return cfg;
    }

    public static BedWars getApi()
    {
        return api;
    }
}
