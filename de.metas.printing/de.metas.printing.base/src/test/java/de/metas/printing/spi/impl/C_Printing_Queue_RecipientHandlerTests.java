package de.metas.printing.spi.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.impl.PrintingQueueBL;
import de.metas.printing.model.I_AD_User;
import de.metas.printing.model.I_C_Printing_Queue;

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

	@Test
	public void testNotIsApplyHandler()
	{
		assertThat(C_Printing_Queue_RecipientHandler.INSTANCE.isApplyHandler(item, null), is(false));
	}

	@Test
	public void testIsApplyHandler()
	{
		final I_AD_User printRecipient = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		InterfaceWrapperHelper.save(printRecipient);

		final I_AD_User itemUser = InterfaceWrapperHelper.create(item.getAD_User(), I_AD_User.class);
		itemUser.setC_Printing_Queue_Recipient(printRecipient);
		InterfaceWrapperHelper.save(itemUser);

		assertThat(C_Printing_Queue_RecipientHandler.INSTANCE.isApplyHandler(item, null), is(true));
	}

	@Test
	public void testAfterEnqueueAfterSaveDirect()
	{
		final I_AD_User printRecipient = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		InterfaceWrapperHelper.save(printRecipient);

		final I_AD_User itemUser = InterfaceWrapperHelper.create(item.getAD_User(), I_AD_User.class);
		itemUser.setC_Printing_Queue_Recipient(printRecipient);
		InterfaceWrapperHelper.save(itemUser);

		C_Printing_Queue_RecipientHandler.INSTANCE.afterEnqueueAfterSave(item, null);

		final List<Integer> result = printingDAO.retrievePrintingQueueRecipientIDs(item);
		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(printRecipient.getAD_User_ID()));
		assertThat(item.isPrintoutForOtherUser(), is(true));
	}

	@Test
	public void testAfterEnqueueAfterSaveIndirect()
	{
		final I_AD_User printRecipientEffective = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		InterfaceWrapperHelper.save(printRecipientEffective);

		final I_AD_User printRecipientMiddle = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		printRecipientMiddle.setC_Printing_Queue_Recipient(printRecipientEffective);
		InterfaceWrapperHelper.save(printRecipientMiddle);

		final I_AD_User itemUser = InterfaceWrapperHelper.create(item.getAD_User(), I_AD_User.class);
		itemUser.setC_Printing_Queue_Recipient(printRecipientMiddle);
		InterfaceWrapperHelper.save(itemUser);

		C_Printing_Queue_RecipientHandler.INSTANCE.afterEnqueueAfterSave(item, null);

		final List<Integer> result = printingDAO.retrievePrintingQueueRecipientIDs(item);
		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(printRecipientEffective.getAD_User_ID()));
		assertThat(item.isPrintoutForOtherUser(), is(true));
	}

	@Test
	public void testAfterEnqueueAfterSaveIndirectWithLoop()
	{
		final I_AD_User printRecipientEffective = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		InterfaceWrapperHelper.save(printRecipientEffective);

		final I_AD_User printRecipientMiddle = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		printRecipientMiddle.setC_Printing_Queue_Recipient(printRecipientEffective);
		InterfaceWrapperHelper.save(printRecipientMiddle);

		printRecipientEffective.setC_Printing_Queue_Recipient(printRecipientMiddle);
		InterfaceWrapperHelper.save(printRecipientEffective);

		final I_AD_User itemUser = InterfaceWrapperHelper.create(item.getAD_User(), I_AD_User.class);
		itemUser.setC_Printing_Queue_Recipient(printRecipientMiddle);
		InterfaceWrapperHelper.save(itemUser);

		C_Printing_Queue_RecipientHandler.INSTANCE.afterEnqueueAfterSave(item, null);

		final List<Integer> result = printingDAO.retrievePrintingQueueRecipientIDs(item);
		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(printRecipientEffective.getAD_User_ID()));
		assertThat(item.isPrintoutForOtherUser(), is(true));
	}

	/**
	 * 
	 */
	@Test
	public void testAfterEnqueueAfterUpdateRecipients()
	{
		// this is the recipient we do *not* want to have in the end result
		final I_AD_User printRecipientWrong = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		InterfaceWrapperHelper.save(printRecipientWrong);
		// .. despite the fact that is it the item user's recipient
		final I_AD_User itemUser = InterfaceWrapperHelper.create(item.getAD_User(), I_AD_User.class);
		itemUser.setC_Printing_Queue_Recipient(printRecipientWrong);
		InterfaceWrapperHelper.save(itemUser);
		
		final I_AD_User printRecipientEffective = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		InterfaceWrapperHelper.save(printRecipientEffective);

		final I_AD_User printRecipientIntermediate = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		printRecipientIntermediate.setC_Printing_Queue_Recipient(printRecipientEffective);
		InterfaceWrapperHelper.save(printRecipientIntermediate);

		// set the item's recipient to be printRecipientIntermediate
		new PrintingQueueBL().setPrintoutForOtherUsers(item, ImmutableSet.of(printRecipientIntermediate.getAD_User_ID()));

		// call the testee
		C_Printing_Queue_RecipientHandler.INSTANCE.afterEnqueueAfterSave(item, null);
		
		// expect the result to be printRecipientEffective, because that's what printRecipientIntermediate links to
		// the result shall *not* be printRecipientWrong. We want the handler to update the existing record no to reset it
		final List<Integer> result = printingDAO.retrievePrintingQueueRecipientIDs(item);
		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(printRecipientEffective.getAD_User_ID()));
		assertThat(item.isPrintoutForOtherUser(), is(true));
	}
}
