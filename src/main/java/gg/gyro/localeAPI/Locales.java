package gg.gyro.localeAPI;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Main class of LocaleAPI
 */
public class Locales {
    private static Locales instance;

    private final HashMap<String, YamlConfiguration> locales = new HashMap<>();
    private final String default_locale;

    /**
     * Get the instance of Locales
     * @return Locales instance
     */
    public static Locales getInstance() {
        return instance;
    }

    /**
     * Create a Locales Manager
     * @param plugin JavaPlugin
     */
    public Locales(JavaPlugin plugin) {
        this(plugin, "en_us");
    }

    /**
     * Create a Locales Manager with a default locale
     * @param plugin JavaPlugin
     * @param default_locale Default locale (default: en_us)
     */
    public Locales(JavaPlugin plugin, String default_locale) {
        plugin.getLogger().info("Use "+default_locale+" as default locale");
        this.default_locale = default_locale;

        instance = this;
        plugin.getDataFolder().mkdir();
        File folder = new File(plugin.getDataFolder(), "locales");
        folder.mkdir();
        File[] files = folder.listFiles();

        if (files.length == 0) {
            throw new NullPointerException("No locales found!");
        }

        for (File file : files) {
            String filename = file.getName();
            filename = filename.contains(".") ? filename.substring(0, filename.lastIndexOf('.')) : filename;
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

            List<String> mirrorfor = yaml.getStringList("mirror-for");
            mirrorfor.add(filename);

            for (String lang : mirrorfor) {
                locales.put(lang, yaml);
                plugin.getLogger().info("Loaded locale " + lang);
            }
        }

        if (!locales.containsKey(default_locale)) {
            throw new NullPointerException("Unable to find default locale ("+default_locale+")");
        }
    }

    /**
     * Save a locale from resources/locales to the DataFolder
     * Update the config if it is already saved
     * @param plugin JavaPlugin
     * @param filename yml filename in resources/locales
     * @see JavaPlugin#getDataFolder()
     */
    public static void saveDefaultConfig(JavaPlugin plugin, String filename) {
        File localeFile = new File(plugin.getDataFolder(), "locales/" + filename);

        if (!localeFile.exists()) {
            plugin.saveResource("locales/" + filename, false);
            plugin.getLogger().info("Saved default locale " + filename);
        } else {
            FileConfiguration existingConfig = YamlConfiguration.loadConfiguration(localeFile);

            InputStream defaultStream = plugin.getResource("locales/" + filename);
            if (defaultStream != null) {
                FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));

                boolean updated = mergeConfigs(existingConfig, defaultConfig);

                if (updated) {
                    try {
                        existingConfig.save(localeFile);
                        plugin.getLogger().info("Updated locale "+filename);
                    } catch (Exception e) {
                        plugin.getLogger().severe("Could not save the updated locale "+filename+": "+e.getMessage());
                    }
                }
            } else {
                plugin.getLogger().severe("Default locale "+filename+" not found in the plugin resources.");
            }
        }
    }

    /**
     * Merges missing keys from the default config into the existing config
     *
     * @param existingConfig The existing configuration file
     * @param defaultConfig  The default configuration loaded from the jar
     * @return If any keys were added.
     */
    private static boolean mergeConfigs(FileConfiguration existingConfig, FileConfiguration defaultConfig) {
        boolean updated = false;

        Set<String> defaultKeys = defaultConfig.getKeys(true);

        for (String key : defaultKeys) {
            if (!existingConfig.contains(key)) {
                existingConfig.set(key, defaultConfig.get(key));
                updated = true;
            }
        }

        return updated;
    }

    /**
     * Return a Set of String containing every loaded locales
     * @return Set of locale
     */
    public Set<String> getLocales() {
        return locales.keySet();
    }

    private YamlConfiguration getLocale(String locale) {
        return locales.get(locale);
    }

    /**
     * Return the default locale
     * @return Default locale (en_us but can be changed)
     */
    public String getDefaultLocale() {
        return default_locale;
    }

    /**
     * Return a localized string
     * @param locale Locale (ie. en_us)
     * @param key Key (ie. plugin_name)
     * @return Localized Key
     */
    public String get(String locale, String key) {
        try {
            YamlConfiguration lang = Objects.requireNonNullElse(getLocale(locale), getLocale(default_locale));
            return Objects.requireNonNullElse(lang.getString(key), "MISSING_PATH");
        } catch (NullPointerException e) {
            throw new NullPointerException("Unable to find default locale ("+default_locale+")");
        }
    }

    /**
     * Return a localized string with the default locale
     * @param key Key (ie. plugin_name)
     * @return Localized Key
     */
    public String get(String key) {
        try {
            YamlConfiguration lang = getLocale(default_locale);
            return Objects.requireNonNullElse(lang.getString(key), "MISSING_PATH");
        } catch (NullPointerException e) {
            throw new NullPointerException("Unable to find default locale ("+default_locale+")");
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
     * Get a Set of String containing all path
     * @param locale Locale (ie. en_us)
     * @param deep Whether to get a deep list, as opposed to a shallow list.
     * @return Set of keys
     */
    public Set<String> getKeys(String locale, boolean deep) {
        YamlConfiguration lang = Objects.requireNonNullElse(getLocale(locale), getLocale(default_locale));
        return lang.getKeys(deep);
    }
}