/**
 *
 */
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.service.IADPInstanceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_PInstance;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.document.archive.api.IDocOutboundProducerService;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.logging.LogManager;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.api.PrintingQueueProcessingInfo;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.I_C_Printing_Queue_Recipient;
import de.metas.printing.spi.IPrintingQueueHandler;
import de.metas.printing.spi.impl.CompositePrintingQueueHandler;

/**
 * @author cg
 *
 */
public class PrintingQueueBL implements IPrintingQueueBL
{
	private final static transient Logger logger = LogManager.getLogger(PrintingQueueBL.class);
	// private final IPrintingDAO dao = Services.get(IPrintingDAO.class);

	private final CompositePrintingQueueHandler printingQueueHandler = new CompositePrintingQueueHandler();

	@Override
	public I_C_Printing_Queue enqueue(final org.compiere.model.I_AD_Archive printOut)
	{
		final Properties localCtx = Env.deriveCtx(InterfaceWrapperHelper.getCtx(printOut));
		Env.setContext(localCtx, Env.CTXNAME_AD_Client_ID, printOut.getAD_Client_ID());
		Env.setContext(localCtx, Env.CTXNAME_AD_Org_ID, printOut.getAD_Org_ID());

		final String trxName = InterfaceWrapperHelper.getTrxName(printOut);

		final I_C_Printing_Queue item = InterfaceWrapperHelper.create(localCtx, I_C_Printing_Queue.class, trxName);
		item.setAD_Archive(printOut);
		item.setIsActive(true);
		item.setProcessed(false);

		// 03870
		item.setAD_Org_ID(printOut.getAD_Org_ID());

		// 03829: set the values for new columns
		item.setAD_User_ID(Env.getAD_User_ID(localCtx));
		item.setAD_Role_ID(Env.getAD_Role_ID(localCtx));
		item.setAD_Process_ID(printOut.getAD_Process_ID());

		item.setIsPrintoutForOtherUser(false); // task 09028: this the default, but the printingQueueHandler can override it.

		printingQueueHandler.afterEnqueueBeforeSave(item, InterfaceWrapperHelper.create(printOut, I_AD_Archive.class));
		// If queue item was deactivated by listeners, there is no point to save it or go forward
		if (!item.isActive())
		{
			return null;
		}

		InterfaceWrapperHelper.save(item);

		printingQueueHandler.afterEnqueueAfterSave(item, InterfaceWrapperHelper.create(printOut, I_AD_Archive.class));
		InterfaceWrapperHelper.save(item); // make sure the changes made in after enqueue, are also saved

		return item;
	}

	@Override
	public void registerHandler(final IPrintingQueueHandler handler)
	{
		printingQueueHandler.addHandler(handler);
	}

	@Override
	public void cancel(final I_C_Printing_Queue item)
	{
		if (item == null)
		{
			return;
		}

		if (item.isProcessed())
		{
			// Item was already printed, nothing do to
			return;
		}

		item.setIsActive(false);
		item.setProcessed(true);
		InterfaceWrapperHelper.save(item);
	}

	@Override
	public void renqueue(final I_C_Printing_Queue item, final boolean recreateArchive)
	{
		cancel(item);

		final org.compiere.model.I_AD_Archive archive = item.getAD_Archive();

		if (recreateArchive)
		{
			final Object model = Services.get(IArchiveDAO.class).retrieveReferencedModel(archive, Object.class);
			if (model == null)
			{
				throw new AdempiereException("Cannot regenerate archive for " + item + " because there is no model for underlying archive " + archive);
			}

			//
			// We are just asking the document outbound producer to recreate the archive for the model.
			// As a consequence, following events are expected to happen:
			// * document archive is regenerated (async or sync)
			// * archive is automatically enqueued again to printing queue
			Services.get(IDocOutboundProducerService.class).createDocOutbound(model);
		}
		else
		{
			// Just renqueue underlying archive again
			enqueue(archive);
		}
	}

	@Override
	public IPrintingQueueQuery createPrintingQueueQuery()
	{
		return new PrintingQueueQuery();
	}

	@Override
	public PrintingQueueProcessingInfo createPrintingQueueProcessingInfo(final Properties ctx, final IPrintingQueueQuery query)
	{
		Check.assumeNotNull(query, "Param query shall not be null");
		Check.assumeNotNull(query.getAggregationKey(), "IPrintingQueueQuery {} shall have an aggregation key", query);

		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
		final I_C_Printing_Queue firstItem = printingDAO
				.createQuery(ctx, query, ITrx.TRXNAME_ThreadInherited)
				.first();

		final int printJobAD_User_ID = getPrintJobAD_User_ID(ctx, query);

		final List<Integer> printToUserIDs;
		final boolean createWithSpecificHostKey;
		if (firstItem.isPrintoutForOtherUser())
		{
			printToUserIDs = ImmutableList.<Integer> copyOf(printingDAO.retrievePrintingQueueRecipientIDs(firstItem));

			// we don't know which hostKeys the users will be using next time they poll for print jobs
			createWithSpecificHostKey = false;
		}
		else
		{
			printToUserIDs = ImmutableList.<Integer> of(printJobAD_User_ID);

			// If the item is for ourselves, then also our hostKey shall be added to the printing instructions.
			// Otherwise, the job might be printed by someone else who is logged in with our user-id (e.g SuperUser).
			createWithSpecificHostKey = true;
		}
		return new PrintingQueueProcessingInfo(printJobAD_User_ID, printToUserIDs, createWithSpecificHostKey);
	}

	private int getPrintJobAD_User_ID(final Properties ctx, final IPrintingQueueQuery printingQueueQuery)
	{

		if (printingQueueQuery.getAD_User_ID() >= 0)
		{
			logger.debug("Using AD_User_ToPrint from from query");
			return printingQueueQuery.getAD_User_ID();
		}

		// 03870 R2
		// if possible, use the AD_User_ID of the user that started the process which is calling this method
		if (printingQueueQuery.getOnlyAD_PInstance_ID() > 0)
		{
			final I_AD_PInstance pinstance = Services.get(IADPInstanceDAO.class).retrieveAD_PInstance(ctx, printingQueueQuery.getOnlyAD_PInstance_ID());
			final int printJobUserId = pinstance.getAD_User_ID();
			if (printJobUserId > 0)
			{
				logger.debug("Using AD_User_ToPrint from AD_PInstance");
				return printJobUserId;
			}
		}

		if (Env.getAD_User_ID(ctx) > 0)
		{
			final int printJobUserId = Env.getAD_User_ID(ctx);
			logger.debug("Using AD_User_ToPrint from context (logged user)");
			return printJobUserId;
		}

		logger.debug("Will use the AD_User_ID of first item to be added to the job");
		return -1;
	}

	@Override
	public List<IPrintingQueueSource> createPrintingQueueSources(final Properties ctx, final IPrintingQueueQuery printingQueueQuery)
	{
		final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

		// get the distinct aggregation keys
		final IQuery<I_C_Printing_Queue> query = printingDAO.createQuery(ctx, printingQueueQuery, ITrx.TRXNAME_None);
		final List<Map<String, Object>> aggregationKeys = query.listDistinct(I_C_Printing_Queue.COLUMNNAME_PrintingQueueAggregationKey);

		final List<IPrintingQueueSource> sources = new ArrayList<IPrintingQueueSource>(aggregationKeys.size());

		// create one source per aggregation key
		for (final Map<String, Object> value2key : aggregationKeys)
		{
			final String aggregationKey = (String)value2key.get(I_C_Printing_Queue.COLUMNNAME_PrintingQueueAggregationKey);

			final IPrintingQueueQuery copy = printingQueueQuery.copy();
			copy.setAggregationKey(aggregationKey == null ? "" : aggregationKey); // if there is no key, whe assume ""..this might be the case for old items or for unit tests.

			final PrintingQueueProcessingInfo printingQueueProcessingInfo = printingQueueBL.createPrintingQueueProcessingInfo(ctx, copy);
			sources.add(new DefaultPrintingQueueSource(ctx, copy, printingQueueProcessingInfo));
		}
		return sources;
	}

	@Override
	public void setItemAggregationKey(final I_C_Printing_Queue item)
	{
		final List<Object> keyItems = new ArrayList<Object>();
		keyItems.add(item.getCopies());
		keyItems.add(item.isPrintoutForOtherUser());
		if (item.isPrintoutForOtherUser())
		{
			final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
			keyItems.addAll(printingDAO.retrievePrintingQueueRecipientIDs(item));
		}

		final String key = Util.mkKey(keyItems.toArray()).toString();
		item.setPrintingQueueAggregationKey(key);
	}

	@Override
	public void setPrintoutForOtherUsers(final I_C_Printing_Queue item, 
			final Set<Integer> userToPrintIds)
	{
		//
		// Make sure the item is not null and it was not already printed (i.e. processed)
		Check.assumeNotNull(item, "item not null");
		Check.assume(!item.isProcessed(), "item not already printed: {}", item);

		//
		// Build a list of valid user to print IDs
		Check.assumeNotEmpty(userToPrintIds, "userToPrintIds not empty");
		final Set<Integer> userToPrintIdsActual = new HashSet<>();
		for (final Integer userToPrintId : userToPrintIds)
		{
			// skip not valid user IDs
			if (userToPrintId == null || userToPrintId <= 0)
			{
				continue;
			}
			userToPrintIdsActual.add(userToPrintId);
		}
		Check.assumeNotEmpty(userToPrintIdsActual, "userToPrintIdsActual not empty");

		//
		// Delete existing queue item recipients (if any),
		// but don't update the aggregation key. We will update it later in this method.
		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
		printingDAO.deletePrintingQueueRecipients(item);

		//
		// Create new recipients
		// Make sure the item was saved.
		// We are not saving it here, because that shall be the responsibility of the caller.
		Check.errorIf(item.getC_Printing_Queue_ID() < 0, "Item shall be saved: {}", item);

		item.setIsPrintoutForOtherUser(true);
		for (final int userToPrintId : userToPrintIdsActual)
		{
			final I_C_Printing_Queue_Recipient recipient = InterfaceWrapperHelper.newInstance(I_C_Printing_Queue_Recipient.class, item);
			recipient.setC_Printing_Queue(item);
			recipient.setAD_User_ToPrint_ID(userToPrintId);
			printingDAO.setDisableAggregationKeyUpdate(recipient); // don't update the aggregation key
			InterfaceWrapperHelper.save(recipient);
		}

		// Make sure the aggregation key is up2date.
		setItemAggregationKey(item);
	}
}
