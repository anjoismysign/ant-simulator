package io.github.anjoismysign.antsimulator.director;

import io.github.anjoismysign.antsimulator.AntSimulator;
import io.github.anjoismysign.antsimulator.director.manager.AntConfigurationManager;
import io.github.anjoismysign.antsimulator.director.manager.AntListenerManager;
import io.github.anjoismysign.bloblib.entities.GenericManagerDirector;
import org.jetbrains.annotations.NotNull;

public class AntManagerDirector extends GenericManagerDirector<AntSimulator> {
    public AntManagerDirector(AntSimulator plugin) {
        super(plugin);
        addManager("ConfigurationManager",
                new AntConfigurationManager(this));
        addManager("ListenerManager",
                new AntListenerManager(this));
    }

    /**
     * From top to bottom, follow the order.
     */
    @Override
    public void reload() {
        getConfigurationManager().reload();
    }

    @Override
    public void unload() {
    }

    @NotNull
    public final AntConfigurationManager getConfigurationManager() {
        return getManager("ConfigurationManager", AntConfigurationManager.class);
    }
}
