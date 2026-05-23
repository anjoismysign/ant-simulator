package io.github.anjoismysign.antsimulator.entity;

import java.util.Set;

public interface ASMobType {

    Set<ASMobType> getAggressiveTowards();

    Set<ASMobType> getFleeFrom();

    String getDefaultModel();

}
