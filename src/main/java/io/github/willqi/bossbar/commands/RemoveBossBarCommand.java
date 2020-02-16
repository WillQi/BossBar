package io.github.willqi.bossbar.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import cn.nukkit.utils.DummyBossBar;
import cn.nukkit.utils.TextFormat;
import io.github.willqi.bossbar.BossBarManager;

import java.util.Map;

public class RemoveBossBarCommand extends Command {

    private static final String permission = "io.github.willqi.bossbar.command.removebossbar";

    public RemoveBossBarCommand () {
        super("removebossbar", "Remove a bossbar for a player!", "/removebossbar <player>");
        this.setPermission(RemoveBossBarCommand.permission);

        CommandParameter[] paramTypes = new CommandParameter[]{
            new CommandParameter("player", CommandParamType.TARGET, false)
        };
        this.getCommandParameters().put("default", paramTypes);

        this.setPermissionMessage(TextFormat.RED + "You do not have permission to execute this command!");

    }

    public boolean execute (CommandSender sender, String label, String[] args) {

        if (!sender.hasPermission(RemoveBossBarCommand.permission)) {
            sender.sendMessage(this.getPermissionMessage());
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("Usage: " + this.getUsage());
            return true;
        }

        if (args[0].toLowerCase().equals("@a")) {
            if (sender.isPlayer()) {
                // Only for the level that user is on.

                Map<Long, Player> players = sender.getServer().getPlayer(sender.getName()).getLevel().getPlayers();
                for (Player player : players.values()) {

                    if (BossBarManager.playerHasBossBar(player)) {
                        BossBarManager.removeBossBar(player);
                    }
                }

            } else {
                // All levels.

                Map<Integer, Level> levels = sender.getServer().getLevels();

                for (Level level : levels.values()) {
                    Map<Long, Player> players = level.getPlayers();
                    for (Player player : players.values()) {
                        if (BossBarManager.playerHasBossBar(player)) {
                            BossBarManager.removeBossBar(player);
                        }
                    }
                }

            }
            sender.sendMessage(TextFormat.GREEN + "Removed boss bar for everyone!");
        } else {
            Player target = sender.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("Usage: " + this.getUsage());
                return true;
            }

            if (!BossBarManager.playerHasBossBar(target)) {
                sender.sendMessage(target.getName() + " does not have any active boss bar!");
                return true;
            }
            BossBarManager.removeBossBar(target);
            sender.sendMessage(TextFormat.GREEN + "Removed boss bar for " + target.getName() + "!");
        }

        return true;
    }

}