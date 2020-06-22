package de.metas.printing.api.impl;

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


import java.util.Iterator;
import java.util.Properties;

import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.IQuery;
import org.compiere.model.Query;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.printing.api.PrintingQueueProcessingInfo;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Check;
import de.metas.util.Services;

public class DefaultPrintingQueueSource extends AbstractPrintingQueueSource
{
	private final Properties ctx;
	private final IPrintingQueueQuery printingQueueQuery;
	private final PrintingQueueProcessingInfo printingQueueProcessingInfo;

	public DefaultPrintingQueueSource(
			@NonNull final Properties ctx,
			@NonNull final IPrintingQueueQuery printingQueueQuery,
			final PrintingQueueProcessingInfo printingQueueProcessingInfo)
	{
		this.ctx = ctx;
		this.printingQueueQuery = printingQueueQuery.copy();
		this.printingQueueProcessingInfo = printingQueueProcessingInfo;
	}

	@Override
	public PrintingQueueProcessingInfo getProcessingInfo()
	{
		return printingQueueProcessingInfo;
	}

	/**
	 * Iterate {@link I_C_Printing_Queue}s which are not processed yet.
	 *
	 * IMPORTANT: items are returned in FIFO order (ordered by {@link I_C_Printing_Queue#COLUMNNAME_C_Printing_Queue_ID})
	 */
	@Override
	public Iterator<I_C_Printing_Queue> createItemsIterator()
	{
		return createPrintingQueueIterator(ctx, printingQueueQuery, ITrx.TRXNAME_None);
	}

	/**
	 * Similar to {@link #createItemsIterator()}, but retrieves an iterator about all items have the same
	 * <ul>
	 * <li><code>AD_Client_ID</code>
	 * <li><code>AD_Org_ID</code>
	 * <li><code>Copies</code>
	 * </ul>
	 * as the given item, but excluding the given item itself.
	 */
	@Override
	public Iterator<I_C_Printing_Queue> createRelatedItemsIterator(@NonNull final I_C_Printing_Queue item)
	{
		final IPrintingQueueQuery queryRelated = printingQueueQuery.copy();
		queryRelated.setAD_Client_ID(item.getAD_Client_ID());
		queryRelated.setAD_Org_ID(item.getAD_Org_ID());
		queryRelated.setIgnoreC_Printing_Queue_ID(item.getC_Printing_Queue_ID());
		queryRelated.setCopies(item.getCopies()); // 08958

		return createPrintingQueueIterator(ctx, queryRelated, ITrx.TRXNAME_None);
	}

	private Iterator<I_C_Printing_Queue> createPrintingQueueIterator(final Properties ctx,
			final IPrintingQueueQuery queueQuery,
			final String trxName)
	{
		final IQuery<I_C_Printing_Queue> query = Services.get(IPrintingDAO.class).createQuery(ctx, queueQuery, trxName);

		// IMPORTANT: we need to query only one item at time (BufferSize=1) because else
		// it could happen that we re-process again an item which was already processed but it was cached in the buffer.
		query.setOption(IQuery.OPTION_IteratorBufferSize, 1);
		final Iterator<I_C_Printing_Queue> it = query.iterate(I_C_Printing_Queue.class);
		return it;
	}

	@Override
	public String getTrxName()
	{
		// mass processing of not printed queue items shall be out of transaction
		return ITrx.TRXNAME_None;
	}

}
