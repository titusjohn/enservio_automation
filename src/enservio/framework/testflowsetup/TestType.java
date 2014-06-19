package enservio.framework.testflowsetup;

public enum TestType {
	Android("android"), ECommerce("ecommerce"), API("api"), Database("database");

	private String value;
	private static TestType defaultTestType = ECommerce;

	TestType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/**
	 * Returns a TestType enum based on the given TestType string. This will
	 * attempt to match with values set to lower case. (current enum string
	 * values are set as lower case) Note, also, that spaces are removed (e.g.
	 * if given <code style='color:blue;'>"Internet Explorer"</code> it will be
	 * compared as <code style='color:blue;'>"internetexplorer"</code>).<br>
	 * <br>
	 * Default return is the default set type (<code>InternetExplorer</code>, if
	 * not otherwise set by {@link #setDefault(TestType)})
	 * 
	 * @param TestTypeName
	 * @return matching TestType type
	 */
	public static TestType getTestType(String TestTypeName) {
		for (TestType b : TestType.values()) {
			String thisTestTypeStr = b.value.replaceAll(" ", "");
			TestTypeName = TestTypeName.replaceAll(" ", "");
			if (thisTestTypeStr.equals(TestTypeName.toLowerCase())) {
				return b;
			}
		}
		System.out.println("TestType " + TestTypeName
				+ " did not match any in TestType enum set.");
		return defaultTestType;
	}

	/**
	 * Set default TestType to be used when a default is needed.
	 * 
	 * @param TestType
	 */
	public static void setDefault(TestType TestType) {
		defaultTestType = TestType;
	}

	/**
	 * Returns the default TestType set. The initialized set default is
	 * <code>InternetExplorer</code>, and can be changed by
	 * {@link #setDefault(TestType)}
	 * 
	 * @return
	 */
	public static TestType getDefault() {
		return defaultTestType;
	}

	/*
	 * @Override public String toString() { return value; }
	 */
}
