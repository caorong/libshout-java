package com.ximalaya.live.shout.ex;

/**
 * ex when push stream to icecast server
 *
 * @author caorong
 */
public class PushStreamException extends RuntimeException{
  public PushStreamException() {
  }

  public PushStreamException(String message) {
    super(message);
  }

  public PushStreamException(String message, Throwable cause) {
    super(message, cause);
  }

  public PushStreamException(Throwable cause) {
    super(cause);
  }
}
