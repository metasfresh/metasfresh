package org.compiere.dbPort;

import java.util.Collections;
import java.util.List;

public class Convert_Oracle extends Convert
{

	public Convert_Oracle() {}
	
	@Override
	protected List<String> convertStatement(String sqlStatement)
	{
		return Collections.singletonList(sqlStatement);
	}

	@Override
	public final boolean isOracle()
	{
		return true;
	}

}
