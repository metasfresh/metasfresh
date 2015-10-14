package de.metas.printing.api;

/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Archive;

import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.spi.IPrintingQueueHandler;

public interface IPrintingQueueBL extends ISingletonService
{
	/**
	 * Adds the given print-out to the queue
	 * 
	 * @param printOut
	 * @return the generated record or <code>null</code> if no printing queue item was generated (i.e. was not needed or an {@link IPrintingQueueHandler} prevented that)
	 */
	I_C_Printing_Queue enqueue(I_AD_Archive printOut);

	void registerHandler(IPrintingQueueHandler handler);

	/**
	 * Cancel given printing queue if is not already printed.
	 * 
	 * If the item is already printed, this method does nothing
	 * 
	 * @param item
	 */
	void cancel(I_C_Printing_Queue item);

	/**
	 * Re-enqueue for printing underlying archive of given printing queue item.
	 * 
	 * This method is expected to run asynchronously so it's possible to not have the result immediate.
	 * 
	 * @param item
	 * @param recreateArchive <ul>
	 *            <li>if true then the printout archive of underlying model will be generated (asynchronously) and then enqueued again;</li>
	 *            <li>if false then current underlying archive is just re-enqueued
	 *            </ul>
	 * @throws AdempiereException if <code>recreateArchive</code> is true and the underlying model could not be found
	 */
	void renqueue(I_C_Printing_Queue item, boolean recreateArchive);

	/**
	 * Create a printing queue processing info by loading the first item what matches the given <code>printingQueueQuery</code> and using its data.
	 * <p>
	 * <b>Important:</b> assume that the given query has {@link IPrintingQueueQuery#getAggregationKey()} set.
	 * 
	 * @param ctx
	 * @param printingQueueQuery
	 * @return
	 */
	PrintingQueueProcessingInfo createPrintingQueueProcessingInfo(Properties ctx, IPrintingQueueQuery printingQueueQuery);

	/**
	 * Compute and set the aggregation key for the given item. Items with different aggregation keys can't be aggregated into the same print job.
	 * <p>
	 * <b>Important:</b> don't save the given item, because the method might be called from a model validator.
	 * 
	 * @param item
	 */
	void setItemAggregationKey(I_C_Printing_Queue item);

	/**
	 * Creates an initial (not configured) {@link IPrintingQueueQuery}.
	 * 
	 * @return
	 */
	IPrintingQueueQuery createPrintingQueueQuery();

	/**
	 * Retrieve the distinct aggregation keys of the items matched by the given <code>printingQueueQuery</code>. Then create one source per aggregation key. Each source has a copy of the given query,
	 * with the query-copy's {@link IPrintingQueueQuery#getAggregationKey()} being set to the source's respective aggregation key.
	 * 
	 * @param ctx
	 * @param printingQueueQuery
	 * @return a list of queue sources; note that the size of this list is expected to be moderate, while the number of items in each source instance might be very large.
	 */
	List<IPrintingQueueSource> createPrintingQueueSources(Properties ctx, IPrintingQueueQuery printingQueueQuery);
	
	void setUsersToPrint(final I_C_Printing_Queue item, final Set<Integer> userToPrintIds);
}
