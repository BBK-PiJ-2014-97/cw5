import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;


public class HTMLReadTest extends TestCase {
	private URL testUrl;
	private BufferedReader inputStream;

	public HTMLReadTest() {
		try {
			this.testUrl = new URL("http://localhost:7001/login");
			this.inputStream = new BufferedReader(new InputStreamReader(this.testUrl.openStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// HTMLRead.readUntil() TESTS
	
	public void testReadUntil() {
		HTMLRead testReader = new HTMLRead();
		boolean found = testReader.readUntil(this.inputStream, '<', -1);
		assertEquals(true, found);
	}

	public void testReadUntilCaseSensitivity1() {
		HTMLRead testReader = new HTMLRead();
		boolean found = testReader.readUntil(this.inputStream, 'A', -1);
		assertEquals(true, found);
	}
	
	public void testReadUntilCaseSensitivity2() {
		HTMLRead testReader = new HTMLRead();
		boolean found = testReader.readUntil(this.inputStream, 'Z', -1);
		assertEquals(true, found);
	}
	
	// HTMLRead.skipSpace() TESTS
	
	public void testReadSpaceWithWhitespace() {
		HTMLRead testReader = new HTMLRead();
		char val = testReader.skipSpace(this.inputStream, '"');

	}
	
	public void testReadSpaceWithoutWhitespace() {
		HTMLRead testReader = new HTMLRead();
		char val = testReader.skipSpace(this.inputStream, 'a');
		assertEquals(Character.MIN_VALUE, val);
	}
	
	// HTMLRead.readString() TESTS
	
	public void testReadStringCharacterRead() {
		HTMLRead testReader = new HTMLRead();
		String val = testReader.readString(this.inputStream, 'l', '>');
		assertNotNull(val);
	}
	
	public void testReadStringCharacterNull() {
		HTMLRead testReader = new HTMLRead();
		String val = testReader.readString(this.inputStream, '>', '<');
		assertNull(val);	
	}
}
