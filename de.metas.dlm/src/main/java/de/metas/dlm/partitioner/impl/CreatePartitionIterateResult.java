package de.metas.dlm.partitioner.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.model.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;

import com.google.common.collect.ImmutableMap;

import de.metas.dlm.Partition;
import de.metas.dlm.Partition.WorkQueue;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.partitioner.IIterateResultHandler;
import de.metas.dlm.partitioner.IIterateResultHandler.AddResult;
import de.metas.dlm.partitioner.IterateResultHandlerSupport;

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
 * This class is heavily used by {@link PartitionerService} when it looks for records to be assigned to a partition.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CreatePartitionIterateResult implements IStorableIterateResult
{
	private final Iterator<WorkQueue> iterator;

	private final LinkedList<WorkQueue> queueItemsToProcess;

	private final ArrayList<WorkQueue> queueItemsToDelete;

	/**
	 * Using a map because when querying "backward" references from a partitular table, we want to exclude IDs we already have.
	 */
	private final Map<String, Set<ITableRecordReference>> tableName2Record = new HashMap<>();

	/**
	 * Using this map, because after the iterate method, we need to know which different DLM_Partition_IDs we have.
	 * With 100.000 ITableRecordReferences, we then don't want to load all their IDLMAwares once again, just to get the DLM_Partition_ID.
	 */
	private final Map<Integer, Set<ITableRecordReference>> dlmPartitionId2Record = new HashMap<>();

	/**
	 * The number of records we currently have in memory.
	 */
	private int size = 0;

	private final IContextAware ctxAware;

	/**
	 * Emtpy by default
	 */
	private Partition partition = new Partition();

	private IterateResultHandlerSupport handlerSupport = new IterateResultHandlerSupport();

	public CreatePartitionIterateResult(
			final Iterator<WorkQueue> initialQueue,
			final IContextAware ctxAware)
	{
		this.ctxAware = ctxAware;
		iterator = initialQueue;
		queueItemsToProcess = new LinkedList<>();
		queueItemsToDelete = new ArrayList<>();
	}

	@Override
	public AddResult addReferencedRecord(final ITableRecordReference IGNORED, final ITableRecordReference referencedRecord, final int dlmPartitionId)
	{
		final boolean neverAddToqueue = false;
		return add0(referencedRecord, dlmPartitionId, neverAddToqueue);
	}

	@Override
	public AddResult addReferencingRecord(final ITableRecordReference referencingRecord, final ITableRecordReference IGNORED, final int dlmPartitionId)
	{
		final boolean neverAddToqueue = false;
		return add0(referencingRecord, dlmPartitionId, neverAddToqueue);
	}

	private AddResult add0(
			final ITableRecordReference tableRecordReference,
			final int dlmPartitionId,
			final boolean neverAddToqueue)
	{
		dlmPartitionId2Record
				.computeIfAbsent(dlmPartitionId, k -> new HashSet<>())
				.add(tableRecordReference);

		final String tableName = mkKey(tableRecordReference);
		final boolean added = tableName2Record
				.computeIfAbsent(tableName, k -> new HashSet<>())
				.add(tableRecordReference);

		final AddResult preliminaryResult;
		if (added)
		{
			preliminaryResult = AddResult.ADDED_CONTINUE;
			size++;
			if (!neverAddToqueue && dlmPartitionId <= 0)
			{
				queueItemsToProcess.addLast(WorkQueue.of(tableRecordReference));
			}

		}
		else
		{
			preliminaryResult = AddResult.NOT_ADDED_CONTINUE;
		}

		return handlerSupport.onRecordAdded(tableRecordReference, preliminaryResult);
	}

	@Override
	public void clearAfterPartitionStored(final Partition partition)
	{
		dlmPartitionId2Record.clear();
		tableName2Record.clear();
		queueItemsToDelete.clear();

		size = 0;
		this.partition = partition;
	}

	@Override
	public boolean contains(final ITableRecordReference tableRecordReference)
	{
		final Set<ITableRecordReference> records = tableName2Record.get(mkKey(tableRecordReference));
		if (records == null)
		{
			return false;
		}
		return records.contains(tableRecordReference);
	}

	private String mkKey(final ITableRecordReference tableRecordReference)
	{
		return tableRecordReference.getTableName();
	}

	@Override
	public Map<Integer, Set<ITableRecordReference>> getDlmPartitionId2Record()
	{
		return ImmutableMap.copyOf(dlmPartitionId2Record);
	}

	@Override
	public Map<String, Collection<ITableRecordReference>> getTableName2Record()
	{
		final Map<String, Collection<ITableRecordReference>> result = new HashMap<>();
		tableName2Record.entrySet().forEach(e -> {
			result.put(e.getKey(), e.getValue());

		});
		return result;
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public boolean isQueueEmpty()
	{
		final boolean iteratorEmpty = !iterator.hasNext();
		return iteratorEmpty && queueItemsToProcess.isEmpty();
	}

	@Override
	public ITableRecordReference nextFromQueue()
	{
		final WorkQueue result = nextFromQueue0();
		if (result.getDLM_Partition_Workqueue_ID() > 0)
		{
			queueItemsToDelete.add(result);
		}

		return result.getTableRecordReference();
	}

	private WorkQueue nextFromQueue0()
	{
		if (iterator.hasNext())
		{
			// once we get the record from the queue, we also add it to our result
			final WorkQueue next = iterator.next();
			final ITableRecordReference tableRecordReference = next.getTableRecordReference();
			final IDLMAware model = tableRecordReference.getModel(ctxAware, IDLMAware.class);
			add0(tableRecordReference, model.getDLM_Partition_ID(), true);

			return next;
		}

		return queueItemsToProcess.removeFirst();
	}

	@Override
	public List<WorkQueue> getQueueRecordsToStore()
	{
		return queueItemsToProcess;
	}

	@Override
	public List<WorkQueue> getQueueRecordsToDelete()
	{
		return queueItemsToDelete;
	}

	/**
	 * @return the {@link Partition} from the last invokation of {@link #clearAfterPartitionStored(Partition)}, or an empty partition.
	 */
	@Override
	public Partition getPartition()
	{
		return partition;
	}

	@Override
	public void registerHandler(IIterateResultHandler handler)
	{
		handlerSupport.registerListener(handler);
	}

	@Override
	public List<IIterateResultHandler> getRegisteredHandlers()
	{
		return handlerSupport.getRegisteredHandlers();
	}

	@Override
	public String toString()
	{
		return "IterateResult [queueItemsToProcess.size()=" + queueItemsToProcess.size()
				+ ", queueItemsToDelete.size()=" + queueItemsToDelete.size()
				+ ", size=" + size
				+ ", tableName2Record.size()=" + tableName2Record.size()
				+ ", dlmPartitionId2Record.size()=" + dlmPartitionId2Record.size()
				+ ", iterator=" + iterator
				+ ", ctxAware=" + ctxAware
				+ ", partition=" + partition + "]";
	}
}
