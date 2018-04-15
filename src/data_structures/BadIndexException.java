package data_structures;

/**
 * @author bernardo
 *
 */
public class BadIndexException extends IllegalArgumentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1314738095503200196L;
	StringBuilder message;

	public BadIndexException() {
		message = new StringBuilder();
		message.append("BadIndexException has occured:\n");
	}

	public BadIndexException(String message) {
		this();
		this.setMessage(message);
		this.print();
	}

	public void setMessage(String message) {
		this.message.append(message);     
		this.message.append("This is not allowed in dataframes and you know it.\n"
        		+ "So you do not test my patience and correct this in your input data.\n"
        		+ "Then we shall pretend that has never happened.\n"
        		+ "Good day.\n");
	}

	private void print() {
		System.err.println(message);
	}
}
