package eu.myra.bw1058statsaddon.cmd;

import com.andrei1058.bedwars.api.BedWars;
import eu.myra.bw1058statsaddon.StatsAddon;
import eu.myra.bw1058statsaddon.config.ConfigPath;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static eu.myra.bw1058statsaddon.utils.ConfigUtils.booleanFromPath;
import static eu.myra.bw1058statsaddon.utils.ConfigUtils.stringFromPath;

public class StatsCommand_NoPAPI implements CommandExecutor
{
    private final BedWars.IStats stats;

    public StatsCommand_NoPAPI()
    {
        this.stats = StatsAddon.getApi().getStatsUtil();
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender commandSender, final @NotNull Command command, final @NotNull String s, final String @NotNull [] args)
    {
        int len = args.length;

        if (len == 0)
        {
            if (commandSender instanceof Player)
            {
                if (booleanFromPath(ConfigPath.ME_CMD_ENABLE))
                {
                    this.showMe((Player) commandSender);
                }
                else
                {
                    commandSender.sendMessage(stringFromPath(ConfigPath.DISABLED_CMD_MESSAGE));
                }
            }
            else
            {
                commandSender.sendMessage(stringFromPath(ConfigPath.ME_CMD_INGAME));
            }
        }
        else if (len == 1)
        {
            if (booleanFromPath(ConfigPath.OTHERS_CMD_ENABLE))
            {
                if (booleanFromPath(ConfigPath.OTHERS_CMD_OVERRIDES_ME))
                {
                    if (commandSender instanceof Player && booleanFromPath(ConfigPath.ME_CMD_ENABLE))
                    {
                        this.showMe((Player) commandSender);
                    }
                    else
                    {
                        commandSender.sendMessage(stringFromPath(ConfigPath.DISABLED_CMD_MESSAGE));
                    }
                }
                else
                {
                    final Player other = Bukkit.getPlayerExact(args[0]);
                    if (other == null) // TODO may be replaced by BW Api - #hasStats
                    {
                        this.noPlayer(commandSender, args[0]);
                    }
                    else
                    {
                        if (other == commandSender)
                        {
                            if (booleanFromPath(ConfigPath.ME_CMD_ENABLE))
                            {
                                this.showMe((Player) commandSender);
                            }
                            else
                            {
                                commandSender.sendMessage(stringFromPath(ConfigPath.DISABLED_CMD_MESSAGE));
                            }
                        }
                        else
                        {
                            this.showOther(commandSender, other);
                        }
                    }
                }
            }
            else
            {
                commandSender.sendMessage(stringFromPath(ConfigPath.DISABLED_CMD_MESSAGE));
            }
        }
        else
        {
            commandSender.sendMessage(stringFromPath(ConfigPath.UNKNOWN_CMD_MESSAGE));
        }
        return true;
    }

    private void showMe(final @NotNull Player me)
    {
        final UUID meUUID = me.getUniqueId();
        final String message = stringFromPath(ConfigPath.ME_CMD_MESSAGE)
                                           .replace("{wins}", String.valueOf(stats.getPlayerWins(meUUID)))
                                           .replace("{losses}", String.valueOf(stats.getPlayerLoses(meUUID)))
                                           .replace("{total_games}", String.valueOf(stats.getPlayerGamesPlayed(meUUID)))
                                           .replace("{kills}", String.valueOf(stats.getPlayerKills(meUUID)))
                                           .replace("{final_kills}", String.valueOf(stats.getPlayerFinalKills(meUUID)))
                                           .replace("{deaths}", String.valueOf(stats.getPlayerDeaths(meUUID)))
                                           .replace("{final_deaths}", String.valueOf(stats.getPlayerFinalDeaths(meUUID)))
                                           .replace("{beds_broken}", String.valueOf(stats.getPlayerBedsDestroyed(meUUID)));

        me.sendMessage(message);
    }

    private void showOther(final @NotNull CommandSender commandSender, final @NotNull Player other)
    {
        final UUID otherUUID = other.getUniqueId();
        final String message = stringFromPath(ConfigPath.OTHERS_CMD_MESSAGE)
                                           .replace("{player}", other.getName())
                                           .replace("{wins}", String.valueOf(stats.getPlayerWins(otherUUID)))
                                           .replace("{losses}", String.valueOf(stats.getPlayerLoses(otherUUID)))
                                           .replace("{total_games}", String.valueOf(stats.getPlayerGamesPlayed(otherUUID)))
                                           .replace("{kills}", String.valueOf(stats.getPlayerKills(otherUUID)))
                                           .replace("{final_kills}", String.valueOf(stats.getPlayerFinalKills(otherUUID)))
                                           .replace("{deaths}", String.valueOf(stats.getPlayerDeaths(otherUUID)))
                                           .replace("{final_deaths}", String.valueOf(stats.getPlayerFinalDeaths(otherUUID)))
                                           .replace("{beds_broken}", String.valueOf(stats.getPlayerBedsDestroyed(otherUUID)));

        commandSender.sendMessage(message);
    }

    private void noPlayer(final @NotNull CommandSender commandSender, final @NotNull String player)
    {
        final String message = stringFromPath(ConfigPath.OTHERS_CMD_NOPLAYER)
                                           .replace("{player}", player)
                                           .replace("%player%", player);
        commandSender.sendMessage(message);
    }
}
