import java.net.URL;

/**
 * 
 * @author Mario Husha
 *
 */
public interface Crawler {
	boolean search(URL url);
	void crawl(String startUrl, String databaseFile);
	void init();
}
