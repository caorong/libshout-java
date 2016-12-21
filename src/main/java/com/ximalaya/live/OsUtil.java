package com.ximalaya.live;

/**
 * desc...
 *
 * @author caorong
 */
public class OsUtil {
  private static String os = System.getProperty("os.name").toLowerCase();
  private static String arch = System.getProperty("os.arch").toLowerCase();

  public enum OS {
    windows, mac32, mac64, linux32, linux64
  }

  public static OS detectOS() {
    if (isWindows()) {
      throw new RuntimeException(OS.windows + " is not supported!");
    } else if (isMac()) {
      if (is64()) {
        return OS.mac64;
      } else {
        throw new RuntimeException(OS.mac32 + " is not supported!");
      }
    } else if (isUnix()) {
      if (is64()) {
        return OS.linux64;
      } else {
        return OS.linux32;
      }
    } else {
      throw new RuntimeException(os + " " + arch + "not supported!");
    }
  }

  // ps, now not support windows
  private static boolean is64() {
    return arch.endsWith("64");
  }

  private static boolean isWindows() {
    return (os.indexOf("win") >= 0);
  }

  private static boolean isMac() {
    return (os.indexOf("mac") >= 0);
  }

  private static boolean isUnix() {
    return (os.indexOf("nux") >= 0);
  }
}
