package de.metas.dlm.partitioner.process;

import de.metas.dlm.model.I_DLM_Partition;
import de.metas.process.JavaProcess;

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

public abstract class AbstractDLM_Partition_Create extends JavaProcess
{
	public final I_DLM_Partition getPartitionToCompleteOrNull()
	{
		if (getRecord_ID() > 0 && I_DLM_Partition.Table_Name.equals(getTableName()))
		{
			final I_DLM_Partition partitionToComplete = getRecord(I_DLM_Partition.class);
			if (partitionToComplete.isPartitionComplete())
			{
				addLog("Ignoring completed partition {}", partitionToComplete);
			}
			else
			{
				return partitionToComplete;
			}
		}
		return null;
	}
}
