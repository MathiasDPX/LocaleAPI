# LocaleAPI

[<img src="https://mvn.coolcraft.ovh/api/badge/latest/releases/gg/gyro/LocaleAPI?color=40c14a&name=Latest release&prefix=v">](https://mvn.coolcraft.ovh/#/releases/gg/gyro/LocaleAPI/)

> [!NOTE]
> For using the very last version: Change `releases` to `snapshots` in url and set version to `SNAPSHOT`

<details>
<summary>Groovy</summary>

```groovy
maven {
    name "mathias-maven"
    url "https://mvn.coolcraft.ovh/releases"
}

implementation "gg.gyro:LocaleAPI:[version]"
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin
maven {
    name = "mathias-maven"
    url = uri("https://mvn.coolcraft.ovh/releases")
}

implementation("gg.gyro:LocaleAPI:[version]")
```

</details>

<details>
<summary>Maven</summary>

```xml
<repository>
    <id>mathias-maven</id>
    <name>Mathias's Maven Repository</name>
    <url>https://mvn.coolcraft.ovh/releases</url>
</repository>

<dependency>
  <groupId>gg.gyro</groupId>
  <artifactId>LocaleAPI</artifactId>
  <version>[version]</version>
</dependency>
```

</details>

## Summary

- [Example Usage](#example-usage)
- [JavaDoc](https://mvn.coolcraft.ovh/javadoc/releases/gg/gyro/LocaleAPI/latest)

# Example Usage

```yaml
datafolder/locales/en_us.yml
hello: "Hello World!"

datafolder/locales/fr_fr.yml
hello: "Bonjour le monde!"
```

### Main Class

```java
import gg.gyro.localeAPI.Locales;
import org.bukkit.plugin.java.JavaPlugin;

public final class LocaleTestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Saving locales to datafolder
        Locales.saveDefaultConfig(this, "fr_fr.yml");
        Locales.saveDefaultConfig(this, "en_us.yml");
        
        // Creating our Locales Manager with the plugin in parameter
        Locales locales = new Locales(this);
      
        // Printing Hello World! in 2 languages
        System.out.println(locales.get("fr_fr", "hello"));
        System.out.println(locales.get("en_us", "hello"));
    }
}
```

### Output

```
Bonjour le monde!
Hello World!
```
