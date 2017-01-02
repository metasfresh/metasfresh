package de.metas.dlm.partitioner;

import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;

import de.metas.dlm.partitioner.config.PartitionConfig;
import de.metas.dlm.partitioner.impl.IIterateResult;

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

public interface IRecordCrawlerService extends ISingletonService
{
	/**
	 * For the records from the given {@code result}'s {@link IIterateResult#nextFromQueue()} load both the records which are referenced by those records ("forward")
	 * and the records which do themselves reference those records ("backward") and add them all to the {@code result} using
	 * {@link IIterateResult#addReferencedRecord(org.adempiere.util.lang.ITableRecordReference, org.adempiere.util.lang.ITableRecordReference, int)} resp.
	 * {@link IIterateResult#addReferencingRecord(org.adempiere.util.lang.ITableRecordReference, org.adempiere.util.lang.ITableRecordReference, int)}.
	 * <p>
	 * The records that are added in this manner will also be returned by {@link IIterateResult#nextFromQueue()} later on, so this method shall actually implement a <a href="https://en.wikipedia.org/wiki/Breadth-first_search">breath-first-search</a>.
	 *
	 * @param config
	 * @param ctxAware
	 * @param firstRecord the record to start with. It is assumed that this does not belong to any partition.
	 *
	 * @return the set of all records that referece or are referenced by the given <code>firstRecord</code> (directly or indirectly!) and
	 *         <li>either have <code>DLM_Partition_ID==0</code>
	 *         <li>or belong to the "outer border" of an existing partition. That means that they have DLM_Partition_ID>0, but are directly or indirectly referenced from the given <code>firstRecord</code>.
	 */
	IIterateResult crawl(
			PartitionConfig config,
			IContextAware ctxAware,
			IIterateResult result);
}
