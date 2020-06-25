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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.metas.user.UserId;
import org.adempiere.model.InterfaceWrapperHelper;

import com.google.common.collect.ImmutableList;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.PrintingQueueProcessingInfo;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Services;
import de.metas.util.collections.SingletonIterator;
import lombok.NonNull;

/**
 * Used for creating a print job for a single printing queue item.
 *
 * @author ad
 */
public class SingletonPrintingQueueSource extends AbstractPrintingQueueSource
{
	private final I_C_Printing_Queue item;
	private final UserId adUserPrintJobId;
	private final String trxName;

	/**
	 * Decides if marking the item as printed shall be persisted in database or just in memory
	 */
	private boolean persistPrintedFlag = true;

	/**
	 * When {@link #persistPrintedFlag} is set, indicates if current item is printed or not.
	 */
	private boolean temporaryPrinted = false;

	private final ImmutableList<UserId> AD_User_ToPrint_IDs;

	/**
	 * Create a new instance
	 *
	 * @param item             the item to create the printjob for
	 * @param adUserPrintJobId the <b>printjob</b>-user. If the queue has {@link I_C_Printing_Queue#isPrintoutForOtherUser()} <code>= true</code>, then the print job's print-job-instruction(s) will
	 *                         not have this user-ID, but the items' reciepents' user-IDs.
	 */
	public SingletonPrintingQueueSource(
			@NonNull final I_C_Printing_Queue item,
			final UserId adUserPrintJobId)
	{
		this.item = item;
		this.adUserPrintJobId = adUserPrintJobId;
		this.trxName = InterfaceWrapperHelper.getTrxName(item);

		if (item.isPrintoutForOtherUser())
		{
			final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
			AD_User_ToPrint_IDs = ImmutableList.copyOf(printingDAO.retrievePrintingQueueRecipientIDs(item));
		}
		else
		{
			AD_User_ToPrint_IDs = ImmutableList.of(adUserPrintJobId);
		}
	}

	/**
	 * If the item is for ourselves, then also our hostkey shall be added to the printing instructions.
	 * Otherwise, the job might be printed by someone else who is logged in with our user-id (e.g SuperUser).
	 */
	@Override
	public PrintingQueueProcessingInfo getProcessingInfo()
	{
		final boolean createWithSpecificHostKey = !item.isPrintoutForOtherUser();
		return new PrintingQueueProcessingInfo(adUserPrintJobId, AD_User_ToPrint_IDs, createWithSpecificHostKey);
	}

	@Override
	public Iterator<I_C_Printing_Queue> createItemsIterator()
	{
		return new SingletonIterator<I_C_Printing_Queue>(item);
	}

	@Override
	public Iterator<I_C_Printing_Queue> createRelatedItemsIterator(final I_C_Printing_Queue item)
	{
		final List<I_C_Printing_Queue> list = Collections.emptyList();
		return list.iterator();
	}

	/**
	 * Specifies if marking the items as printed shall be persisted to database (usually) or just in memory for this run (temporary).
	 *
	 * @param persistPrintedFlag true if item's printed status shall be persisted in database
	 */
	public void setPersistPrintedFlag(final boolean persistPrintedFlag)
	{
		this.persistPrintedFlag = persistPrintedFlag;
	}

	@Override
	public String getTrxName()
	{
		return trxName;
	}

	@Override
	public boolean isPrinted(@NonNull final I_C_Printing_Queue item)
	{
		if (persistPrintedFlag)
		{
			return super.isPrinted(item);
		}
		else
		{
			return temporaryPrinted;
		}
	}

	@Override
	public void markPrinted(@NonNull final I_C_Printing_Queue item)
	{
		if (persistPrintedFlag)
		{
			super.markPrinted(item);
		}
		else
		{
			temporaryPrinted = true;
		}
	}
}
