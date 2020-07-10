package de.metas.printing.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import de.metas.user.UserId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.impl.PrintingQueueBL;
import de.metas.printing.model.I_AD_User;
import de.metas.printing.model.I_C_Printing_Queue;
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

public class C_Printing_Queue_RecipientHandlerTests
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

	@Test
	public void testNotIsApplyHandler()
	{
		assertFalse(C_Printing_Queue_RecipientHandler.INSTANCE.isApplyHandler(item, null));
	}

	@Test
	public void testIsApplyHandler()
	{
		final I_AD_User printRecipient = newInstance(I_AD_User.class);
		save(printRecipient);

		final I_AD_User itemUser = InterfaceWrapperHelper.loadOutOfTrx(item.getAD_User_ID(), I_AD_User.class);

		itemUser.setC_Printing_Queue_Recipient(printRecipient);
		save(itemUser);

		assertTrue(C_Printing_Queue_RecipientHandler.INSTANCE.isApplyHandler(item, null));
	}

	@Test
	public void testAfterEnqueueAfterSaveDirect()
	{
		final I_AD_User printRecipient = newInstance(I_AD_User.class);
		save(printRecipient);

		final I_AD_User itemUser = InterfaceWrapperHelper.loadOutOfTrx(item.getAD_User_ID(), I_AD_User.class);

		itemUser.setC_Printing_Queue_Recipient(printRecipient);
		save(itemUser);

		C_Printing_Queue_RecipientHandler.INSTANCE.afterEnqueueAfterSave(item, null);

		final List<UserId> result = printingDAO.retrievePrintingQueueRecipientIDs(item);
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getRepoId(), printRecipient.getAD_User_ID());
		assertTrue(item.isPrintoutForOtherUser());
	}

	@Test
	public void testAfterEnqueueAfterSaveIndirect()
	{
		final I_AD_User printRecipientEffective = newInstance(I_AD_User.class);
		save(printRecipientEffective);

		final I_AD_User printRecipientMiddle = newInstance(I_AD_User.class);
		printRecipientMiddle.setC_Printing_Queue_Recipient(printRecipientEffective);
		save(printRecipientMiddle);

		final I_AD_User itemUser = loadOutOfTrx(item.getAD_User_ID(), I_AD_User.class);

		itemUser.setC_Printing_Queue_Recipient(printRecipientMiddle);
		save(itemUser);

		C_Printing_Queue_RecipientHandler.INSTANCE.afterEnqueueAfterSave(item, null);

		final List<UserId> result = printingDAO.retrievePrintingQueueRecipientIDs(item);
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getRepoId(), printRecipientEffective.getAD_User_ID());
		assertTrue(item.isPrintoutForOtherUser());
	}

	@Test
	public void testAfterEnqueueAfterSaveIndirectWithLoop()
	{
		final I_AD_User printRecipientEffective = newInstance(I_AD_User.class);
		save(printRecipientEffective);

		final I_AD_User printRecipientMiddle = newInstance(I_AD_User.class);
		printRecipientMiddle.setC_Printing_Queue_Recipient(printRecipientEffective);
		save(printRecipientMiddle);

		printRecipientEffective.setC_Printing_Queue_Recipient(printRecipientMiddle);
		save(printRecipientEffective);

		final I_AD_User itemUser = InterfaceWrapperHelper.loadOutOfTrx(item.getAD_User_ID(), I_AD_User.class);

		itemUser.setC_Printing_Queue_Recipient(printRecipientMiddle);
		save(itemUser);

		C_Printing_Queue_RecipientHandler.INSTANCE.afterEnqueueAfterSave(item, null);

		final List<UserId> result = printingDAO.retrievePrintingQueueRecipientIDs(item);
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getRepoId(), printRecipientEffective.getAD_User_ID());
		assertTrue(item.isPrintoutForOtherUser());
	}

	@Test
	public void testAfterEnqueueAfterUpdateRecipients()
	{
		// this is the recipient we do *not* want to have in the end result
		final I_AD_User printRecipientWrong = newInstance(I_AD_User.class);
		save(printRecipientWrong);
		// .. despite the fact that is it the item user's recipient

		final I_AD_User itemUser = loadOutOfTrx(item.getAD_User_ID(), I_AD_User.class);
		itemUser.setC_Printing_Queue_Recipient(printRecipientWrong);
		save(itemUser);

		final I_AD_User printRecipientEffective = newInstance(I_AD_User.class);
		save(printRecipientEffective);

		final I_AD_User printRecipientIntermediate = newInstance(I_AD_User.class);
		printRecipientIntermediate.setC_Printing_Queue_Recipient(printRecipientEffective);
		save(printRecipientIntermediate);

		// set the item's recipient to be printRecipientIntermediate
		new PrintingQueueBL().setPrintoutForOtherUsers(item, ImmutableSet.of(printRecipientIntermediate.getAD_User_ID()));

		// call the testee
		C_Printing_Queue_RecipientHandler.INSTANCE.afterEnqueueAfterSave(item, null);

		// expect the result to be printRecipientEffective, because that's what printRecipientIntermediate links to
		// the result shall *not* be printRecipientWrong. We want the handler to update the existing record no to reset it
		final List<UserId> result = printingDAO.retrievePrintingQueueRecipientIDs(item);
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getRepoId(), printRecipientEffective.getAD_User_ID());
		assertTrue(item.isPrintoutForOtherUser());
	}
}
