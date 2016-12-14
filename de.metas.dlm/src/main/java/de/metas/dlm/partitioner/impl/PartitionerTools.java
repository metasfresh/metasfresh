package de.metas.dlm.partitioner.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.util.Env;

import de.metas.dlm.Partition.WorkQueue;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_DLM_Partition_Workqueue;

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

public class PartitionerTools
{
	// de.metas.dlm.partitioner.PartitionerTools.dumpHistogram(records)
	public static void dumpHistogram(final Map<String, Set<ITableRecordReference>> result)
	{
		final Set<ITableRecordReference> allRecords = result.values()
				.stream()
				.flatMap(records -> records.stream())
				.collect(Collectors.toSet());

		System.out.println("overall size=" + allRecords.size());

		final PlainContextAware ctxProvider = PlainContextAware.newOutOfTrx(Env.getCtx());

		allRecords.stream()
				.collect(Collectors.groupingBy(ITableRecordReference::getTableName))
				.forEach((t, r) -> {
					System.out.println(t + ":\tsize=" + r.size() + ";\trepresentant=" + r.get(0).getModel(ctxProvider, IDLMAware.class));
				});
	}

	public static Iterator<WorkQueue> loadQueue(final int dlm_Partition_ID, final IContextAware contextAware)
	{
		final Iterator<I_DLM_Partition_Workqueue> iterator = Services.get(IQueryBL.class).createQueryBuilder(I_DLM_Partition_Workqueue.class, contextAware)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DLM_Partition_Workqueue.COLUMN_DLM_Partition_ID, dlm_Partition_ID)
				.orderBy().addColumn(I_DLM_Partition_Workqueue.COLUMNNAME_DLM_Partition_Workqueue_ID).endOrderBy()
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 5000)
				.iterate(I_DLM_Partition_Workqueue.class);

		return new Iterator<WorkQueue>()
		{
			@Override
			public boolean hasNext()
			{
				return iterator.hasNext();
			}

			@Override
			public WorkQueue next()
			{
				return WorkQueue.of(iterator.next());
			}
		};

	}

}
