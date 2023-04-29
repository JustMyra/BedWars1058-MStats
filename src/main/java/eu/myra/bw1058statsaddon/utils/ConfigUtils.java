package eu.myra.bw1058statsaddon.utils;

import eu.myra.bw1058statsaddon.StatsAddon;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ConfigUtils
{
    @Contract("_ -> new")
    public static @NotNull String stringFromPath(final @NotNull String cfgPath)
    {
        return colorize(StatsAddon.getCfg().getString(cfgPath));
    }

    public static boolean booleanFromPath(@NotNull String cfgPath)
    {
        return StatsAddon.getCfg().getBoolean(cfgPath);
    }

    @Contract("_ -> new")
    private static @NotNull String colorize(final @NotNull String string)
    {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
