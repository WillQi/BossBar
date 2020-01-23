package io.github.willqi.bossbar.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import io.github.willqi.bossbar.BossBarManager;

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

        return true;
    }

}