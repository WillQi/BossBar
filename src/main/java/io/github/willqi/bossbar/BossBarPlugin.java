package io.github.willqi.bossbar;

import cn.nukkit.plugin.PluginBase;
import io.github.willqi.bossbar.commands.SetBossBarCommand;
import io.github.willqi.bossbar.commands.RemoveBossBarCommand;

public class BossBarPlugin extends PluginBase {

    public BossBarPlugin () {
        super();
    }

    @Override
    public void onEnable () {
        this.getServer().getCommandMap().register("setbossbar", new SetBossBarCommand());
        this.getServer().getCommandMap().register("removebossbar", new RemoveBossBarCommand());
    }
}