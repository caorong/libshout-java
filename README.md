libshout implemention in pure java
=============

make a great change from  [original libshout-java](https://github.com/OlegKunitsyn/libshout-java),
make it can be used in pure java, without jni

but it's feature is limited, only support stream http stream and file as source
and only mp3 file is supported to auto calculate buffer length,
other file like ogg your should specified buffer size every second by your self

Requirements

* Java 1.6+


```xml
<dependency>
    <groupId>com.ximalaya.live</groupId>
    <artifactId>libshout-java</artifactId>
    <version>0.1.3</version>
</dependency>
```




Streaming
-----------


1. relay http stream to icecast

```java
Jlibshout jlibshout = new Jlibshout("localhost", 8030, "/jlibshout");
try {
  jlibshout.pushLiveHttpStream("http://192.168.3.xx:8030/sourceStream");
} finally {
  jlibshout.close();
}
```

2. relay mp3 file to icecast

```java
try {
  Jlibshout jlibshout = new Jlibshout("localhost", 8030, "/jshout");
  jlibshout.pushMp3(new File("/Users/caorong/test_ffmpeg/10sec_.mp3"));
} finally {
  jlibshout.close();
}
```

3. relay ogg or ther media file to icecast

```java
try {
  Jlibshout jlibshout = new Jlibshout("localhost", 8030, "/jshout");
  InputStream mp3 = new BufferedInputStream(JlibshoutTest.class.getResourceAsStream("/test.ogg"));
  // send 4096 byte every second
  jlibshout.pushMediaFileAsStream(mp3, 4096);
} finally {
  jlibshout.close();
}
```
