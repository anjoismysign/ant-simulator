package io.github.anjoismysign.antsimulator;

import io.github.anjoismysign.antsimulator.asset.BreakPayment;
import org.bukkit.block.BlockType;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;

public interface AntSimulatorAPI {

    static AntSimulatorAPI getInstance(){
        return AntSimulator.getInstance();
    }

    @Nullable
    BreakPayment getBreakPayment(@NotNull BlockType blockType);

}
