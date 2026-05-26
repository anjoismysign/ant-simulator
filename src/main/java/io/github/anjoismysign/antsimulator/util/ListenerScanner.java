package io.github.anjoismysign.antsimulator.util;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

public final class ListenerScanner {

    private ListenerScanner() {}

    /**
     * Scans the plugin's JAR for all concrete classes in the given package
     * that implement {@link Listener} and have a no-arg constructor, then
     * instantiates and returns them.
     *
     * @param plugin       the owning plugin (used for its ClassLoader and logger)
     * @param packageName  the package to scan, e.g. "com.example.plugin.listener"
     * @return an immutable list of ready-to-register {@link Listener} instances
     */
    public static List<Listener> scan(JavaPlugin plugin, String packageName) {
        List<Listener> result = new ArrayList<>();
        String packagePath = packageName.replace('.', '/');

        File jarFile = getJarFile(plugin);
        if (jarFile == null) {
            plugin.getLogger().warning("[ListenerScanner] Could not locate plugin JAR; falling back to empty listener list.");
            return result;
        }

        ClassLoader classLoader = plugin.getClass().getClassLoader();

        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();

                // Keep only .class files directly inside the target package (non-recursive).
                // Swap the startsWith+!contains check for a plain startsWith if you want
                // recursive scanning of sub-packages.
                if (!name.startsWith(packagePath + "/")
                        || !name.endsWith(".class")
                        || name.substring(packagePath.length() + 1).contains("/")) {
                    continue;
                }

                String className = name
                        .replace('/', '.')
                        .replace(".class", "");

                try {
                    Class<?> clazz = classLoader.loadClass(className);

                    if (!Listener.class.isAssignableFrom(clazz)) continue;
                    if (clazz.isInterface()) continue;
                    if (Modifier.isAbstract(clazz.getModifiers())) continue;

                    // Require a public no-arg constructor (mirrors Spring's default behaviour)
                    clazz.getConstructor();

                    Listener instance = (Listener) clazz.getDeclaredConstructor().newInstance();
                    result.add(instance);

                } catch (NoSuchMethodException e) {
                    plugin.getLogger().warning("[ListenerScanner] Skipping " + className
                            + ": no public no-arg constructor found.");
                } catch (Exception e) {
                    plugin.getLogger().log(Level.SEVERE,
                            "[ListenerScanner] Failed to instantiate " + className, e);
                }
            }
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "[ListenerScanner] JAR scan failed", e);
        }

        return result;
    }

    /**
     * Resolves the physical JAR file for the given plugin via its ClassLoader's
     * URL, which Paper/Bukkit always sets to the plugin's JAR path.
     */
    private static File getJarFile(JavaPlugin plugin) {
        try {
            URL location = plugin.getClass()
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation();
            return new File(location.toURI());
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING,
                    "[ListenerScanner] Could not resolve JAR via ProtectionDomain, trying ClassLoader URL", e);
        }

        // Fallback: ask the ClassLoader directly
        try {
            URL resource = plugin.getClass().getClassLoader()
                    .getResource(plugin.getClass().getName().replace('.', '/') + ".class");
            if (resource != null) {
                // URL is jar:file:/path/to/plugin.jar!/com/example/...
                String path = resource.getPath();
                String jarPath = path.substring("file:".length(), path.indexOf('!'));
                return new File(jarPath);
            }
        } catch (Exception ignored) {}

        return null;
    }
}