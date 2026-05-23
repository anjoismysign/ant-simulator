package io.github.anjoismysign.antsimulator;

import io.github.anjoismysign.bloblib.managers.BlobPlugin;
import io.github.anjoismysign.bloblib.managers.IManagerDirector;
import org.jetbrains.annotations.NotNull;

public final class AntSimulator extends BlobPlugin implements AntSimulatorAPI{

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
    }

    @Override
    public @NotNull IManagerDirector getManagerDirector() {
        return null;
    }
}
