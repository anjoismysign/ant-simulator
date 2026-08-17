package io.github.anjoismysign.antsimulator.director.manager;

import io.github.anjoismysign.antsimulator.director.AntManager;
import io.github.anjoismysign.antsimulator.director.AntManagerDirector;
import io.github.anjoismysign.bloblib.utility.ListenerScanner;

public class AntListenerManager extends AntManager {

    public AntListenerManager(AntManagerDirector managerDirector) {
        super(managerDirector);
        var plugin = managerDirector.getPlugin();

        ListenerScanner.scanAndRegister(plugin, "io.github.anjoismysign.antsimulator.listener", ()->AntConfigurationManager.getConfiguration().isTinyDebug());
    }
}