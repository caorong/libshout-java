package com.ximalaya.live.shout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.annotations.Beta;
import com.google.common.io.LineReader;
import com.ximalaya.live.shout.ex.PushStreamException;
import com.ximalaya.live.shout.ex.ReadStreamException;

/**
 * libshout from source url to target ip port
 *
 * @author caorong
 */
public class Jlibshout {

  private String user;
  private String password;
  private String targetHost;
  private int targetPort;
  private String mounter;

  private String iceName = "no name";
  private String iceDesc = null;

  // target icecast  outputStream
  private OutputStream outputStream;

  //  URL url = new URL("http://source:hackme@localhost:8030/res_1065_24");
  public Jlibshout(String targetHost, int targetPort, String mounter) throws IOException {
    this("source", "hackme", targetHost, targetPort, mounter);
  }

  public Jlibshout(String user, String password, String targetHost, int targetPort, String mounter)
      throws IOException {
    this.user = user;
    this.password = password;
    this.targetHost = targetHost;
    this.targetPort = targetPort;
    this.mounter = mounter;
    init();
  }

  public void setIceDesc(String iceDesc) {
    this.iceDesc = iceDesc;
  }

  public void setIceName(String iceName) {
    this.iceName = iceName;
  }

  private void init() throws IOException {
    Socket socket = null;
    try {
      socket = new Socket(targetHost, targetPort);
      outputStream = socket.getOutputStream();
      PrintWriter out = new PrintWriter(outputStream, false);
      InputStream inputStream = socket.getInputStream();

      // send an HTTP request to the web server
      out.println(String.format("SOURCE %s HTTP/1.0", mounter));
      out.println(String.format("Authorization: Basic %s", HttpRequest.Base64.encode(user + ":" + password)));
      out.println("User-Agent: libshout/2.3.1");
      // TODO add
      // mimetype = "application/ogg";
      // mimetype = "audio/mpeg";
      // mimetype = "video/webm";
      // mimetype = "audio/webm";
      out.println("Content-Type: audio/mpeg");
      out.println(String.format("ice-name: %s", iceName));
      out.println("ice-public: 0");
      if (iceDesc != null) {
        out.println(String.format("ice-description: %s", iceDesc));
      }
      out.println();
      out.flush();

      // check if 404
      LineReader lineReader = new LineReader(new InputStreamReader(inputStream));
      String data = lineReader.readLine();

      handleResponse(data);
    } catch (IOException e) {
      if (socket != null && !socket.isClosed()) {
        try {
          socket.close();
        } catch (IOException e1) {
          // skip
        }
      }
      throw e;
    }
  }

  /**
   * push mp3 with sleep
   * 32 4kb/s
   * 64 8kb/s
   * <p/>
   * unaccurate version
   *
   * @param mp3
   * @param bitrate
   * @throws FileNotFoundException
   */
  @Beta
  public void pushMp3(File mp3, int bitrate) throws IOException {
    InputStream inputStream = new FileInputStream(mp3);
    pushMp3asStream(inputStream, bitrate);
  }

  @Beta
  public void pushMp3asStream(InputStream inputStream, int bitrate) throws IOException {
    int bufferSize = bitrate <= 32 ? 4096 : 8192;

    byte[] buffer = new byte[bufferSize];
    try {
      // mainloop, write every specified size, reduce syscall
      while (true) {
        int readed = inputStream.read(buffer, 0, bufferSize);
        // EOF
        if (readed < 0) {
          break;
        } else {
          outputStream.write(buffer, 0, readed);
          outputStream.flush();
        }
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // skip
        }
      }
    } catch (IOException e) {
      try {
        inputStream.close();
      } catch (IOException e1) {
        // skip
      }
      throw e;
    }
  }

  /**
   * push 'live' http stream support mp3, ogg, webm etc without sync
   * <p/>
   * sync is dependence on source stream
   * <p/>
   * this method will block until exception or reach EOF
   * <p/>
   * <p/>
   * about buffersize is 8k
   * e.g mp3
   * 64kbit/s => 8kb/s => sleep time may be 1s will be just in time
   * <p/>
   * maybe throw:
   * <p/>
   * ReadStreamEx (404), sourceStreamIOEx
   * <p/>
   *
   * @param url source live http stream url
   */
  public void pushLiveHttpStream(String url) throws IOException, InterruptedException {
    pushLiveHttpStream(url, 8192);
  }

  public void pushLiveHttpStream(String url, int bufferSize) throws IOException {
    //    InputStream inputStream = new BufferedInputStream(HttpRequest.get(url).stream(), bufferSize);
    int code = HttpRequest.get(url).code();
    if (code != 200) {
      throw new ReadStreamException(code);
    }
    InputStream inputStream = HttpRequest.get(url).stream();

    // resued buffer
    byte[] buffer = new byte[bufferSize];
    try {
      int count = 0;
      // mainloop, write every specified size, reduce syscall
      while (true) {
        // 避免 最后一段
        if (inputStream.available() > bufferSize || count > 4) {
          int readed = inputStream.read(buffer, 0, bufferSize);
          // EOF
          if (readed < 0) {
            break;
          } else {
            outputStream.write(buffer, 0, readed);
            outputStream.flush();
            count = 0;
          }
        }
        try {
          count++;
          Thread.sleep(400);
        } catch (InterruptedException e) {
          // skip
        }
      }
    } catch (IOException e) {
      try {
        inputStream.close();
      } catch (IOException e1) {
        // skip
      }
      throw e;
    }
  }

  /**
   * 密码错误
   * HTTP/1.0 401 Authentication Required
   * 同一个 mounter 重复推流
   * HTTP/1.0 403 Forbidden
   *
   * @param data
   */
  private void handleResponse(String data) {
    if (data.startsWith("HTTP/1.0 401")) {
      throw new PushStreamException("auth error! check username and password");
    } else if (data.startsWith("HTTP/1.0 403 Forbidden")) {
      throw new PushStreamException("invalid operation! this stream is alreay streaming!");
    } else {
      if (!data.startsWith("HTTP/1.0 200")) {
        throw new PushStreamException("unknown exception! " + data);
      }
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    Jlibshout jlibshout = new Jlibshout("localhost", 8030, "/javashout2");
    jlibshout.pushLiveHttpStream("http://192.168.3.52:8030/res_16_64");
  }
}
