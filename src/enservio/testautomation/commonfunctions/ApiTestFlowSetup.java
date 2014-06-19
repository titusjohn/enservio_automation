package enservio.testautomation.commonfunctions;

import java.util.ArrayList;
import java.util.HashMap;

import enservio.framework.globalfunctions.DataTable;

public interface ApiTestFlowSetup {

	public abstract void initializeTestArtifacts();

	public abstract void initializeTestDefinition(DataTable dataTable);

	public abstract void initializeInputData(ArrayList<String> aArrayParam1);

	public abstract HashMap<String, Object> mapInpTestData(
			HashMap<String, Object> hMap, ArrayList<String> aArrParam1);

	public abstract HashMap<String, Object> getInpQueryData(
			ArrayList<String> aArrParam1, String sParam1);

	public abstract String getInpTestData(String sParam1);

	public abstract HashMap<String, Object> getInpNoneData(
			HashMap<String, Object> hMap);

	public abstract HashMap<String, Object> consolidateInputData(ArrayList<String> aArrayParam1);


}
