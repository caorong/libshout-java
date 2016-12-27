package com.ximalaya.live.shout.ex;

/**
 * ex when read stream for push
 *
 * @author caorong
 */
public class ReadStreamException extends RuntimeException {
  public ReadStreamException() {
  }

  public ReadStreamException(int code) {
    super("source stream code is " + code);
  }

  public ReadStreamException(String message) {
    super(message);
  }

  public ReadStreamException(String message, Throwable cause) {
    super(message, cause);
  }

  public ReadStreamException(Throwable cause) {
    super(cause);
  }
}
