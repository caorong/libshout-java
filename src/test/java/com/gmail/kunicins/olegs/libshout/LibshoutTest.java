//package com.gmail.kunicins.olegs.libshout;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.github.kevinsawicki.http.HttpRequest;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class LibshoutTest {
//	private Libshout libshout = null;
//
//	@Before
//	public void setUp() throws IOException {
//		this.libshout = new Libshout();
//	}
//
//	@After
//	public void tearDown() {
//		this.libshout = null;
//	}
//
//	@Test
//	public void testOS() {
//		System.out.println(System.getProperty("user.dir"));
//		System.out.println(System.getProperty("os.arch"));
//		System.out.println(System.getProperty("os.name").toLowerCase());
//	}
//
//  @Test
//  public void testreStreamer() throws IOException {
//    byte[] buffer = new byte[4096];
//		int code = HttpRequest.get("http://192.168.3.131:8030/activity_7").code();
//		System.out.println(code);
//
//    InputStream mp3 = HttpRequest.get("http://192.168.3.131:8030/activity_7").buffer();
//		Libshout icecast = new Libshout();
//    //		icecast://192.168.3.132:8030/activity_36?token=7465954c5f4c4633967d404ab01b2988
//    icecast.setHost("192.168.3.52");
//    icecast.setPort(8030);
//    //		icecast.setProtocol(Libshout.PROTOCOL_HTTP);
//    icecast.setProtocol(Libshout.PROTOCOL_HTTP);
//    icecast.setPassword("hackme");
//    icecast.setMount("/activity_7");
//    icecast.setFormat(Libshout.FORMAT_MP3);
//    icecast.open();
//    int read = mp3.read(buffer);
//		System.out.println(new String(buffer ));
//		while (read > 0) {
//      icecast.send(buffer, read);
//      read = mp3.read(buffer);
//    }
//    icecast.close();
//    mp3.close();
//  }
//
//	@Test
//	public void testNginxRange() throws IOException {
////		byte[] buffer = new byte[1024];
//		byte[] buffer = new byte[4096];
//
////		String body = HttpRequest.get("http://192.168.3.131/live/1065_24.mp3")
////				.header("Content-Range", "bytes 2487842-").body();
////		System.out.println(body);
////		System.exit(1);
//
////		InputStream mp3 = new BufferedInputStream(new FileInputStream(new File("/Users/caorong/outtt.mp3")));
//
//		InputStream mp3 = HttpRequest.get("http://192.168.3.131/live/1065_64.mp3")
//				.header("Range", "bytes=4255077-").buffer();
//		Libshout icecast = new Libshout();
////		icecast://192.168.3.132:8030/activity_36?token=7465954c5f4c4633967d404ab01b2988
//		icecast.setHost("192.168.3.132");
//		icecast.setPort(8030);
////		icecast.setProtocol(Libshout.PROTOCOL_HTTP);
//		icecast.setProtocol(Libshout.PROTOCOL_HTTP);
//		icecast.setPassword("hackme");
//		icecast.setMount("/activity_36?token=7465954c5f4c4633967d404ab01b2988");
//		icecast.setFormat(Libshout.FORMAT_MP3);
//		icecast.open();
//		int read = mp3.read(buffer);
//		while (read > 0) {
//			icecast.send(buffer, read);
//			read = mp3.read(buffer);
//		}
//		icecast.close();
//		mp3.close();
//
//
//	}
//
//	@Test
//	public void testVersion() {
//		String currentVersion = this.libshout.getVersion();
//		assertTrue("Expected Libshout version 2.2.2 or greater, but found version " + currentVersion,
//				"2.2.2".compareTo(currentVersion) <= 0);
//	}
//
//	@Test
//	public void testHost() throws IOException {
//		String fixture = "localhost";
//		this.libshout.setHost(fixture);
//		assertEquals(fixture, this.libshout.getHost());
//	}
//
//	@Test
//	public void testPort() throws IOException {
//		int fixture = 8000;
//		this.libshout.setPort(fixture);
//		assertEquals(fixture, this.libshout.getPort());
//	}
//
//	@Test
//	public void testProtocol() throws IOException {
//		int fixture = Libshout.PROTOCOL_HTTP;
//		this.libshout.setProtocol(fixture);
//		assertEquals(fixture, this.libshout.getProtocol());
//	}
//
//	@Test
//	public void testPassword() throws IOException {
//		String fixture = "hackme";
//		this.libshout.setPassword(fixture);
//		assertEquals(fixture, this.libshout.getPassword());
//	}
//
//	@Test
//	public void testMount() throws IOException {
//		String fixture = "/mount";
//		this.libshout.setMount(fixture);
//		assertEquals(fixture, this.libshout.getMount());
//	}
//
//	@Test
//	public void testFormat() throws IOException {
//		int fixture = Libshout.FORMAT_MP3;
//		this.libshout.setFormat(fixture);
//		assertEquals(fixture, this.libshout.getFormat());
//	}
//
//	@Test
//	public void testName() throws IOException {
//		String fixture = "name";
//		this.libshout.setName(fixture);
//		assertEquals(fixture, this.libshout.getName());
//	}
//
//	@Test
//	public void testUrl() throws IOException {
//		String fixture = "http://myfm.at";
//		this.libshout.setUrl(fixture);
//		assertEquals(fixture, this.libshout.getUrl());
//	}
//
//	@Test
//	public void testGenre() throws IOException {
//		String fixture = "metal";
//		this.libshout.setGenre(fixture);
//		assertEquals(fixture, this.libshout.getGenre());
//	}
//
//	@Test
//	public void testUser() throws IOException {
//		String fixture = "user";
//		this.libshout.setUser(fixture);
//		assertEquals(fixture, this.libshout.getUser());
//	}
//
//	@Test
//	public void testAgent() throws IOException {
//		String fixture = "icecast2";
//		this.libshout.setAgent(fixture);
//		assertEquals(fixture, this.libshout.getAgent());
//	}
//
//	@Test
//	public void testDescription() throws IOException {
//		String fixture = "some description";
//		this.libshout.setDescription(fixture);
//		assertEquals(fixture, this.libshout.getDescription());
//	}
//
//	@Test
//	public void testDumpfile() throws IOException {
//		String fixture = "file.bin";
//		this.libshout.setDumpfile(fixture);
//		assertEquals(fixture, this.libshout.getDumpfile());
//	}
//
//	@Test
//	public void testInfo() throws IOException {
//		String fixture = "value";
//		this.libshout.setInfo("key", fixture);
//		assertEquals(fixture, this.libshout.getInfo("key"));
//	}
//
//	@Test
//	public void testPublic() throws IOException {
//		boolean fixture = false;
//		this.libshout.setPublic(fixture);
//		assertEquals(fixture, this.libshout.isPublic());
//	}
//
//	@Test
//	public void testNonBlocking() throws IOException {
//		boolean fixture = true;
//		this.libshout.setNonBlocking(fixture);
//		assertEquals(fixture, this.libshout.isNonBlocking());
//	}
//}
