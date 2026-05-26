package io.github.anjoismysign.antsimulator.director.manager;

import io.github.anjoismysign.antsimulator.director.AntManager;
import io.github.anjoismysign.antsimulator.director.AntManagerDirector;
import io.github.anjoismysign.antsimulator.util.ListenerScanner;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.List;

public class AntListenerManager extends AntManager {

    public AntListenerManager(AntManagerDirector managerDirector) {
        super(managerDirector);
        var plugin = managerDirector.getPlugin();
        var pluginManager = Bukkit.getPluginManager();

        List<Listener> listeners = ListenerScanner.scan(
                plugin,
                "io.github.anjoismysign.antsimulator.listener"
        );

        boolean tinyDebug = AntConfigurationManager.getConfiguration().isTinyDebug();

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, plugin);
            if (tinyDebug) {
                plugin.getLogger().info("Registered listener: " + listener.getClass().getSimpleName());
            }
        }
    }
}