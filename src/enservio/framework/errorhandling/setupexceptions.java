package enservio.framework.errorhandling;

@SuppressWarnings("serial")
public class setupexceptions extends RuntimeException {
	public String errorName = "Error";

	public setupexceptions(String errorDescription) {
		super(errorDescription);
	}

	public setupexceptions(String errorName, String errorDescription) {
		super(errorDescription);
		this.errorName = errorName;
	}
}
