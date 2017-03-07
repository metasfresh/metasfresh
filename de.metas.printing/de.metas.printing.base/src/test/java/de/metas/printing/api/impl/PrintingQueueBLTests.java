package de.metas.printing.api.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.I_C_Printing_Queue_Recipient;

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

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		printingDAO = Services.get(IPrintingDAO.class);

		final I_AD_User itemUser = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		InterfaceWrapperHelper.save(itemUser);

		item = InterfaceWrapperHelper.newInstance(I_C_Printing_Queue.class);
		item.setAD_User(itemUser);
		InterfaceWrapperHelper.save(item);
		assertThat(item.isPrintoutForOtherUser(), is(false)); // guard
	}

	/**
	 * Verifies that {@link PrintingQueueBL#setPrintoutForOtherUsers(I_C_Printing_Queue, java.util.Set)} sets the given {@link #item}'s {@code PrintoutForOtherUser} value to true and and can attach a {@link I_C_Printing_Queue_Recipient} to the item.
	 */
	@Test
	public void testSetPrintoutForOtherUsers_one_recipient()
	{
		final I_AD_User printRecipient = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		InterfaceWrapperHelper.save(printRecipient);

		assertThat(item.isPrintoutForOtherUser(), is(false)); // guard

		final PrintingQueueBL printingQueueBL = new PrintingQueueBL();
		printingQueueBL.setPrintoutForOtherUsers(item, ImmutableSet.of(printRecipient.getAD_User_ID()));

		final List<Integer> result = printingDAO.retrievePrintingQueueRecipientIDs(item);
		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(printRecipient.getAD_User_ID()));
		assertThat(item.isPrintoutForOtherUser(), is(true));
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
			final I_AD_User printRecipient1 = InterfaceWrapperHelper.newInstance(I_AD_User.class);
			InterfaceWrapperHelper.save(printRecipient1);

			printingQueueBL.setPrintoutForOtherUsers(item, ImmutableSet.of(printRecipient1.getAD_User_ID()));

			final List<Integer> result = printingDAO.retrievePrintingQueueRecipientIDs(item);
			assertThat(result.size(), is(1));
			assertThat(result.get(0), is(printRecipient1.getAD_User_ID()));
			assertThat(item.isPrintoutForOtherUser(), is(true));
		}

		final I_AD_User printRecipient2 = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		InterfaceWrapperHelper.save(printRecipient2);

		printingQueueBL.setPrintoutForOtherUsers(item, ImmutableSet.of(printRecipient2.getAD_User_ID()));

		// still expecting one recipient, but printRecipient2
		final List<Integer> result = printingDAO.retrievePrintingQueueRecipientIDs(item);
		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(printRecipient2.getAD_User_ID()));
		assertThat(item.isPrintoutForOtherUser(), is(true));
	}
}
