package io.github.anjoismysign.antsimulator;

import io.github.anjoismysign.antsimulator.asset.BreakPayment;
import io.github.anjoismysign.antsimulator.asset.KillPayment;
import io.github.anjoismysign.antsimulator.director.AntManagerDirector;
import io.github.anjoismysign.bloblib.managers.BlobPlugin;
import io.github.anjoismysign.bloblib.managers.PluginManager;
import io.github.anjoismysign.bloblib.managers.asset.BukkitIdentityManager;
import io.github.anjoismysign.blobspawner.domain.BlobMobData;
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
    private BukkitIdentityManager<KillPayment> killPaymentManager;

    @Override
    public void onEnable() {
        instance = this;
        director = new AntManagerDirector(this);
        PluginManager pluginManager = PluginManager.getInstance();
        breakPaymentManager = pluginManager.addIdentityManager(BreakPayment.Info.class, this, "breakPayment", true);
        killPaymentManager = pluginManager.addIdentityManager(KillPayment.Info.class, this, "killPayment", true);
    }

    @Override
    public @NotNull AntManagerDirector getManagerDirector() {
        return director;
    }

    public BukkitIdentityManager<BreakPayment> getBreakPaymentManager() {
        return breakPaymentManager;
    }

    public BukkitIdentityManager<KillPayment> getKillPaymentManager() {
        return killPaymentManager;
    }

    @Override
    public @Nullable BreakPayment getBreakPayment(@NotNull BlockType blockType) {
        return breakPaymentManager.stream().filter(bp->bp.identifier().equals(blockType.getKey().toString().replace(":", "_"))).findFirst().orElse(null);
    }

    @Override
    public @Nullable KillPayment getKillPayment(@NotNull BlobMobData mobData){
        return killPaymentManager.stream().filter(kp -> kp.identifier().equals(mobData.identifier())).findFirst().orElse(null);
    }
}
