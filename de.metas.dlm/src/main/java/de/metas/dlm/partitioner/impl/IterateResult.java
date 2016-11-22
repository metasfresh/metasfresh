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
public class IterateResult
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

	public IterateResult(
			final Iterator<WorkQueue> initialQueue,
			final IContextAware ctxAware)
	{
		this.ctxAware = ctxAware;
		iterator = initialQueue;
		queueItemsToProcess = new LinkedList<>();
		queueItemsToDelete = new ArrayList<>();
	}

	/**
	 * Adds the given <code>tableRecordReference</code> to the result, and also declares that the table reference belongs to the given <code>dlmPartitionId</code>.
	 * <p>
	 * Note that <code>tableRecordReference</code> was not yet added earlier <b>and</b> if <code>dlmPartitionId</code> is less or equal zero,
	 * then this method also adds the given record to the in-memory-queues so that it will eventually be returned by {@link #nextFromQueue()}.
	 *
	 * In case of <code>dlmPartitionId</code> being greater than zero, we don't need to add it to the queue, because it si an "edge" record that marks the "border" to an already existing partition.
	 *
	 * @param tableRecordReference
	 * @param dlmPartitionId
	 * @return
	 */
	public boolean add(final ITableRecordReference tableRecordReference, final int dlmPartitionId)
	{
		final boolean neverAddToqueue = false;
		return add0(tableRecordReference, dlmPartitionId, neverAddToqueue);
	}

	private boolean add0(
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

		if (added)
		{
			size++;
			if (!neverAddToqueue && dlmPartitionId <= 0)
			{
				queueItemsToProcess.addLast(WorkQueue.of(tableRecordReference));
			}
		}
		return added;
	}

	public void clearAfterPartitionStored(final Partition partition)
	{
		this.dlmPartitionId2Record.clear();
		this.tableName2Record.clear();
		this.queueItemsToDelete.clear();

		this.size = 0;
		this.partition = partition;
	}

	boolean contains(final ITableRecordReference tableRecordReference)
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

	Map<Integer, Set<ITableRecordReference>> getDlmPartitionId2Record()
	{
		return ImmutableMap.copyOf(dlmPartitionId2Record);
	}

	Map<String, Collection<ITableRecordReference>> getTableName2Record()
	{
		final Map<String, Collection<ITableRecordReference>> result = new HashMap<>();
		tableName2Record.entrySet().forEach(e -> {
			result.put(e.getKey(), e.getValue());

		});
		return result;
	}

	public int size()
	{
		return size;
	}

	public boolean isQueueEmpty()
	{
		final boolean iteratorEmpty = !iterator.hasNext();
		return iteratorEmpty && queueItemsToProcess.isEmpty();
	}

	/**
	 * Returns the next record from out work queue.
	 * If the record entered the queue as "initital result" via {@link #IterateResult(Iterator, IContextAware)}, then the record is now also loaded and added to this instance properly
	 * together with its <code>DLM_Partition_ID</code> that is known only after loading. So, after this method was called, {@link #size()} might have increated by one.
	 *
	 * @return
	 * @see #add(ITableRecordReference, int)
	 */
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

	public List<WorkQueue> getQueueRecordsToStore()
	{
		return queueItemsToProcess;
	}

	public List<WorkQueue> getQueueRecordsToDelete()
	{
		return queueItemsToDelete;
	}

	/**
	 * @return the {@link Partition} from the last invokation of {@link #clearAfterPartitionStored(Partition)}, or an empty partition.
	 */
	public Partition getPartition()
	{
		return partition;
	}

	@Override
	public String toString()
	{
		return "IterateResult [iterator=" + iterator + ", queueItemsToProcess.size()=" + queueItemsToProcess.size() + ", queueItemsToDelete.size()=" + queueItemsToDelete.size()
				+ ", size=" + size + ", tableName2Record.size()=" + tableName2Record.size() + ", dlmPartitionId2Record.size()=" + dlmPartitionId2Record.size()
				+ ", ctxAware=" + ctxAware + ", partition=" + partition + "]";
	}

}
