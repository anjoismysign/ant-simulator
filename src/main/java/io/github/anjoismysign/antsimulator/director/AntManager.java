package io.github.anjoismysign.antsimulator.director;

import io.github.anjoismysign.antsimulator.AntSimulator;
import io.github.anjoismysign.bloblib.manager.GenericManager;

public class AntManager extends GenericManager<AntSimulator, AntManagerDirector> {
    public AntManager(AntManagerDirector managerDirector) {
        super(managerDirector);
    }
}