package io.github.anjoismysign.antsimulator;

import io.github.anjoismysign.antsimulator.asset.BreakPayment;
import io.github.anjoismysign.antsimulator.asset.KillPayment;
import io.github.anjoismysign.blobspawner.domain.BlobMobData;
import org.bukkit.block.BlockType;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;

public interface AntSimulatorAPI {

    static AntSimulatorAPI getInstance(){
        return AntSimulator.getInstance();
    }

    @Nullable
    BreakPayment getBreakPayment(@NotNull BlockType blockType);

    @Nullable KillPayment getKillPayment(@NotNull BlobMobData mobData);

}
