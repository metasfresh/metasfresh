package de.metas.dlm.partitioner.process;

import org.adempiere.util.Services;
import org.compiere.process.SvrProcess;

import de.metas.dlm.Partition;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.model.I_DLM_Partition_Config;
import de.metas.dlm.partitioner.IPartitionerService;
import de.metas.dlm.partitioner.config.PartitionerConfig;
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

public class DLM_Partition_Create extends SvrProcess
{

	private final IPartitionerService partitionerService = Services.get(IPartitionerService.class);

	@Param(mandatory = true, parameterName = I_DLM_Partition_Config.COLUMNNAME_DLM_Partition_Config_ID)
	private I_DLM_Partition_Config configDB;

	@Param(mandatory = true, parameterName = "Count")
	private int count;

	@Override
	protected String doIt() throws Exception
	{
		final PartitionerConfig config = partitionerService.loadPartitionConfig(configDB);

		for (int i = 0; i < count; i++)
		{
			final Partition partition = partitionerService.createPartition(config);

			// partitionerService.storePartitionConfig(partition.getConfig()); this is already done by storePartition
			final I_DLM_Partition partitionDB = partitionerService.storePartition(partition);

			addLog("@Created@ " + partitionDB);
		}
		return MSG_OK;
	}

}
