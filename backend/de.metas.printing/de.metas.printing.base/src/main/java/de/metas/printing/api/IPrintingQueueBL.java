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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.adempiere.service.PrinterRoutingsQuery;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.spi.IPrintingQueueHandler;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Archive;

import java.util.List;
import java.util.Properties;
import java.util.Set;

public interface IPrintingQueueBL extends ISingletonService
{
	/**
	 * Adds the given print-out to the queue
	 *
	 * @return the generated record or <code>null</code> if no printing queue item was generated (i.e. was not needed or an {@link IPrintingQueueHandler} prevented that)
	 */
	I_C_Printing_Queue enqueue(I_AD_Archive printOut);

	void registerHandler(IPrintingQueueHandler handler);

	/**
	 * Cancel given printing queue if is not already printed.
	 *
	 * If the item is already printed, this method does nothing
	 */
	void cancel(I_C_Printing_Queue item);

	/**
	 * Re-enqueue for printing underlying archive of given printing queue item.
	 *
	 * This method is expected to run asynchronously so it's possible to not have the result immediate.
	 *
	 * @param recreateArchive
	 *            <ul>
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
	 */
	PrintingQueueProcessingInfo createPrintingQueueProcessingInfo(Properties ctx, IPrintingQueueQuery printingQueueQuery);

	PrintingQueueProcessingInfo createPrintingQueueProcessingInfo(@NonNull I_C_Printing_Queue printingQueueRecord);

	/**
	 * Compute and set the aggregation key for the given item. Items with different aggregation keys can't be aggregated into the same print job.
	 * <p>
	 * <b>Important:</b> don't save the given item, because the method might be called from a model validator.
	 */
	void setItemAggregationKey(I_C_Printing_Queue item);

	/**
	 * Creates an initial (not configured) {@link IPrintingQueueQuery}.
	 */
	IPrintingQueueQuery createPrintingQueueQuery();

	/**
	 * Retrieve the distinct aggregation keys of the items matched by the given <code>printingQueueQuery</code>. Then create one source per aggregation key. Each source has a copy of the given query,
	 * with the query-copy's {@link IPrintingQueueQuery#getAggregationKey()} being set to the source's respective aggregation key.
	 *
	 * @return a list of queue sources; note that the size of this list is expected to be moderate, while the number of items in each source instance might be very large.
	 */
	List<IPrintingQueueSource> createPrintingQueueSources(Properties ctx, IPrintingQueueQuery printingQueueQuery);

	/**
	 * Note: even if there is only one single user, we still have set IsPrintoutForOtherUser <code>true</code> and therefore create a recipients list, because we will be creating a print job for
	 * another user, and that's a big difference to creating one for ourselves, as we do not know the other user's <code>HostKey</code>.
	 * <p>
	 * Also see
	 * {@link IPrintJobBL#createPrintJobInstructions(de.metas.user.UserId, boolean, I_C_Print_Job_Line, I_C_Print_Job_Line, int)}
	 * <p>
	 * Note 2: this method does <b>not</b> save the given {@code item}.
	 */
	void setPrintoutForOtherUsers(I_C_Printing_Queue item, Set<Integer> userToPrintIds);

	PrinterRoutingsQuery createPrinterRoutingsQueryForItem(I_C_Printing_Queue item);
}
