package gg.gyro.localeAPI;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.junit.jupiter.api.*;

public class UsageTests {
    private LocalesPlugin plugin;

    @BeforeEach
    void setUp() {
        MockBukkit.mock();
        plugin = MockBukkit.load(LocalesPlugin.class);
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("Getting a localized key")
    void getLocalizedKey() {
        Locales.saveDefaultConfig(plugin, "en_us.yml");
        Locales locales = new Locales(plugin);
        String localized = locales.get("hello");

        Assertions.assertEquals("Hello World!", localized);
    }

    @Test
    @DisplayName("Getting a localized key (specify locale)")
    void getLocalizedKeyWithLocale() {
        Locales.saveDefaultConfig(plugin, "en_us.yml");
        Locales locales = new Locales(plugin);
        String localized = locales.get("en_us", "hello");

        Assertions.assertEquals("Hello World!", localized);
    }

    @Test
    @DisplayName("Getting a localized key with non-existent locale")
    void getLocalizedKeyWithNonExistentLocale() {
        Locales.saveDefaultConfig(plugin, "en_us.yml");
        Locales locales = new Locales(plugin);
        String localized = locales.get("fr_fr", "hello");

        Assertions.assertEquals("Hello World!", localized);
    }

    @Test
    @DisplayName("Getting a localized key with non-existent key")
    void getLocalizedKeyWithNonExistentKey() {
        Locales.saveDefaultConfig(plugin, "en_us.yml");
        Locales locales = new Locales(plugin);
        String localized = locales.get("inexistent_key");

        Assertions.assertEquals("MISSING_PATH", localized);
    }

    @Test
    @DisplayName("Getting a localized key with different default locale")
    void getLocalizedKeyWithDifferentDefaultLocale() {
        Locales.saveDefaultConfig(plugin, "fr_fr.yml");
        Locales.saveDefaultConfig(plugin, "en_us.yml");
        Locales locales = new Locales(plugin);
        String localized = locales.get("fr_fr", "hello");

        Assertions.assertEquals("Bonjour le monde!", localized);
    }

    @Test
    @DisplayName("Getting a localized key with a locale that have mirror")
    void getLocalizedKeyWithLocaleThatHaveMirror() {
        Assertions.assertDoesNotThrow(() -> {
            Locales.saveDefaultConfig(plugin, "de_de.yml");
            Locales locales = new Locales(plugin, "de_de");
            Assertions.assertEquals(locales.get("de_de", "hello"), "Hallo Welt!");
        });
    }

    @Test
    @DisplayName("Getting a localized key with a mirror")
    void getLocalizedKeyWithMirror() {
        Assertions.assertDoesNotThrow(() -> {
            Locales.saveDefaultConfig(plugin, "de_de.yml");
            Locales locales = new Locales(plugin, "de_de");
            Assertions.assertEquals(locales.get("de_at", "hello"), "Hallo Welt!");
        });
    }

    @Test
    @DisplayName("Verify default_locale")
    void verifyDefaultLocale() {
        Locales.saveDefaultConfig(plugin, "en_us.yml");
        Locales locales = new Locales(plugin);
        Assertions.assertEquals(locales.getDefaultLocale(), "en_us");
    }

    @Test
    @DisplayName("Verify default_locale (differant than default)")
    void verifyDifferentDefaultLocale() {
        Locales.saveDefaultConfig(plugin, "fr_fr.yml");
        Locales locales = new Locales(plugin, "fr_fr");
        Assertions.assertEquals(locales.getDefaultLocale(), "fr_fr");
    }
}
