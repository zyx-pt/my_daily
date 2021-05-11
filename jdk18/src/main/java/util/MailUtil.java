package util;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * 使用类加载器获取配置文件
 * @Author zhengyongxian
 * @Date 2020/5/14
 */
public class MailUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropUtils.class);
    /**
     * 如果文件多一层目录，MAIL_CONFIG_FILE = "/mail.properties"
      */
    private static final String MAIL_CONFIG_FILE = "mail.properties";
    private static Properties properties = new Properties();
    private static ClassLoader classLoader;
    private static URL resource;
    static {
//        classLoader = MailUtil.class.getClassLoader();
//        if (null != classLoader) {
//            resource = classLoader.getResource(MAIL_CONFIG_FILE);
//        }
//        if (null == resource) {
//            try {
//                Method method = Thread.class.getMethod("getContextClassLoader",
//                        null);
//                classLoader = (ClassLoader) method.invoke(Thread
//                        .currentThread(), null);
//                resource = classLoader.getResource(MAIL_CONFIG_FILE);
//            } catch (SecurityException | NoSuchMethodException | IllegalArgumentException
//                    | IllegalAccessException | InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
        // 不知到为什么要这么复杂来获取，直接用下面的不是更简单？
        resource = getResource(MAIL_CONFIG_FILE);
        if (resource == null) {
            throw new RuntimeException("Configuration file not read:["+MAIL_CONFIG_FILE+"]");
        }
        URLConnection urlConnection;
        try {
            urlConnection = resource.openConnection();
            urlConnection.setUseCaches(false);
            properties.load(urlConnection.getInputStream());
            System.out.println("Successfully read the configuration file:[" + resource + "]");
        } catch (IOException e) {
            LOGGER.error("Failed to read configuration file:[" + resource + "]", e);
        }
    }

    public static String get(String name) {
        return properties.getProperty(name);
    }

    public static URL getResource(final String resourceName) {
        return Thread.currentThread().getContextClassLoader().getResource(resourceName);
    }
}
