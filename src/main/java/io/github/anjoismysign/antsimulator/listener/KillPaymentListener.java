package io.github.anjoismysign.antsimulator.listener;

import io.github.anjoismysign.antsimulator.AntSimulator;
import io.github.anjoismysign.antsimulator.AntSimulatorAPI;
import io.github.anjoismysign.antsimulator.asset.KillPayment;
import io.github.anjoismysign.bloblib.api.BlobLibEconomyAPI;
import io.github.anjoismysign.bloblib.entities.message.BlobMessage;
import io.github.anjoismysign.blobspawner.event.BlobMobDeathEvent;
import net.milkbowl.vault.economy.IdentityEconomy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;

public class KillPaymentListener implements Listener {

    @EventHandler
    public void onKill(BlobMobDeathEvent event){
        var mob = event.getBlobMob();
        var data = mob.getData();
        @Nullable KillPayment killPayment = AntSimulatorAPI.getInstance().getKillPayment(data);
        if (killPayment == null){
            return;
        }
        boolean isLegendary = mob.isLegendary();
        var underlying = mob.getMob();
        double randomAmount = killPayment.getRandomAmount(isLegendary);
        Player player = underlying.getKiller();
        IdentityEconomy identityEconomy = BlobLibEconomyAPI.getInstance().getElasticEconomy().getImplementation(killPayment.currency());
        identityEconomy.depositPlayer(player, randomAmount);
        BlobMessage.by("Economy.Received-Deposit")
                .localize(player.getLocale())
                .modder()
                .replace("%display%", identityEconomy.format(randomAmount))
                .get()
                .handle(player);
    }

}
