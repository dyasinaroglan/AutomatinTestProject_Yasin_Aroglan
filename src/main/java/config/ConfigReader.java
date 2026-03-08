package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        loadProperties("configuration.properties");
        String env = System.getProperty("env");
        if (env != null && !env.isEmpty()) {
            loadProperties("config-" + env + ".properties");
        }
    }

    /*
    Static initializer, sınıf JVM'e ilk yüklendiğinde bir kez çalışan kod bloğudur.
    Config dosyası okuma, database connection pool oluşturma gibi bir kez yapılması gereken pahalı işlemler için kullanılır.
    Her metod çağrısında tekrar çalışmaz
     */

    private static void loadProperties(String fileName) {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream(fileName)) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Config dosyası okunamadı: " + fileName);
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Config key bulunamadı: " + key);
        }
        return value.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
