package io.github.anjoismysign.antsimulator;

import io.github.anjoismysign.antsimulator.asset.BreakPayment;
import io.github.anjoismysign.antsimulator.director.AntManagerDirector;
import io.github.anjoismysign.bloblib.managers.BlobPlugin;
import io.github.anjoismysign.bloblib.managers.PluginManager;
import io.github.anjoismysign.bloblib.managers.asset.BukkitIdentityManager;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.block.BlockType;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public final class AntSimulator extends BlobPlugin implements AntSimulatorAPI{

    private static AntSimulator instance;

    public static AntSimulator getInstance() {
        return instance;
    }

    private AntManagerDirector director;
    private BukkitIdentityManager<BreakPayment> breakPaymentManager;

    @Override
    public void onEnable() {
        instance = this;
        director = new AntManagerDirector(this);
        PluginManager pluginManager = PluginManager.getInstance();
        breakPaymentManager = pluginManager.addIdentityManager(BreakPayment.Info.class, this, "breakPayment", true);
    }

    @Override
    public @NotNull AntManagerDirector getManagerDirector() {
        return director;
    }

    public BukkitIdentityManager<BreakPayment> getBreakPaymentManager() {
        return breakPaymentManager;
    }

    @Override
    public @Nullable BreakPayment getBreakPayment(@NotNull BlockType blockType) {
        var registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.BLOCK);
        return breakPaymentManager.stream().filter(bp->bp.identifier().equals(blockType.getKey().toString().replace(":", "_"))).findFirst().orElse(null);
    }
}
