package com.ximalaya.live.shout;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
    //    new Jlibshout("localhost", 8030, "/jshout").pushMp3(
    //        new File("/Users/caorong/test_ffmpeg/151210_085010_1449708300000_1449720480000.aac.mp3"), 24);
    new Thread(new Runnable() {
      @Override
      public void run() {
        InputStream mp3 = new BufferedInputStream(JlibshoutTest.class.getResourceAsStream("/test.mp3"));
        Jlibshout jlibshout = null;
        try {
          jlibshout = new Jlibshout("192.168.3.131", 8030, "/res_" + 55553 + "_" + 24);
        } catch (IOException e) {
          e.printStackTrace();
        }
        try {
          jlibshout.pushMp3asStream(mp3, 24);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();

    Thread.sleep(1000 * 2);

    Jlibshout jlibshout = new Jlibshout("localhost", 8030, "/res_" + 55532+ "_" + 24);
    jlibshout.pushLiveHttpStream("http://192.168.3.131:8030/res_55553_24");
  }

}