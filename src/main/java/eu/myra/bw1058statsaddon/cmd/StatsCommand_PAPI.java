package eu.myra.bw1058statsaddon.cmd;

import eu.myra.bw1058statsaddon.config.ConfigPath;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static eu.myra.bw1058statsaddon.utils.ConfigUtils.booleanFromPath;
import static eu.myra.bw1058statsaddon.utils.ConfigUtils.stringFromPath;

public class StatsCommand_PAPI implements CommandExecutor
{
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
                        this.noPlayer(commandSender,args[0]);
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
        final String message = stringFromPath(ConfigPath.ME_CMD_MESSAGE);

        me.sendMessage(PlaceholderAPI.setPlaceholders(me, message));
    }

    private void showOther(final @NotNull CommandSender commandSender, final @NotNull Player other)
    {
        final String message = stringFromPath(ConfigPath.OTHERS_CMD_MESSAGE);

        commandSender.sendMessage(PlaceholderAPI.setPlaceholders(other, message));
    }

    private void noPlayer(final @NotNull CommandSender commandSender, final @NotNull String player)
    {
        final String message = stringFromPath(ConfigPath.OTHERS_CMD_NOPLAYER)
                                           .replace("{player}", player)
                                           .replace("%player%", player);
        commandSender.sendMessage(message);
    }
}
