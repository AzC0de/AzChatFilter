package us.azcode.azchatfilter.commands;

import us.azcode.azchatfilter.AzChatFilter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private final AzChatFilter plugin;

    public ReloadCommand(AzChatFilter plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("azf") && args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("azchatfilter.reload")) {
            	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("no_permission_message")));
                return true;
            }

            plugin.reloadConfig();
            plugin.reloadMessages();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("config_messages_reloaded")));
            return true;
        }
        return false;
    }
}
