package de.metas.dlm.partitioner.process;

import java.util.List;

import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.dlm.IDLMService;
import de.metas.dlm.model.I_AD_Table;
import de.metas.dlm.model.I_DLM_Partition_Config;
import de.metas.dlm.partitioner.IRecordCrawlerService;
import de.metas.dlm.partitioner.config.PartitionConfig;
import de.metas.dlm.partitioner.graph.FindPathIterateResult;
import de.metas.process.JavaProcess;
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

public class DLM_FindPathBetweenRecords extends JavaProcess
{
	@Param(mandatory = true, parameterName = I_DLM_Partition_Config.COLUMNNAME_DLM_Partition_Config_ID)
	private I_DLM_Partition_Config configDB;

	@Param(mandatory = true, parameterName = "AD_Table_Start_ID")
	private I_AD_Table adTableStart;

	@Param(mandatory = true, parameterName = "Record_Start_ID")
	private int recordIdStart;

	@Param(mandatory = true, parameterName = "AD_Table_Goal_ID")
	private I_AD_Table adTableGoal;

	@Param(mandatory = true, parameterName = "Record_Goal_ID")
	private int recordIdGoal;

	@Override
	protected String doIt() throws Exception
	{
		final TableRecordReference startReference = TableRecordReference.of(adTableStart.getAD_Table_ID(), recordIdStart);
		final TableRecordReference goalReference = TableRecordReference.of(adTableGoal.getAD_Table_ID(), recordIdGoal);

		final IDLMService dlmService = Services.get(IDLMService.class);
		final IRecordCrawlerService recordCrawlerService = Services.get(IRecordCrawlerService.class);

		final FindPathIterateResult findPathIterateResult = new FindPathIterateResult(
				startReference,
				goalReference);

		final PartitionConfig config = dlmService.loadPartitionConfig(configDB);
		addLog("Starting at start record={}, searching for goal record={}", startReference, goalReference);

		recordCrawlerService.crawl(config, this, findPathIterateResult);
		addLog("Search done. Found goal record: {}", findPathIterateResult.isFoundGoalRecord());

		// new GraphUI().showGraph(findPathIterateResult);

		if (!findPathIterateResult.isFoundGoalRecord())
		{
			addLog("Upable to reach the goal record from the given start using DLM_Partition_Config= {}", config.getName());
			return MSG_OK;
		}

		final List<ITableRecordReference> path = findPathIterateResult.getPath();
		if (path == null)
		{
			addLog("There is no path between start={} and goal={}", startReference, goalReference);
			return MSG_OK;
		}
		addLog("Records that make up the path from start to goal (size={}):", path.size());
		for (int i = 0; i < path.size(); i++)
		{
			final ITableRecordReference pathElement = path.get(i);
			pathElement.getModel(this); // make sure the model is loaded and thus shown in the reference's toString output
			addLog("{}: {}", i, pathElement);
		}
		return null;
	}

}
