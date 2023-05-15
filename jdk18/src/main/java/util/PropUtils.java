package util;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 读取配置类的工具类
 * @Author yxzheng
 * @Date 2020/5/14
 */
public class PropUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropUtils.class);

    /** configuration */
    private final Properties properties = new Properties();

    /**
     * Creates a new PropUtils object.
     * @param filePath
     */
    public PropUtils(final String filePath) {
        URL resource = getResource(filePath);
        if (resource == null) {
            throw new RuntimeException("Configuration file not read:["+filePath+"]");
        }
        try (InputStream inputStream = resource.openStream()) {
            properties.load(inputStream);
            System.out.println("Successfully read the configuration file:[" + resource + "]");
        } catch (final IOException e) {
            LOGGER.error("Failed to read configuration file:[" + resource + "]", e);
        }
    }


    /**
     * return configuration value by key
     * @param itemName
     * @return
     */
    public String get(final String itemName) {
        return properties.getProperty(itemName);
    }

    /**
     * return configuration value by key, return default value if property not found
     * @param itemName
     * @param defaultValue
     * @return
     */
    public String get(final String itemName, final String defaultValue) {
        return properties.getProperty(itemName, defaultValue);
    }

    /**
     * get configuration file URL
     * @param resourceName
     * @return
     */
    public static URL getResource(final String resourceName) {
        return Thread.currentThread().getContextClassLoader().getResource(resourceName);
    }
}
