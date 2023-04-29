package eu.myra.bw1058statsaddon.cmd;

import eu.myra.bw1058statsaddon.StatsAddon;
import eu.myra.bw1058statsaddon.config.ConfigPath;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static eu.myra.bw1058statsaddon.utils.ConfigUtils.stringFromPath;

public class ReloadCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(final @NotNull CommandSender commandSender, final @NotNull Command command, final @NotNull String s, final String @NotNull [] args)
    {
        if (commandSender.isOp() || commandSender.hasPermission(stringFromPath(ConfigPath.RELOAD_CMD_PERM_NODE)))
        {
            try
            {
                StatsAddon.getCfg().reload();
                commandSender.sendMessage(stringFromPath(ConfigPath.RELOAD_CMD_SUCCESS));
            }
            catch (final Exception ignored)
            {
                commandSender.sendMessage("§cAn error occurred while reloading configuration. Please check console for any error.");
            }
        }
        else
        {
            commandSender.sendMessage(stringFromPath(ConfigPath.RELOAD_CMD_NOPERMS));
        }

        return true;
    }
}
