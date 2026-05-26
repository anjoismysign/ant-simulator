package io.github.anjoismysign.antsimulator.director.manager;

import io.github.anjoismysign.antsimulator.AntSimulator;
import io.github.anjoismysign.antsimulator.configuration.AntConfiguration;
import io.github.anjoismysign.antsimulator.director.AntManager;
import io.github.anjoismysign.antsimulator.director.AntManagerDirector;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AntConfigurationManager extends AntManager {
    private static AntConfiguration configuration;

    public AntConfigurationManager(AntManagerDirector managerDirector) {
        super(managerDirector);
        reload();
    }

    @Override
    public void reload() {
        AntSimulator plugin = getPlugin();
        plugin.saveResource("config.yml", false);
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        Constructor constructor = new Constructor(AntConfiguration.class, new LoaderOptions());
        Yaml yaml = new Yaml(constructor);
        try (FileInputStream inputStream = new FileInputStream(configFile)) {
            configuration = yaml.load(inputStream);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @NotNull
    public static AntConfiguration getConfiguration() {
        return configuration;
    }
}