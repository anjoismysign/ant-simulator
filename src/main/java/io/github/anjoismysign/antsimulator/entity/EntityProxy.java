package io.github.anjoismysign.antsimulator.entity;

import org.bukkit.entity.Entity;

public interface EntityProxy<T extends Entity> {

    ASMobType getMobType();

    T getEntity();

    String getModel();

    void setModel(String modelName);

}
