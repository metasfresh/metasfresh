package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_PickingSlot_HU;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.impl.HUPickingSlotBL;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, PickingCandidateRepository.class, ShutdownListener.class })
public class HUPickingSlotTest
{
	@BeforeClass
	public final static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
		POJOWrapper.setDefaultStrictValues(false);
	}

	/** Service under test */
	private HUPickingSlotBL huPickingSlotBL;

	@Before
	public final void init()
	{
		huPickingSlotBL = new HUPickingSlotBL();
	}

	/**
	 * {@link HUPickingSlotBL#removeFromPickingSlotQueue(de.metas.picking.model.I_M_PickingSlot, de.metas.handlingunits.model.I_M_HU)}:<br>
	 * &bull; shall *not* release the BPartner from a picking slot if that slot's queue is not yet empty.
	 */
	@Test
	public void testOnlyReleaseIfQueueEmpty()
	{
		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_NoneNotNull;

		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(ctx, I_C_BPartner.class, trxName);
		InterfaceWrapperHelper.save(bpartner);

		//
		// Create a dynamic slot and assign it to partner
		final I_M_PickingSlot pickingSlot = InterfaceWrapperHelper.create(ctx, I_M_PickingSlot.class, trxName);
		pickingSlot.setIsDynamic(true);
		pickingSlot.setC_BPartner(bpartner);
		InterfaceWrapperHelper.save(pickingSlot);

		//
		// Add one HU to the queue
		final I_M_HU hu1 = InterfaceWrapperHelper.create(ctx, I_M_HU.class, trxName);
		InterfaceWrapperHelper.save(hu1);

		//
		// Setup a PickingSlot-HU assignment
		final I_M_PickingSlot_HU pickingSlotHu1 = InterfaceWrapperHelper.create(ctx, I_M_PickingSlot_HU.class, trxName);
		pickingSlotHu1.setM_HU(hu1);
		pickingSlotHu1.setM_PickingSlot_ID(pickingSlot.getM_PickingSlot_ID());
		InterfaceWrapperHelper.save(pickingSlotHu1);

		//
		// Add a second HU to the queue!
		final I_M_HU hu2 = InterfaceWrapperHelper.create(ctx, I_M_HU.class, trxName);
		InterfaceWrapperHelper.save(hu2);

		final I_M_PickingSlot_HU pickingSlotHu2 = InterfaceWrapperHelper.create(ctx, I_M_PickingSlot_HU.class, trxName);
		pickingSlotHu2.setM_HU(hu2);
		pickingSlotHu2.setM_PickingSlot_ID(pickingSlot.getM_PickingSlot_ID());
		InterfaceWrapperHelper.save(pickingSlotHu2);

		//
		// Remove just the first HU hu1 (queue is not yet empty)
		huPickingSlotBL.removeFromPickingSlotQueue(pickingSlot, hu1);
		assertThat("Queue is not yet empty, so partner shall not yet be released", pickingSlot.getC_BPartner(), is(bpartner));

		//
		// Remove the second HU hu2 (queue shall now be empty)
		huPickingSlotBL.removeFromPickingSlotQueue(pickingSlot, hu2);
		assertThat("Queue is empty, so partner shall be released", pickingSlot.getC_BPartner(), nullValue());
	}
}
