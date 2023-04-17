#AzChatFilter
Chat Filter is a lightweight Spigot Plugin for Minecraft that helps server administrators filter out banned words, prevent message repetition, and manage URL sharing in chat.

## Features

- Filter forbidden words defined in the configuration file
- Censor forbidden words with asterisks (optional)
- Avoid repetition of messages within a specified time frame
- Control URL sharing by allowing only whitelisted URLs

## Installation

1. Download the plugin JAR file and place it in the 'plugins' folder of your server.
2. Restart your Minecraft server to generate the configuration files.
3. Edit the "messages.yml" and "config.yml" files to customize the behavior and messages of the plugin
4. Reload the plugin with the '/azf reload' command or restart the server for the changes to take effect.

## Commands and Permissions

"/azf reload': Reload the plugin settings and messages.
  - Permission: 'azchatfilter.reload`
- Permission: "azchatfilter.bypass"

## Setting

- config.yml: Contains options for banned words, whitelisted URLs, censorship with asterisks, and message replay duration
- message.yml: Contains customizable messages for various chat filter warnings and notifications
