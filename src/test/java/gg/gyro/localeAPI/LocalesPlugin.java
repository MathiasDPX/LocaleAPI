package gg.gyro.localeAPI;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class LocalesPlugin extends JavaPlugin {
    protected LocalesPlugin(
            JavaPluginLoader loader,
            PluginDescriptionFile description,
            File dataFolder,
            File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}
}
