import java.io.BufferedReader;
import java.io.IOException;

/**
 * 
 * @author Mario Husha
 * PIJ CW5 Assignment Submission
 */
public class HTMLRead {
	/**
	 * Our current character being read
	 */
	private int currentChar;

	/**
	 * Constructor method for HTMLRead
	 */
	public HTMLRead() {	

	}
	
	/**
	 * This method loops through each character, and return whether ch1 or ch2 are found
	 * NOTE: This method is case-insensitive
	 * 
	 * @param in
	 * @param ch1
	 * @param ch2
	 * @return
	 */
	public boolean readUntil(BufferedReader in, char ch1, int ch2) {
		boolean found = false;
		try {
			while ((currentChar = in.read()) != ch2) { // Use ch2 variable so we select end index
				char current = (char) currentChar;
				// Ignore case when doing comparison for this method
				if(Character.toLowerCase(current) == Character.toLowerCase(ch1)) {
					found = true;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return found;
	}
	
	/**
	 * Read the input buffer up until the first non-white space character, if it's the same as input char,
	 * then Char.MIN_VALUE is returned, else the read vallue is returned
	 * @param in
	 * @param ch
	 * @return
	 */
	public char skipSpace(BufferedReader in, char ch) {
		char returnValue = Character.MIN_VALUE;
		
		try {
			while ((currentChar = in.read()) != -1) {
				char current = (char) currentChar;
				if(!Character.isWhitespace(current)) {
					if(current == ch) {
						returnValue = Character.MIN_VALUE;
						break;
					} else {
						returnValue = current;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return returnValue;
	}
	
	/**
	 * Read HTML until ch1 or ch2 are encountered, ignoring case
	 * @param in
	 * @param ch1
	 * @param ch2
	 */
	public String readString(BufferedReader is, char ch1, char ch2) {

		int data;
		StringBuffer output = new StringBuffer();
		try {
			while ((data = is.read()) != -1) {

				char ch = (char) data;
				if (ch == ch1) {
					break;
				} else if (ch == ch2) {
					return null;
				}
				output.append((char) data);
			}

		} catch (IOException e) {
			System.out.println("File cannot be read: " + e);
		}
		return output.toString();
	}
	
}
