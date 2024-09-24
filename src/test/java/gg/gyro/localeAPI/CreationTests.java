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
    @DisplayName("Creating an instance should throw NullPointerException")
    void createInstanceWithoutLocales() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> new Locales(plugin)
        );
    }

    @Test
    @DisplayName("Creating an instance should not throw any exceptions")
    void createInstance() {
        Assertions.assertDoesNotThrow(() -> {

            Locales.saveDefaultConfig(plugin, "en_us.yml");
            new Locales(plugin);

        });
    }

    @Test
    @DisplayName("Create an instance with an inexistant default locale should throw NullPointerException")
    void createInstanceWithInexistantDefault() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            Locales.saveDefaultConfig(plugin, "en_us.yml");
            new Locales(plugin, "fr_fr");
        });
    }

    @Test
    @DisplayName("Create an instance with an differente default locale")
    void createInstanceWithDifferentDefault() {
        Assertions.assertDoesNotThrow(() -> {
            Locales.saveDefaultConfig(plugin, "fr_fr.yml");
            new Locales(plugin, "fr_fr");
        });
    }

    @Test
    @DisplayName("List keys")
    void listKeys() {
        Locales.saveDefaultConfig(plugin, "en_us.yml");
        Locales locales = new Locales(plugin);
        Set<String> keys = locales.getKeys("en_us");

        Assertions.assertEquals(1, keys.size());
    }

    @Test
    @DisplayName("List keys (deep)")
    void deeplistKeys() {
        Locales.saveDefaultConfig(plugin, "en_us.yml");
        Locales locales = new Locales(plugin);
        Set<String> keys = locales.getKeys("en_us", true);

        Assertions.assertEquals(1, keys.size());
    }

    @Test
    @DisplayName("Access invalid singelton")
    void access_invalid_singleton() {
        Assertions.assertNull(Locales.getInstance());
    }

    @Test
    @DisplayName("Access singelton")
    void access_singleton() {
        Locales.saveDefaultConfig(plugin, "en_us.yml");
        new Locales(plugin);
        Assertions.assertSame(Locales.getInstance().getClass(), Locales.class);
    }
}
