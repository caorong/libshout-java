package com.ximalaya.live.shout;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * desc...
 *
 * @author caorong
 */
public class JlibshoutTest {

  @Test
  public void testPushMp3() throws Exception {
    new Thread(new Runnable() {
      @Override
      public void run() {
        InputStream mp3 = new BufferedInputStream(JlibshoutTest.class.getResourceAsStream("/test.mp3"));
        Jlibshout jlibshout = null;
        try {
          jlibshout = new Jlibshout("192.168.3.131", 8030, "/res_" + 55553 + "_" + 24);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        }
        try {
          jlibshout.pushMediaFileAsStream(mp3, 4096);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();

    Thread.sleep(1000 * 2);

    Jlibshout jlibshout = new Jlibshout("localhost", 8030, "/res_" + 55532 + "_" + 24);
    jlibshout.pushLiveHttpStream("http://192.168.3.131:8030/res_55553_24");
  }

  @Test
  public void testPushMp32() throws Exception {
    Jlibshout jlibshout = new Jlibshout("localhost", 8030, "/jshout");
    jlibshout.pushMp3(new File("/Users/caorong/test_ffmpeg/10sec_.mp3"));
  }

}