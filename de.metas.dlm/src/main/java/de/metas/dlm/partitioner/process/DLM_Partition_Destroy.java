package de.metas.dlm.partitioner.process;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.process.SvrProcess;

import de.metas.dlm.IDLMService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_DLM_Partition;

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

/**
 * Sets {@link IDLMAware#COLUMNNAME_DLM_Partition_ID} to 0 for all records that reference the current partition
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DLM_Partition_Destroy extends SvrProcess
{
	private final IDLMService dlmService = Services.get(IDLMService.class);

	@Override
	protected String doIt() throws Exception
	{
		final I_DLM_Partition partitionDB = getProcessInfo().getRecord(I_DLM_Partition.class);

		dlmService.directUpdateDLMColumn(this, partitionDB.getDLM_Partition_ID(), IDLMAware.COLUMNNAME_DLM_Partition_ID, 0);
		partitionDB.setPartitionSize(0);
		InterfaceWrapperHelper.save(partitionDB);
		return MSG_OK;
	}

}
