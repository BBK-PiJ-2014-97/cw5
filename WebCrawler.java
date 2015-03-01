import java.io.*;
import java.math.BigInteger;
import java.net.*;

/**
 * @author Mario Husha
 * PIJ CW5 Assignment Submission
 */
public class WebCrawler implements Crawler{
	public int MAX_UNIQUE_LINKS = 10;
	public int MAX_DEPTH_SEARCH = 10;

	/**
	 * String version of start URL
	 */
	public String startUrl = null;
	
	/**
	 * Reader for our pages
	 */
	public BufferedReader reader = null;
	
	/**
	 * Instanced version of our start URL
	 */
	public URL impUrl = null;
	
	/**
	 * Instanced version of our current URL
	 */
	public URL currentUrl = null;
	
	/**
	 * Where we would like to save each link
	 */
	
	public File temp_databaseFile = null;
	public File final_databaseFile = null;
	
	/**
	 * Current depth of our WebCrawler
	 */
	private static int depth = 0;

	/**
	 * Instance of htmlRead utils
	 */
	private HTMLRead htmlReader;

	/**
	 * Databse file name template string
	 */
	private String databaseFile;
	
	/**
	 * Empty constructor
	 */
	public WebCrawler() {

	}
	
	/**
	 * Our main crawl method to start crawling a URL, can be called multiple times
	 */
	public void crawl(String url, String databaseFile) {
		try {
			if(url.toLowerCase().contains("https")) {
				throw new Exception("Crawler does not support HTTPS protocol");
			} else {
				if(WebCrawler.depth == this.MAX_DEPTH_SEARCH) {
					System.out.println("MAX_DEPTH_SEARCH of " + this.MAX_DEPTH_SEARCH + " Reached");
					return;
				} else {
					// Only runs for the initial call
					if(this.startUrl == null) {
						this.startUrl = url;
						this.databaseFile = databaseFile;
						this.temp_databaseFile  = new File("temp_" + databaseFile);
						this.final_databaseFile = new File("final_" + databaseFile);
						this.htmlReader = new HTMLRead();
						
						this.temp_databaseFile.createNewFile();
						this.final_databaseFile.createNewFile();
					}
					
					WebCrawler.depth++ ;
					
					// Current URL we are crawling
					this.impUrl = new URL(url);
					this.init();
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Call when first starting WebCrawler
	 */
	public void init() {
		this.addDatabaseRecord("temp", this.startUrl);
		
		System.out.println("Crawling URL: " + this.impUrl.toString());
		
		try {
			this.reader = new BufferedReader(new InputStreamReader(this.impUrl.openStream()));
			if(this.search(this.currentUrl)) {
				this.processPage();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
 
	}
	
	/**
	 * Scan the current page for URLS
	 */
	public void processPage() {	
		// Find HTML tags
		while (this.htmlReader.readUntil(this.reader, '<', -1)) {
			// Check if our tag is a link (as we skip spaces, we look for ahref=)
			if(this.contains("ahref=")) {
				this.htmlReader.readUntil(this.reader, '\'', '"'); // Move our index to the end
				String url = this.htmlReader.readString(this.reader, '"', '\'');
					// Remove unwanted results
					if(this.contains("http")){
						// Add to temp table with priority
						this.addDatabaseRecord("temp", url);
						this.crawl(url, this.databaseFile);
						this.removeDatabaseRecord("temp", url);
						this.addDatabaseRecord("final", url);
					}
			}
		}
		
		
		// Done parsing page, move onto next temp link
//		try {
//			System.out.println(this.getNextTemp());
//			this.crawl(this.getNextTemp(), this.final_databaseFile.getCanonicalPath());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	/**
	 * REmove records from temp database file
	 * @param type
	 * @param url
	 */
	private void removeDatabaseRecord(String type, String url) {
		// TODO Auto-generated method stub
		
	}

	private String getNextTemp() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.getFile("temp")));
			String line = null;  
			while ((line = reader.readLine()) != null)  {  
				String[] lineContents = line.split(" ");
				if(WebCrawler.depth + 1 == Integer.parseInt(lineContents[0])) {
					return lineContents[1];
				}
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper to get HTML contents of page URL supplied
	 * @param in
	 * @return String page contents
	 */
	private String getPageContents() {
		int currentchar;
		
		String page = "";
		try {
			// We just simply add each character to a string
			while (( currentchar = this.reader.read()) != -1) {
				page += (char) currentchar;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return page;
	}

	
	/**
	 * Add a new line to our database file
	 * @param priority
	 * @param content
	 * @return [boolean] if record was added successfully
	 */
	public boolean addDatabaseRecord(String type, String content) {
		try {
			FileWriter fileWriter = new FileWriter(this.getFile(type).getAbsoluteFile(), true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(WebCrawler.depth + " " + content + "\n");
			bufferedWriter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Return database file object depending on type
	 * @param type
	 * @return
	 */
	private File getFile(String type) {
		File fileObject = this.temp_databaseFile;
		if(type == "final") {
			fileObject = this.final_databaseFile;
		}
		
		return fileObject;
	}

	/**
	 * Check for occurrence of string
	 * @param check
	 * @return [boolean] If string was found in page
	 */
	public boolean contains(String checkString) {
		for (int i = 0; i < checkString.length(); i++) {
			if (this.htmlReader.skipSpace(this.reader, checkString.charAt(i)) != Character.MIN_VALUE) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 *  Default search method, that can be overridden
	 *  @param url
	 *  @return [boolean] where the search method found something
	 */
	public boolean search(URL url) {
		return true;
	}

}
