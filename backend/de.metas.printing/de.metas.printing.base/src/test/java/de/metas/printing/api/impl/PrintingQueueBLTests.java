package de.metas.printing.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import de.metas.user.UserId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.I_C_Printing_Queue_Recipient;
import de.metas.util.Services;

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

public class PrintingQueueBLTests
{
	private IPrintingDAO printingDAO;

	/**
	 * This item is reset between tests.
	 */
	private I_C_Printing_Queue item;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		printingDAO = Services.get(IPrintingDAO.class);

		final I_AD_User itemUser = newInstance(I_AD_User.class);
		save(itemUser);

		item = newInstance(I_C_Printing_Queue.class);
		item.setAD_User_ID(itemUser.getAD_User_ID());
		save(item);
		assertFalse(item.isPrintoutForOtherUser()); // guard

	}

	/**
	 * Verifies that {@link PrintingQueueBL#setPrintoutForOtherUsers(I_C_Printing_Queue, java.util.Set)} sets the given {@link #item}'s {@code PrintoutForOtherUser} value to true and and can attach a {@link I_C_Printing_Queue_Recipient} to the item.
	 */
	@Test
	public void testSetPrintoutForOtherUsers_one_recipient()
	{
		final I_AD_User printRecipient = newInstance(I_AD_User.class);
		save(printRecipient);

		assertFalse(item.isPrintoutForOtherUser()); // guard

		final PrintingQueueBL printingQueueBL = new PrintingQueueBL();
		printingQueueBL.setPrintoutForOtherUsers(item, ImmutableSet.of(printRecipient.getAD_User_ID()));

		final List<UserId> result = printingDAO.retrievePrintingQueueRecipientIDs(item);
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getRepoId(), printRecipient.getAD_User_ID());
		assertTrue(item.isPrintoutForOtherUser());
	}

	/**
	 * Verifies that that {@link PrintingQueueBL#setPrintoutForOtherUsers(I_C_Printing_Queue, java.util.Set)} removes and previously attached printing queue recipients.
	 */
	@Test
	public void testSetPrintoutForOtherUsers_change_recipient()
	{
		final PrintingQueueBL printingQueueBL = new PrintingQueueBL();

		// initial setup & guard
		{
			final I_AD_User printRecipient1 = newInstance(I_AD_User.class);
			save(printRecipient1);

			printingQueueBL.setPrintoutForOtherUsers(item, ImmutableSet.of(printRecipient1.getAD_User_ID()));

			final List<UserId> result = printingDAO.retrievePrintingQueueRecipientIDs(item);
			assertEquals(result.size(), 1);
			assertEquals(result.get(0).getRepoId(), printRecipient1.getAD_User_ID());
			assertTrue(item.isPrintoutForOtherUser());
		}

		final I_AD_User printRecipient2 = newInstance(I_AD_User.class);
		save(printRecipient2);

		printingQueueBL.setPrintoutForOtherUsers(item, ImmutableSet.of(printRecipient2.getAD_User_ID()));

		// still expecting one recipient, but printRecipient2
		final List<UserId> result = printingDAO.retrievePrintingQueueRecipientIDs(item);
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getRepoId(), printRecipient2.getAD_User_ID());
		assertTrue(item.isPrintoutForOtherUser());
	}
}
