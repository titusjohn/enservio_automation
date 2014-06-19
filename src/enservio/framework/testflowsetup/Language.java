package enservio.framework.testflowsetup;

public enum Language {
	// Android("android"),
	English("Eng"), French("Fre");

	private String value;
	private String qcValue = "";
	private static Language defaultLanguage = English;

	Language(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getQcValue() {
		return this.qcValue;
	}

	/**
	 * Returns a Language enum based on the given Language string. This will
	 * attempt to match with values set to lower case. (current enum string
	 * values are set as lower case) Note, also, that spaces are removed (e.g.
	 * if given <code style='color:blue;'>"Internet Explorer"</code> it will be
	 * compared as <code style='color:blue;'>"internetexplorer"</code>).<br>
	 * <br>
	 * Default return is the default set type (<code>InternetExplorer</code>, if
	 * not otherwise set by {@link #setDefault(Language)})
	 * 
	 * @param LanguageName
	 * @return matching Language type
	 */
	public static String getLanguage(String LanguageName) {
		for (Language b : Language.values()) {
			String thisLanguageStr = b.value.replaceAll(" ", "");
			LanguageName = LanguageName.replaceAll(" ", "");
			if (thisLanguageStr.equals(LanguageName.toLowerCase())) {
				return b.getValue();
			}
		}
		System.out.println("Language " + LanguageName
				+ " did not match any in Language enum set.");
		return defaultLanguage.getValue();
	}

	/**
	 * Set default Language to be used when a default is needed.
	 * 
	 * @param Language
	 */
	public static void setDefault(Language Language) {
		defaultLanguage = Language;
	}

	/**
	 * Returns the default Language set. The initialized set default is
	 * <code>InternetExplorer</code>, and can be changed by
	 * {@link #setDefault(Language)}
	 * 
	 * @return
	 */
	public static Language getDefault() {
		return defaultLanguage;
	}

	/*
	 * @Override public String toString() { return value; }
	 */
}
