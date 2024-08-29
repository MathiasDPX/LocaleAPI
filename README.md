# LocaleAPI

<img src="https://mvn.coolcraft.ovh/api/badge/latest/releases/gg/gyro/LocaleAPI?color=40c14a&name=Latest release&prefix=v">

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
- [Saving default locales](#create-default-locales)
- [JavaDoc](https://mvn.coolcraft.ovh/javadoc/releases/gg/gyro/LocaleAPI/latest)

# Example Usage

```yaml
ourdatafolder/locales/en_us.yml
hello: "Hello World!"

ourdatafolder/locales/fr_fr.yml
hello: "Bonjour le monde!"
```

### Main Class

```java
import gg.gyro.localeAPI.Locales;
import org.bukkit.plugin.java.JavaPlugin;

public final class LocaleTestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {      
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

# Create default locales

You may want to create default locale for your users, This is completly possible with LocaleAPI

The function saveDefaultConfig() while save your locale file from resources/locales/xx_yy.yml to the plugin DataFolder, in one simple line of code
- `this` is your plugin Main class
- `en_us.yml` is the file you want to save
```java
saveDefaultConfig(this, "en_us.yml");

// You need to run this BEFORE creating your Locales class
```
