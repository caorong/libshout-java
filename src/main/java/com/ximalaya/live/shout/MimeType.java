package com.ximalaya.live.shout;

/**
 * desc...
 *
 * @author caorong
 */
public enum MimeType {
  mp3("audio/mpeg"), ogg("application/ogg"), audioWebm("audio/webm"), videoWebm("video/webm");

  private String contentType;

  MimeType(String s) {
    contentType = s;
  }

  public String getContentType() {
    return contentType;
  }
}
