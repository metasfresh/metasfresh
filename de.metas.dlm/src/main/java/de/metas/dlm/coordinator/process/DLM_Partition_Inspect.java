package de.metas.dlm.coordinator.process;

import java.util.Iterator;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.api.LoggableTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.IQuery;
import org.compiere.process.SvrProcess;

import de.metas.dlm.Partition;
import de.metas.dlm.coordinator.ICoordinatorService;
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
/**
 * Iterates {@link I_DLM_Partition} records that have {@link I_DLM_Partition#COLUMNNAME_DateNextInspection} being <code>null</code> or in the past.
 * If the process is called via "gear", then only the selected partitions are iterated.
 * For each of those {@link I_DLM_Partition}s, the process loads its {@link Partition}, submits it to {@link ICoordinatorService#inspectPartition(Partition)}
 * and then stores the partition's new target DLM level in the DB.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DLM_Partition_Inspect extends SvrProcess
{

	@Override
	protected String doIt() throws Exception
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);

		final IPartitionerService partitionerService = Services.get(IPartitionerService.class);
		final ICoordinatorService coordinatorService = Services.get(ICoordinatorService.class);

		final ICompositeQueryFilter<I_DLM_Partition> dateNextInspectionFilter = queryBL.createCompositeQueryFilter(I_DLM_Partition.class)
				.setJoinOr()
				.addEqualsFilter(I_DLM_Partition.COLUMN_DateNextInspection, null)
				.addCompareFilter(I_DLM_Partition.COLUMN_DateNextInspection, Operator.LESS_OR_EQUAL, SystemTime.asTimestamp());

		final Iterator<I_DLM_Partition> partitionsToInspect = queryBL
				.createQueryBuilder(I_DLM_Partition.class, this)
				.addOnlyActiveRecordsFilter()
				.filter(dateNextInspectionFilter)
				.filter(getProcessInfo().getQueryFilter())

		.orderBy().addColumn(I_DLM_Partition.COLUMN_DLM_Partition_ID).endOrderBy()
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
						final Partition validatedPartition = coordinatorService.inspectPartition(partition);

						addLog("Inspected partition={} with result={}", partition, validatedPartition);
						partitionerService.storePartition(validatedPartition);
					}
				})
				.setExceptionHandler(LoggableTrxItemExceptionHandler.instance)
				.process(partitionsToInspect);

		return MSG_OK;
	}

}
