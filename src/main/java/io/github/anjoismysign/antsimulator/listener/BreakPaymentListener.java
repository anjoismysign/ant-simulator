package io.github.anjoismysign.antsimulator.listener;

import io.github.anjoismysign.antsimulator.AntSimulatorAPI;
import io.github.anjoismysign.antsimulator.asset.BreakPayment;
import io.github.anjoismysign.bloblib.api.BlobLibEconomyAPI;
import io.github.anjoismysign.bloblib.entities.message.BlobMessage;
import net.milkbowl.vault.economy.IdentityEconomy;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import org.jetbrains.annotations.Nullable;
import java.util.Objects;

public class BreakPaymentListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        Material type = block.getType();
        BlockType blockType = Objects.requireNonNull(type.asBlockType(), type.name()+" is not a BlockType");
        @Nullable BreakPayment breakPayment = AntSimulatorAPI.getInstance().getBreakPayment(blockType);
        if (breakPayment == null){
            return;
        }
        double randomAmount = breakPayment.getRandomAmount();
        Player player = event.getPlayer();
        IdentityEconomy identityEconomy = BlobLibEconomyAPI.getInstance().getElasticEconomy().getImplementation(breakPayment.currency());
        identityEconomy.depositPlayer(player, randomAmount);
        BlobMessage.by("Economy.Received-Deposit")
                .localize(player.getLocale())
                .modder()
                .replace("%display%", identityEconomy.format(randomAmount))
                .get()
                .handle(player);
    }

}
