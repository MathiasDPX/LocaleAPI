package gg.gyro.localeAPI;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Main class of LocaleAPI
 */
public class Locales {
    private final HashMap<String, YamlConfiguration> locales = new HashMap<>();
    private final JavaPlugin plugin;
    private String default_locale = "en_us";

    private void init(JavaPlugin plugin){
        try {
            walkResources(plugin.getClass(), "/locales", 1, path -> {
                String localeFileName = path.getFileName().toString();
                if (!localeFileName.toLowerCase().endsWith(".yml")) return;

                if (!Files.exists(plugin.getDataFolder().toPath().resolve("locales").resolve(localeFileName))) {
                    plugin.saveResource("locales/" + localeFileName, false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            plugin.getLogger().severe("Failed to load locales from resources");
        }

        plugin.getDataFolder().mkdir();
        File folder = new File(plugin.getDataFolder(), "locales");
        folder.mkdir();
        File[] files = folder.listFiles();

        if (files == null) {
            plugin.getLogger().warning("Minor error while loading locales!");
            return;
        }

        if (files.length == 0) {
            plugin.getLogger().warning("No locales found!");
            return;
        }

        for (File file : files) {
            String filename = file.getName();
            filename = filename.contains(".") ? filename.substring(0, filename.lastIndexOf('.')) : filename;
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

            locales.put(filename, yaml);
            plugin.getLogger().info("Loaded locale "+filename);
        }
    }

    private static void walkResources(Class<?> clazz, String path, int depth, Consumer<Path> consumer) throws URISyntaxException, IOException {
        URI uri = clazz.getResource(path).toURI();
        FileSystem fileSystem = null;
        Path myPath;
        try {
            if (uri.getScheme().equals("jar")) {
                fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                myPath = fileSystem.getPath(path);
            }else {
                myPath = Paths.get(uri);
            }

            try (Stream<Path> walker = Files.walk(myPath, depth)) {
                walker.forEach(consumer);
            }
        }finally {
            if (fileSystem != null) fileSystem.close();
        }
    }

    /**
     * Create a Locales Manager
     * @param plugin JavaPlugin
     */
    public Locales(JavaPlugin plugin) {
        this.plugin = plugin;
        init(this.plugin);
    }

    /**
     * Create a Locales Manager
     * @param plugin JavaPlugin
     * @param default_locale Default locale (default: en_us)
     */
    public Locales(JavaPlugin plugin, String default_locale) {
        this.plugin = plugin;
        this.default_locale = default_locale;
        plugin.getLogger().info("Use "+default_locale+" as default locale");
        init(this.plugin);
    }

    /**
     * Save a locale from resources/locales to the DataFolder
     * @param plugin JavaPlugin
     * @param filename yml filename in resources/locales
     * @see JavaPlugin#getDataFolder()
     */
    public static void saveDefaultConfig(JavaPlugin plugin, String filename) {
        plugin.saveResource("locales/"+filename, false);
        plugin.getLogger().info("Saved default locale "+filename);
    }

    /**
     * Reload the locales
     * @param plugin JavaPlugin
     */
    public void reloadLocales(JavaPlugin plugin) {
        init(this.plugin);
    }

    /**
     * @return Set of locale
     */
    public Set<String> getLocales() {
        return locales.keySet();
    }

    private YamlConfiguration getLocale(String locale) {
        return locales.get(locale);
    }

    /**
     * @return Default locale (en_us but can be changed)
     */
    public String getDefaultLocale() {
        return default_locale;
    }

    /**
     *
     * @param locale Locale (ie. en_us)
     * @param key Key (ie. plugin_name)
     * @return Localized Key
     */
    public String get(String locale, String key) {
        YamlConfiguration lang = Objects.requireNonNullElse(getLocale(locale), getLocale(default_locale));
        try {
            return Objects.requireNonNullElse(lang.getString(key), "MISSING_PATH");
        } catch (Exception e) {
            e.printStackTrace();
            return "MISSING_PATH";
        }
    }

    /**
     * Get a Set of String containing all keys in root
     * @param locale Locale (ie. en_us)
     * @return Set of root keys
     */
    public Set<String> getKeys(String locale) {
        YamlConfiguration lang = Objects.requireNonNullElse(getLocale(locale), getLocale(default_locale));
        return lang.getKeys(false);
    }

    /**
     *
     * @param locale Locale (ie. en_us)
     * @param deep Whether or not to get a deep list, as opposed to a shallow list.
     * @return Set of keys
     */
    public Set<String> getKeys(String locale, boolean deep) {
        YamlConfiguration lang = Objects.requireNonNullElse(getLocale(locale), getLocale(default_locale));
        return lang.getKeys(deep);
    }
}
