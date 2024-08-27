# LocaleAPI
<img src="https://mvn.coolcraft.ovh/api/badge/latest/releases/gg/gyro/LocaleAPI?color=40c14a&name=Latest release&prefix=v">

<details>
<summary>Groovy</summary>

```groovy
maven {
    name "mathiasRepo"
    url "https://mvn.coolcraft.ovh/releases"
}

implementation "gg.gyro:LocaleAPI:[version]"
```
</details>

<details>
<summary>Kotlin</summary>

```kotlin
maven {
    name = "mathiasRepo"
    url = uri("https://mvn.coolcraft.ovh/releases")
}

implementation("gg.gyro:LocaleAPI:[version]")
```
</details>

<details>
<summary>Maven</summary>

```xml
<repository>
    <id>mathias-repo</id>
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
- [JavaDoc](https://mvn.coolcraft.ovh)

# Example Usage
```yaml
ourdatafolder/locales/fr_fr.yml
hello: "Hello World!"

ourdatafolder/locales/en_us.yml
hello: "Bonjour le monde!"
```

### Main Class
```java
import gg.gyro.localeAPI.Locales;
import org.bukkit.plugin.java.JavaPlugin;

public final class LocaleTestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Manually create the DataFolder because we don't have config.yml
        this.getDataFolder().mkdir();
        
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
Hello World!
Bonjour le monde!
```