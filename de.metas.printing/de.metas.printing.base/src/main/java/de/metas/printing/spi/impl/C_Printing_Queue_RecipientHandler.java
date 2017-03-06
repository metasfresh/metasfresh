package de.metas.printing.spi.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import com.google.common.collect.ImmutableSet;

import de.metas.document.archive.model.I_AD_Archive;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.model.I_AD_User;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.I_C_Printing_Queue_Recipient;
import de.metas.printing.spi.PrintingQueueHandlerAdapter;

/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * This handler evaluates {@link I_AD_User#COLUMNNAME_C_Printing_Queue_Recipient_ID} for a {@link I_C_Printing_Queue} item's {@code AD_User_ID} and also for it's possibly already existing {@link I_C_Printing_Queue_Recipient} records.
 * It creates or updates {@link I_C_Printing_Queue_Recipient#COLUMN_AD_User_ToPrint_ID} according to the respective user's {@code COLUMNNAME_C_Printing_Queue_Recipient_ID} value.<br>
 * If an item does not yet have {@code C_Printing_Queue_Recipient}s, it creates one. If the item already has {@code C_Printing_Queue_Recipient}, it does not reset them, but updates their respective {@code AD_User_ToPrint_ID}s.
 * <p>
 * If can also handle indirection, i.e. for chains of {@code AD_User.C_Printing_Queue_Recipient_ID}, it will traverse to the last {@code AD_User}.
 * It will also deal with loops gracefully, i.e. stop traversing when it already saw a certain {@code AD_User_ID}.
 * <p>
 * Note: we assume that this handler will always be called last, when other handlers already did their stuff with {@link I_C_Printing_Queue_Recipient}s.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/1081
 */
public class C_Printing_Queue_RecipientHandler extends PrintingQueueHandlerAdapter
{
	public static C_Printing_Queue_RecipientHandler INSTANCE = new C_Printing_Queue_RecipientHandler();

	private C_Printing_Queue_RecipientHandler()
	{
	}

	/**
	 * Returns {@code true} if the given {@code item} has an {@code AD_User_ID > 0} and if that user also has {@link I_AD_User#COLUMNNAME_C_Printing_Queue_Recipient_ID} {@code > 0}.
	 */
	@Override
	public boolean isApplyHandler(final I_C_Printing_Queue queueItem, final I_AD_Archive IGNORED)
	{
		if (queueItem.getAD_User_ID() <= 0)
		{
			return false;
		}
		
		// return true if the item's user has a C_Printing_Queue_Recipient
		final I_AD_User queueUser = InterfaceWrapperHelper.create(queueItem.getAD_User(), I_AD_User.class);
		return queueUser.getC_Printing_Queue_Recipient_ID() > 0;
	}

	/**
	 * Updates the given {@code item}, see the javadoc of this class for further details.
	 */
	@Override
	public void afterEnqueueAfterSave(final I_C_Printing_Queue queueItem, final I_AD_Archive IGNORED)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final List<I_C_Printing_Queue_Recipient> recipients = queryBL.createQueryBuilder(I_C_Printing_Queue_Recipient.class, queueItem)
				.addOnlyActiveRecordsFilter() // we must retrieve ALL; the caller shall decide
				.addEqualsFilter(I_C_Printing_Queue_Recipient.COLUMN_C_Printing_Queue_ID, queueItem.getC_Printing_Queue_ID())
				.create()
				.list();

		if (recipients.isEmpty())
		{
			resetToUserPrintingQueueRecipient(queueItem);
		}
		else
		{
			for (final I_C_Printing_Queue_Recipient queueRecipient : recipients)
			{
				final I_AD_User userToPrint = InterfaceWrapperHelper.create(queueRecipient.getAD_User_ToPrint(), I_AD_User.class);
				final int newUserToPrintId = getEffectiveUserToPrint(userToPrint, new HashSet<>()).getAD_User_ID();

				queueRecipient.setAD_User_ToPrint_ID(newUserToPrintId);
				InterfaceWrapperHelper.save(queueRecipient);
			}
		}
	}

	private void resetToUserPrintingQueueRecipient(final I_C_Printing_Queue queueItem)
	{
		final I_AD_User itemUser = InterfaceWrapperHelper.create(queueItem.getAD_User(), I_AD_User.class);

		final int userToPrintId = getEffectiveUserToPrint(itemUser, new HashSet<>()).getAD_User_ID();

		final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
		printingQueueBL.setPrintoutForOtherUsers(queueItem, ImmutableSet.of(userToPrintId));
	}

	private I_AD_User getEffectiveUserToPrint(final I_AD_User user, final Set<Integer> alreadSeenUserIDs)
	{
		if (user.getC_Printing_Queue_Recipient_ID() <= 0)
		{
			return user;
		}
		if (!alreadSeenUserIDs.add(user.getC_Printing_Queue_Recipient_ID()))
		{
			return user;
		}

		return getEffectiveUserToPrint(user.getC_Printing_Queue_Recipient(), alreadSeenUserIDs);
	}

}
