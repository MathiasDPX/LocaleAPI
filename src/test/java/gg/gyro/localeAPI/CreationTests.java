package gg.gyro.localeAPI;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.junit.jupiter.api.*;

import java.util.Set;

public class CreationTests {
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
    @DisplayName("Saving default locale")
    void saveInstance() {
        Assertions.assertDoesNotThrow(() -> Locales.saveDefaultConfig(plugin, "en_us.yml"));
    }

    @Test
    @DisplayName("Saving non-existent locale should throw IllegalArgumentException")
    void saveNonExistentLocaleShouldThrowException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> Locales.saveDefaultConfig(plugin, "abc_efg")
        );
    }

    @Test
    @DisplayName("Initialize should throw NullPointerException")
    void createInstanceWithoutLocales() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> Locales.initialize(plugin)
        );
    }

    @Test
    @DisplayName("Create an instance with an differente default locale")
    void createInstanceWithDifferentDefault() {
        Assertions.assertDoesNotThrow(() -> {
            Locales.saveDefaultConfig(plugin, "fr_fr.yml");
            Locales.initialize(plugin, "fr_fr");
        });
    }

    @Test
    @DisplayName("List keys")
    void listKeys() {
        Locales.saveDefaultConfig(plugin, "en_us.yml");
        Locales.initialize(plugin);
        Set<String> keys = Locales.getKeys("en_us");

        Assertions.assertEquals(1, keys.size());
    }

    @Test
    @DisplayName("List keys (deep)")
    void deeplistKeys() {
        Locales.saveDefaultConfig(plugin, "en_us.yml");
        Locales.initialize(plugin);
        Set<String> keys = Locales.getKeys("en_us", true);

        Assertions.assertEquals(1, keys.size());
    }

    @Test
    @DisplayName("Create mirror locale")
    void createMirrorLocale() {
        Assertions.assertDoesNotThrow(() -> {
            Locales.saveDefaultConfig(plugin, "de_de.yml");
            Locales.initialize(plugin, "de_de");
        });
    }

    @Test
    @DisplayName("Create Locales instance with mirror as default")
    void createLocalesWithMirrorAsDefault() {
        Assertions.assertDoesNotThrow(() -> {
            Locales.saveDefaultConfig(plugin, "de_de.yml");
            Locales.initialize(plugin, "de_at");
        });
    }
}
