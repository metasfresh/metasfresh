package de.metas.dlm.migrator.process;

import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.ModelColumnNameValue;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.api.LoggableTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.process.SvrProcess;

import de.metas.dlm.Partition;
import de.metas.dlm.migrator.IMigratorService;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.partitioner.IPartitionerService;

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

public class DLM_Partition_Migrate extends SvrProcess
{

	@Override
	protected String doIt() throws Exception
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);

		final IPartitionerService partitionerService = Services.get(IPartitionerService.class);
		final IMigratorService migratorService = Services.get(IMigratorService.class);

		final Iterator<I_DLM_Partition> partitionsToMigrate = queryBL.createQueryBuilder(I_DLM_Partition.class, this)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_DLM_Partition.COLUMN_Target_DLM_Level, null)
				.addNotEqualsFilter(I_DLM_Partition.COLUMN_Target_DLM_Level, IMigratorService.DLM_Level_NOT_SET)
				.addNotEqualsFilter(I_DLM_Partition.COLUMN_Target_DLM_Level, ModelColumnNameValue.forColumn(I_DLM_Partition.COLUMN_Current_DLM_Level))
				.orderBy().addColumn(I_DLM_Partition.COLUMNNAME_DLM_Partition_ID).endOrderBy()
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(I_DLM_Partition.class);

		trxItemProcessorExecutorService.<I_DLM_Partition, Void> createExecutor()
				.setContext(getCtx(), getTrxName())
				.setProcessor(new TrxItemProcessorAdapter<I_DLM_Partition, Void>()
				{
					@Override
					public void process(final I_DLM_Partition item) throws Exception
					{
						final Partition partition = partitionerService.loadPartition(item);
						final Partition migratedPartition = migratorService.migratePartition(partition);

						addLog("Migrated partition={} with result={}", partition, migratedPartition);
						partitionerService.storePartition(migratedPartition, false);
					}
				})
				.setExceptionHandler(LoggableTrxItemExceptionHandler.instance)
				.process(partitionsToMigrate);

		return MSG_OK;
	}

}
