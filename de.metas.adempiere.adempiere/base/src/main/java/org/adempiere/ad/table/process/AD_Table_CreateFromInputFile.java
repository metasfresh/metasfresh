package org.adempiere.ad.table.process;

import java.io.File;

import org.adempiere.ad.table.api.impl.CreateColumnsProducer;
import org.compiere.util.Util;

import de.metas.process.JavaProcess;
import de.metas.process.Param;

/**
 * Create a table based on the input file
 *
 */
public class AD_Table_CreateFromInputFile extends JavaProcess
{
	@Param(parameterName = "EntityType", mandatory = true)
	private String p_EntityType;
	@Param(parameterName = "TableName", mandatory = true)
	private String p_tableName;
	@Param(mandatory = true, parameterName = "FileName")
	private String p_FileName;

	@Override
	protected String doIt()
	{
		final byte[] content = Util.readBytes(new File(p_FileName));

		final int countCreated = CreateColumnsProducer.newInstance()
				.setLogger(this)
				.setFileContent(content)
				.setTargetTable(p_tableName)
				.setEntityType(p_EntityType)
				.create();

		return "@Created@ #" + countCreated;
	}

}
