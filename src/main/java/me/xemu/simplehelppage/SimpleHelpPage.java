/*
Plugin Developed & Maintained by Xemu
 */
package me.xemu.simplehelppage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleHelpPage extends JavaPlugin implements CommandExecutor, Listener
{

    protected final FileConfiguration cfg = this.getConfig();

    protected void print(final String msg)
    {
        System.out.println("[UltimateHelpPage] " + msg);
    };

    protected String translate(final String str)
    {
        return ChatColor.translateAlternateColorCodes('&', str);
    };

    protected void sendHelp(Player player)
    {
        getServer().getScheduler().runTaskAsynchronously(this, () ->
        {

            for (String string : getConfig().getStringList("HelpPage"))
            {
                player.sendMessage(translate(string));
                break;
            };

        });
    };

    @Override public void onEnable()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();

        print("Loading Event (AsyncPlayerChatEvent)");
        print("Loaded Event (AsyncPlayerChatEvent)");

        print("Replacing Command /help from Minecraft");
        print("Replaced & Loaded Commands (/help)");

        try
        {
            getCommand("help").setExecutor(this);
        } catch (NullPointerException exception) {
            print("[NullPointerException] Failed to register /help.");
        };

        try
        {
            Bukkit.getPluginManager().registerEvents(this, this);
        } catch (final NullPointerException exception) {
            print("[NullPointerException] Failed to register event AsyncPlayerChatEvent.");
        };

        print("Plugin Enabled");
    };

    @Override public void onDisable() 
    {
        print("Plugin Disabled");
    };

    @Override public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
    {
        if(sender instanceof Player)
        {

            final Player player = (Player) sender;

            this.sendHelp(player);

        } else {
            sender.sendMessage(ChatColor.RED + "Only players can execute this.");
        }
        return true;
    };

    @EventHandler (priority = EventPriority.HIGHEST) protected void onChat(final AsyncPlayerChatEvent event)
    {
        if(event.getMessage().contains(":help") && event.getMessage().startsWith("/bukkit") && event.getMessage().startsWith("/minecraft"))
        {
            event.setCancelled(true);
            sendHelp(event.getPlayer());
        };
    };
    
};
