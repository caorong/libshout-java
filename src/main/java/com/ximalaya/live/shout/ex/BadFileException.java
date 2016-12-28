package com.ximalaya.live.shout.ex;

/**
 * bad source file
 *
 * @author caorong
 */
public class BadFileException extends Exception {
  public BadFileException() {
  }

  public BadFileException(String message) {
    super(message);
  }

  public BadFileException(String message, Throwable cause) {
    super(message, cause);
  }

  public BadFileException(Throwable cause) {
    super(cause);
  }
}
