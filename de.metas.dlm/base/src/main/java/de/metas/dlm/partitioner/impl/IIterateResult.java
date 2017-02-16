package de.metas.dlm.partitioner.impl;

import java.util.Iterator;

import org.adempiere.model.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;

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

public interface IIterateResult
{

	/**
	 * Add the given <code>referencedRecord</code> to the result, and also declare that
	 * <ul>
	 * <li>the <code>referencedRecord</code> belongs to the given <code>dlmPartitionId</code> and</li>
	 * <li>the given <code>referencedRecord</code> is referenced by the given <code>referencingRecord</code>. I.e. <code>referencingRecord</code> has some column that points to <code>referencedRecord</code>.</li>
	 * </ul>
	 * <p>
	 * Note that if <code>referencedRecord</code> was not yet added earlier <b>and</b> if <code>dlmPartitionId</code> is less or equal zero,<br>
	 * then this method also adds the given record to the in-memory-queues, so that it will eventually be returned by {@link #nextFromQueue()}.
	 *
	 * In case of <code>dlmPartitionId</code> being greater than zero, we don't need to add it to the queue, because it is an "edge" record that marks the "border" to an already existing partition.
	 *
	 * @param referencingRecord the record from which we found referencedRecord. Might be ignored by some implementations.
	 * @param referencedRecord the record to add.
	 * @param dlmPartitionId
	 *
	 * @return <code>true</code> if the given <code>referencedRecord</code> was added for the first time.
	 */
	boolean addReferencedRecord(ITableRecordReference referencingRecord, ITableRecordReference referencedRecord, int dlmPartitionId);

	/**
	 * Analog to {@link #addReferencedRecord(ITableRecordReference, ITableRecordReference, int)}.<br>
	 * Add the given <code>referencingRecord</code> to the result, and also declare that
	 * <ul>
	 * <li><code>referencingRecord</code> belongs to the given <code>dlmPartitionId</code> and</li>
	 * <li><code>referencingRecord</code> is references by the given <code>referencedRecord</code>. I.e. <code>referencingRecord</code> has some column that points to <code>referencedRecord</code>.</li>
	 * </ul>
	 *
	 * @param referencingRecord the record to add.
	 * @param referencedRecord the record from which we found referencedRecord. Might be ignored by some implementations.
	 * @param dlmPartitionId
	 *
	 * @return <code>true</code> if the given <code>referencingRecord</code> was added for the first time.
	 */
	boolean addReferencingRecord(ITableRecordReference referencingRecord, ITableRecordReference referencedRecord, int dlmPartitionId);

	boolean contains(ITableRecordReference forwardReference);

	int size();

	boolean isQueueEmpty();

	/**
	 * Returns the next record from out work queue.
	 * If the record entered the queue as "initital result" via {@link #IterateResult(Iterator, IContextAware)}, then the record is now also loaded and added to this instance properly
	 * together with its <code>DLM_Partition_ID</code> that is known only after loading. So, after this method was called, {@link #size()} might have increated by one.
	 *
	 * @return
	 * @see #add(ITableRecordReference, int)
	 */
	ITableRecordReference nextFromQueue();
}
