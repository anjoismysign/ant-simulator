package io.github.anjoismysign.antsimulator.configuration;

public class AntConfiguration {
    private boolean tinyDebug;
    private String spawn;

    AntConfiguration(){}

    public boolean isTinyDebug() {
        return tinyDebug;
    }

    public void setTinyDebug(boolean tinyDebug) {
        this.tinyDebug = tinyDebug;
    }

    public String getSpawn() {
        return spawn;
    }

    public void setSpawn(String spawn) {
        this.spawn = spawn;
    }
}
