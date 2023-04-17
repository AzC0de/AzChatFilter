package us.azcode.azchatfilter.listeners;

import us.azcode.azchatfilter.AzChatFilter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener implements Listener {
    private final AzChatFilter plugin;
    private final Set<String> lastMessages;
    private final Pattern urlPattern;

    public ChatListener(AzChatFilter plugin) {
        this.plugin = plugin;
        this.lastMessages = new HashSet<>();
        this.urlPattern = Pattern.compile(
                "((?:(?:http|https|Http|Https|ftp|Ftp)://|(?:www\\.|ftp\\.)[\\w_-]+\\.)" +
                "[a-zA-Z0-9-_]+(?:\\.[a-zA-Z0-9-_]+)*(?::\\d+)?(?:/\\S*)?)");
        }

        @EventHandler
        public void onChat(AsyncPlayerChatEvent event) {
            Player player = event.getPlayer();
            String message = event.getMessage();

            if (player.hasPermission("azchatfilter.bypass")) {
                return;
            }

            List<String> bannedWords = plugin.getConfig().getStringList("banned_words");
            boolean censorWithAsterisks = plugin.getConfig().getBoolean("censor_with_asterisks");

            for (String word : bannedWords) {
                Pattern pattern = Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(message);
                if (matcher.find()) {
                    if (censorWithAsterisks) {
                        message = matcher.replaceAll(getAsterisks(word.length()));
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("banned_word_message")));
                        return;
                    }
                }
            }

            if (lastMessages.contains(player.getUniqueId().toString() + message)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("repeat_message")));
                return;
            } else {
                lastMessages.add(player.getUniqueId().toString() + message);
            }

            Matcher urlMatcher = urlPattern.matcher(message);
            if (urlMatcher.find()) {
                String url = urlMatcher.group();
                List<String> whitelistedUrls = plugin.getConfig().getStringList("whitelisted_urls");
                boolean isWhitelisted = false;
                for (String whitelistedUrl : whitelistedUrls) {
                    if (url.contains(whitelistedUrl)) {
                        isWhitelisted = true;
                        break;
                    }
                }

                if (!isWhitelisted) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("url_not_allowed_message")));
                    return;
                }
            }

            event.setMessage(message); 
        }
        private String getAsterisks(int length) {
            StringBuilder asterisks = new StringBuilder();
            for (int i = 0; i < length; i++) {
                asterisks.append("*");
            }
            return asterisks.toString();
        }
}