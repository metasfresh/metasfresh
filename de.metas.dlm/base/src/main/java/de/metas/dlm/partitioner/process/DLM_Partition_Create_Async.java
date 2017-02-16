package de.metas.dlm.partitioner.process;

import java.sql.Timestamp;

import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.dlm.IDLMService;
import de.metas.dlm.model.I_AD_Table;
import de.metas.dlm.model.I_DLM_Partition_Config;
import de.metas.dlm.partitioner.PartitionRequestFactory;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionAsyncRequest;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionRequest.OnNotDLMTable;
import de.metas.dlm.partitioner.async.DLMPartitionerWorkpackageProcessor;
import de.metas.dlm.partitioner.config.PartitionConfig;
import de.metas.process.Param;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DLM_Partition_Create_Async extends AbstractDLM_Partition_Create
{
	private final IDLMService dlmService = Services.get(IDLMService.class);

	@Param(mandatory = true, parameterName = I_DLM_Partition_Config.COLUMNNAME_DLM_Partition_Config_ID)
	private I_DLM_Partition_Config configDB;

	/**
	 * How many async workpackages do we want?
	 * Mandatory because we want to user to make a conscious decision about how long the async work shall run.
	 */
	@Param(mandatory = true, parameterName = "Count")
	private int count;

	/**
	 * After which time shall the async-work stop?
	 * Mandatory because we want to user to make a conscious decision about how long the async work shall run.
	 */
	@Param(mandatory = true, parameterName = "DontReEnqueueAfter")
	private Timestamp dontReEnqueueAfter;

	@Param(mandatory = true, parameterName = "DLMOldestFirst")
	private boolean oldestFirst;

	@Param(mandatory = false, parameterName = "AD_Table_ID")
	private I_AD_Table adTable;

	@Param(mandatory = false, parameterName = "Record_ID")
	private int recordId;

	@Override
	protected String doIt() throws Exception
	{
		ITableRecordReference recordToAttach = null;
		if (adTable != null && recordId > 0)
		{
			recordToAttach = TableRecordReference.of(adTable.getAD_Table_ID(), recordId);
		}

		final PartitionConfig config = dlmService.loadPartitionConfig(configDB);

		final CreatePartitionAsyncRequest request = PartitionRequestFactory.asyncBuilder()
				.setConfig(config)
				.setOldestFirst(oldestFirst)
				.setRecordToAttach(recordToAttach)
				.setPartitionToComplete(getPartitionToCompleteOrNull())
				.setOnNotDLMTable(OnNotDLMTable.FAIL) // the processing will run unattended. See the javadoc of ADD_TO_DLM on why it's not an option.
				.setCount(count)
				.setDontReEnqueueAfter(dontReEnqueueAfter)
				.build();

		final I_C_Queue_WorkPackage newWorkpackage = DLMPartitionerWorkpackageProcessor.schedule(request, getAD_PInstance_ID());
		addLog("Scheduled " + newWorkpackage);

		return MSG_OK;
	}

}
