import junit.framework.TestCase;


public class WebCrawlerTest extends TestCase {

	// Tests for initialization
	
	public void testCanInitCrawler() {
		WebCrawler testCrawler = new WebCrawler();
		testCrawler.crawl("http://wizardsworkshop.co.uk/crawler-test", "db1.txt");
		assertNotNull(testCrawler);
	}
	
	public void testCannotInitWithHttps() {
		WebCrawler testCrawler = new WebCrawler();
		testCrawler.crawl("https://www.wizardsworkshop.co.uk/crawler-test", "db2.txt");
		assertEquals(null, testCrawler.startUrl);
	}
	
	// Tests for crawl method
	
	public void testCanFindUrls() {
		WebCrawler testCrawler = new WebCrawler();
		testCrawler.crawl("http://www.wizardsworkshop.co.uk/crawler-test", "db3.txt");
	}
	
	public void testCanAddDBRecord() {
		WebCrawler testCrawler = new WebCrawler();
		testCrawler.crawl("http://www.wizardsworkshop.co.uk/crawler-test", "db4.txt");
		testCrawler.addDatabaseRecord("temp", "This is a test entry");
	}
	
	public void testCantSkipLimits() {
		WebCrawler testCrawler = new WebCrawler();
		testCrawler.MAX_DEPTH_SEARCH = 1;
		testCrawler.MAX_UNIQUE_LINKS = 1;
		testCrawler.crawl("http://www.wizardsworkshop.co.uk/crawler-test", "db5.txt");
	}
 }
