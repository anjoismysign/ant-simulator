package io.github.anjoismysign.antsimulator.entity;

import kr.toxicity.model.api.BetterModel;
import kr.toxicity.model.api.bukkit.platform.BukkitAdapter;
import kr.toxicity.model.api.tracker.EntityTracker;
import org.bukkit.entity.Zombie;

public class ZombieProxy implements EntityProxy<Zombie> {

    private final ASMobType mobType;
    private final Zombie real;
    private final EntityTracker entityTracker;

    private String model;

    public ZombieProxy(ASMobType mobType, Zombie zombie){
        this.mobType = mobType;
        this.real = zombie;
        this.entityTracker = BetterModel.model("demon_knight")
                .map(r -> r.getOrCreate(BukkitAdapter.adapt(zombie)))
                .orElse(null);
        this.model = mobType.getDefaultModel();
    }

    @Override
    public ASMobType getMobType() {
        return mobType;
    }

    @Override
    public Zombie getEntity() {
        return real;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public void setModel(String modelName) {
        this.model = modelName;
    }
}
