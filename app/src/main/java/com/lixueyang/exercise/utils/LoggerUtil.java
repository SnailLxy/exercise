package com.lixueyang.exercise.utils;

import android.util.Log;


import com.lixueyang.exercise.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 日志类
 */
public class LoggerUtil {

  public enum LOGGER_DEPTH {
    ACTUAL_METHOD(4),
    LOGGER_METHOD(3),
    STACK_TRACE_METHOD(1),
    JVM_METHOD(0);

    private final int value;

    LOGGER_DEPTH(final int newValue) {
      value = newValue;
    }

    public int getValue() {
      return value;
    }
  }

  private static final int JSON_INDENT = 4;

  private static final int MAX_LENGTH_OF_SINGLE_MESSAGE = 4063;

  private static String personalTag = "Logger";

  private StringBuilder sb;

  private RemoteLogger remoteLogger;

  private LoggerUtil() {
    if (LoggerLoader.instance != null) {
      Log.e(personalTag, "Error: Logger already instantiated");
      throw new IllegalStateException("Already Instantiated");
    } else {

      this.sb = new StringBuilder(255);
    }
  }

  public static LoggerUtil getLogger() {
    return LoggerLoader.instance;
  }

  public void setRemoteLogger(RemoteLogger remoteLogger) {
    this.remoteLogger = remoteLogger;
  }

  public static void setPersonalTag(String personalTag) {
    LoggerUtil.personalTag = personalTag;
  }

  private String getTag(LOGGER_DEPTH depth) {
    try {
      StackTraceElement element = Thread.currentThread().getStackTrace()[depth.getValue()];
      String className = element.getClassName();
      sb.append(personalTag)
          .append(":")
          .append(className.substring(className.lastIndexOf(".") + 1))
          .append("[")
          .append(element.getMethodName())
          .append("] - ")
          .append(element.getLineNumber());

      return sb.toString();
    } catch (Exception ex) {
      ex.printStackTrace();
      Log.d(personalTag, ex.getMessage());
    } finally {
      sb.setLength(0);
    }
    return null;
  }

  public void json(String msg) {
    try {
      printJson(Log.DEBUG, getTag(LOGGER_DEPTH.ACTUAL_METHOD), msg);
    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }


  public void d(String msg) {
    try {
      print(Log.DEBUG, getTag(LOGGER_DEPTH.ACTUAL_METHOD), msg, null);
    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void d(String msg, LOGGER_DEPTH depth) {
    try {
      print(Log.DEBUG, getTag(depth), msg, null);
    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void d(String msg, Throwable t, LOGGER_DEPTH depth) {
    try {
      print(Log.DEBUG, getTag(depth), msg, t);
    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void remote(String msg) {
    try {
      if (remoteLogger != null) {
        remoteLogger.remote(getTag(LOGGER_DEPTH.ACTUAL_METHOD) + " ：" + msg);
      }
      e(msg);
    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void e(String msg) {
    try {
      print(Log.ERROR, getTag(LOGGER_DEPTH.ACTUAL_METHOD), msg, null);

    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void e(String msg, LOGGER_DEPTH depth) {
    try {
      print(Log.ERROR, getTag(depth), msg, null);

    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void e(String msg, Throwable t, LOGGER_DEPTH depth) {
    try {
      print(Log.ERROR, getTag(depth), msg, t);

    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void w(String msg) {
    try {
      print(Log.WARN, getTag(LOGGER_DEPTH.ACTUAL_METHOD), msg, null);

    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void w(String msg, LOGGER_DEPTH depth) {
    try {
      print(Log.WARN, getTag(depth), msg, null);

    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void w(String msg, Throwable t, LOGGER_DEPTH depth) {
    try {
      print(Log.WARN, getTag(depth), msg, t);

    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void v(String msg) {
    try {
      print(Log.VERBOSE, getTag(LOGGER_DEPTH.ACTUAL_METHOD), msg, null);

    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void v(String msg, LOGGER_DEPTH depth) {
    try {
      print(Log.VERBOSE, getTag(depth), msg, null);

    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void v(String msg, Throwable t, LOGGER_DEPTH depth) {
    try {
      print(Log.VERBOSE, getTag(depth), msg, t);

    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void i(String msg) {
    try {
      print(Log.INFO, getTag(LOGGER_DEPTH.ACTUAL_METHOD), msg, null);

    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void i(String msg, LOGGER_DEPTH depth) {
    try {
      print(Log.INFO, getTag(depth), msg, null);

    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }

  public void i(String msg, Throwable t, LOGGER_DEPTH depth) {
    try {
      print(Log.INFO, getTag(depth), msg, t);

    } catch (Exception exception) {
      Log.e(getTag(LOGGER_DEPTH.ACTUAL_METHOD), "Logger failed, exception: " + exception.getMessage());
    }
  }


  private String formatJson(String json) {
    String formattedString = null;
    if (json == null || json.trim().length() == 0) {
      throw new RuntimeException("JSON empty.");
    }
    try {
      if (json.startsWith("{")) {
        JSONObject jsonObject = new JSONObject(json);
        formattedString = jsonObject.toString(JSON_INDENT);
      } else if (json.startsWith("[")) {
        JSONArray jsonArray = new JSONArray(json);
        formattedString = jsonArray.toString(JSON_INDENT);
      } else {
        throw new RuntimeException("JSON should start with { or [, but found " + json);
      }
    } catch (Exception e) {
      throw new RuntimeException("Parse JSON error. JSON string:" + json, e);
    }
    return formattedString;
  }

  private void printJson(int debug, String tag, String msg) {
    String json = null;
    try {
      json = formatJson(msg);
    } catch (Exception e) {
      e.printStackTrace();
    }

    print(debug, tag, json == null ? msg : json);
  }

  private void print(int logLevel, String tag, String msg, Throwable t) {

    print(logLevel, tag, t == null ? msg : msg + "\n" + Log.getStackTraceString(t));
  }

  private void print(int logLevel, String tag, String msg) {
//    if (!BuildConfig.DEBUG) {
//      return;
//    }
    if (msg.length() <= MAX_LENGTH_OF_SINGLE_MESSAGE) {
      printChunk(logLevel, tag, msg);
      return;
    }

    int msgLength = msg.length();
    int start = 0;
    int end = start + MAX_LENGTH_OF_SINGLE_MESSAGE;
    while (start < msgLength) {
      printChunk(logLevel, tag, msg.substring(start, end));
      start = end;
      end = Math.min(start + MAX_LENGTH_OF_SINGLE_MESSAGE, msgLength);
    }
  }

  private void printChunk(int logLevel, String tag, String msg) {
    Log.println(logLevel, tag, msg);
  }

  private static class LoggerLoader {
    private static final LoggerUtil instance = new LoggerUtil();
  }

  public interface RemoteLogger {
    void remote(String msg);
  }

}
