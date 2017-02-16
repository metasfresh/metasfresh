package de.metas.dlm.partitioner;

import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;

import de.metas.dlm.model.IDLMAware;
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
	 * The method loads both the records which are referenced by the given <code>firstRecord</code> ("forward") and the records which do reference the given <code>firstRecord</code> ("backward") and adds them result.
	 * For those forward and backward references that are not yet part of any partion (i.e. {@link IDLMAware#COLUMNNAME_DLM_Partition_ID} <code>== 0</code>), it repeats the procedure.
	 * The method finishes and returns the result when there are no further records to repeat that procedure with.
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
