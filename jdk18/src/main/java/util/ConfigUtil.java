package util;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * @author zyx
 * @date 2020/5/14
 */
public class ConfigUtil {
   private static final String DEFAULT_CONFIG_FILE = "application.properties";
   private static Properties properties = new Properties();
   private static ClassLoader classLoader;
   private static URL resource;
   static {
      classLoader = ConfigUtil.class.getClassLoader();
      if (null != classLoader) {
         resource = classLoader.getResource(DEFAULT_CONFIG_FILE);
      }
      if (null == resource) {
         try {
            Method method = Thread.class.getMethod("getContextClassLoader",
                    (Class<?>) null);
            classLoader = (ClassLoader) method.invoke(Thread
                  .currentThread(), (Object) null);
            resource = classLoader.getResource(DEFAULT_CONFIG_FILE);
         } catch (SecurityException | NoSuchMethodException | IllegalArgumentException 
			| IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
         }
      }
      URLConnection urlConnection;
      try {
         urlConnection = resource.openConnection();
         urlConnection.setUseCaches(false);
         properties.load(urlConnection.getInputStream());
      } catch (IOException e) {
		  e.printStackTrace();
         // logger.error("IOException: {}", e);
      }
   }

   public static String get(String name) {
      return properties.getProperty(name);
   }
}