package com.ximalaya.live.shout;

import java.io.File;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * desc...
 *
 * @author caorong
 */
public class JlibshoutTest {

  @Test
  public void testPushMp3() throws Exception {
    new Jlibshout("localhost", 8030, "/jshout").pushMp3(
        new File("/Users/caorong/test_ffmpeg/151210_085010_1449708300000_1449720480000.aac.mp3"), 24);
  }
}