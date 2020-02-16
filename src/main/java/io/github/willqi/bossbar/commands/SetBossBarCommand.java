package io.github.willqi.bossbar.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import cn.nukkit.utils.DummyBossBar;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.DummyBossBar.Builder;
import io.github.willqi.bossbar.BossBarManager;

import java.util.Map;

public class SetBossBarCommand extends Command {

    private final static String permission = "io.github.willqi.bossbar.command.setbossbar";

    public SetBossBarCommand () {
        super("setbossbar", "Set a boss bar for a player!", "/setbossbar <player> <text>");
        this.setPermission(SetBossBarCommand.permission);

        CommandParameter[] paramTypes = new CommandParameter[]{
            new CommandParameter("player", CommandParamType.TARGET, false),
            new CommandParameter("text", CommandParamType.RAWTEXT, false)
        };
        this.getCommandParameters().put("default", paramTypes);
        this.setPermissionMessage(TextFormat.RED + "You do not have permission to execute this command!");

    }

    public boolean execute (CommandSender sender, String label, String[] args) {

        if (!sender.hasPermission(SetBossBarCommand.permission)) {
            sender.sendMessage(this.getPermissionMessage());
            return true;
        }
        
        if (args.length < 2) {
            sender.sendMessage("Usage: " + this.getUsage());
            return true;
        }

        String bossBarText = args[1];
        for (int i = 2; i < args.length; i++) {
            bossBarText = bossBarText + " " + args[i];
        }

        if (args[0].toLowerCase().equals("@a")) {

            if (sender.isPlayer()) {
                Map<Long, Player> players = sender.getServer().getPlayer(sender.getName()).getLevel().getPlayers();
                for (Player player : players.values()) {

                    if (BossBarManager.playerHasBossBar(player)) {
                        DummyBossBar bossbar = BossBarManager.getPlayerBossBar(player);
                        bossbar.setText(bossBarText);
                    } else {
                        DummyBossBar bossBar = new Builder(player)
                            .text(bossBarText)
                            .build();
                        player.createBossBar(bossBar);
                        BossBarManager.addBossBar(player, bossBar);
                    }
                }
            } else {
                // Send it to EVERYONE
                Map<Integer, Level> levels = sender.getServer().getLevels();

                for (Level level : levels.values()) {
                    Map<Long, Player> players = level.getPlayers();
                    for (Player player : players.values()) {
                        if (BossBarManager.playerHasBossBar(player)) {
                            DummyBossBar bossbar = BossBarManager.getPlayerBossBar(player);
                            bossbar.setText(bossBarText);
                        } else {
                            DummyBossBar bossBar = new Builder(player)
                                .text(bossBarText)
                                .build();
                            player.createBossBar(bossBar);
                            BossBarManager.addBossBar(player, bossBar);
                        }
                    }
                }
            }

            sender.sendMessage(TextFormat.GREEN + "Successfully set boss bar for everyone!");

        } else {
            Player target = sender.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("Usage: " + this.getUsage());
                return true;
            }
            if (BossBarManager.playerHasBossBar(target)) {
                DummyBossBar bossbar = BossBarManager.getPlayerBossBar(target);
                bossbar.setText(bossBarText);
            } else {
                DummyBossBar bossBar = new Builder(target)
                        .text(bossBarText)
                        .build();
                target.createBossBar(bossBar);
                BossBarManager.addBossBar(target, bossBar);
            }

            sender.sendMessage(TextFormat.GREEN + "Successfully set boss bar for " + target.getName() + "!");
        }



        return true;
    }

}