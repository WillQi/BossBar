package io.github.willqi.bossbar;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import io.github.willqi.bossbar.commands.SetBossBarCommand;
import io.github.willqi.bossbar.commands.RemoveBossBarCommand;

public class BossBarPlugin extends PluginBase implements Listener {

    public BossBarPlugin () {
        super();
    }

    @Override
    public void onEnable () {
        this.getServer().getCommandMap().register("setbossbar", new SetBossBarCommand());
        this.getServer().getCommandMap().register("removebossbar", new RemoveBossBarCommand());

        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerLeave (PlayerQuitEvent event) {

        if (BossBarManager.playerHasBossBar(event.getPlayer())) {
            BossBarManager.removeBossBar(event.getPlayer());
        }

    }
}