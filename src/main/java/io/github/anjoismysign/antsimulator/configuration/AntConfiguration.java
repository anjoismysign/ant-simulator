package io.github.anjoismysign.antsimulator.configuration;

public class AntConfiguration {
    private boolean tinyDebug;

    AntConfiguration(){}

    public boolean isTinyDebug() {
        return tinyDebug;
    }

    public void setTinyDebug(boolean tinyDebug) {
        this.tinyDebug = tinyDebug;
    }
}
