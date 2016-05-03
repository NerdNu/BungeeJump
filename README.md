# BungeeJump

Command provider for functions handled by the BungeeCord proxy

## Server Switching Commands
* `/server` — Replacement for the stock /server command. Suppresses the player switch message when going to a server not listed in the "aliases" block.

* `/to-server <player> <server>` — Admin command to send a player to another server.

* `/all-to-server <server>` — Admin command to send everyone to another server. (e.g. kick to lobby)

## Permissions

* `bungeejump.admin` — Admin functions

* `bungeejump.mcbouncer.*` — All MCBouncer commands

* `bungeejump.mcbouncer.<command>` — Specific MCBouncer commands, e.g. lookup or kick


## Configuration

* `debug` — Boolean controlling diagnostic logging in console

* `mcbouncer` — Boolean controlling whether the MCBouncer commands will be enabled

* `switch-message` — Message displayed when a player switches server. `%p` will be substituted with the player name and `%s` the server string.

* `aliases` — Block of command aliases that will forward the user to another server. (e.g. */pve*). Defined in key-value format, with the key being the command and the value being BungeeCord's identifier for the server.
