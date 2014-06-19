package enservio.framework.testflowsetup;

public enum Browser {
	Chrome("chrome", "Chrome"), Firefox("firefox", "Firefox"), HtmlUnit(
			"htmlunit", "HtmlUnit"), InternetExplorer("internet explorer",
			"Internet Explorer"), Opera("opera", "Opera"), Safari("safari",
			"Safari"), NA("NA", "NA");

	private String value;
	private String qcValue = "";
	private static Browser defaultBrowser = Firefox;

	Browser(String value, String qcEquiv) {
		this.value = value;
		this.qcValue = qcEquiv;
	}

	public String getValue() {
		return value;
	}

	public String getQcValue() {
		return this.qcValue;
	}

	/**
	 * Returns a Browser enum based on the given browser string. This will
	 * attempt to match with values set to lower case. (current enum string
	 * values are set as lower case) Note, also, that spaces are removed (e.g.
	 * if given <code style='color:blue;'>"Internet Explorer"</code> it will be
	 * compared as <code style='color:blue;'>"internetexplorer"</code>).<br>
	 * <br>
	 * Default return is the default set type (<code>InternetExplorer</code>, if
	 * not otherwise set by {@link #setDefault(Browser)})
	 * 
	 * @param browserName
	 * @return matching Browser type
	 */
	public static Browser getBrowser(String browserName) {

		for (Browser b : Browser.values()) {
			String thisbrowserStr = b.value.replaceAll(" ", "");

			browserName = browserName.replaceAll(" ", "");
			if (thisbrowserStr.equals(browserName.toLowerCase())) {
				return b;
			}
		}
		System.out.println("Browser " + browserName
				+ " did not match any in Browser enum set.");
		return defaultBrowser;
	}

	/**
	 * Set default browser to be used when a default is needed.
	 * 
	 * @param browser
	 */
	public static void setDefault(Browser browser) {
		defaultBrowser = browser;
	}

	/**
	 * Returns the default browser set. The initialized set default is
	 * <code>InternetExplorer</code>, and can be changed by
	 * {@link #setDefault(Browser)}
	 * 
	 * @return
	 */
	public static Browser getDefault() {
		return defaultBrowser;
	}

}