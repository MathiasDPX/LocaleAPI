package gg.gyro.example;

import gg.gyro.localeAPI.Locales;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.annotation.AutoComplete;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public final class ExamplePlugin extends JavaPlugin {
    private Locales locales;

    @Override
    public void onEnable() {
        Locales.saveDefaultConfig(this, "de_de.yml");
        Locales.saveDefaultConfig(this, "en_us.yml");
        Locales.saveDefaultConfig(this, "fr_fr.yml");
        locales = new Locales(this);

        BukkitCommandHandler handler = BukkitCommandHandler.create(this);

        handler.getAutoCompleter().registerSuggestion("locales", SuggestionProvider.of(() -> locales.getLocales()));
        handler.getAutoCompleter().registerSuggestion("keys", SuggestionProvider.of(() -> locales.getKeys(locales.getDefaultLocale(), true))); // Assuming that default locale have every possible keys

        handler.register(this);
    }

    @Command("locales get")
    @AutoComplete("@locales @keys")
    public void get(CommandSender sender, String locale, String key) {
        sender.sendMessage("§6"+key + " [" + locale + "]: §r" +
                locales.get(locale, key));
    }

    @Command("locales list locales")
    public void listlocale(CommandSender sender) {
        sender.sendMessage("§6List of locales (§4"+locales.getLocales().size()+"§6)§r");

        for (String locale: locales.getLocales()) {
            sender.sendMessage("§7-§r "+locale);
        }
    }

    @Command("locales list keys")
    public void listkeys(CommandSender sender) {
        sender.sendMessage("§6List of keys (§4"+locales.getKeys(locales.getDefaultLocale(), true).size()+"§6)§r");

        for (String key: locales.getKeys(locales.getDefaultLocale(), true)) {
            sender.sendMessage("§7-§r "+key);
        }
    }
}
