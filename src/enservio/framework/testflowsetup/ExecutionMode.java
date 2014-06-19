package enservio.framework.testflowsetup;

public enum ExecutionMode {
	/**
	 * Execute on the local machine
	 */
	Local,

	/**
	 * Execute on a remote machine
	 */
	Remote,

	/**
	 * Execute on a selenium grid
	 */
	Grid;
}