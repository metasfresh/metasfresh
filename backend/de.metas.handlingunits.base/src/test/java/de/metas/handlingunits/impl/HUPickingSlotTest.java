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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_PickingSlot_HU;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingSlotConnectedComponent;
import de.metas.handlingunits.picking.impl.HUPickingSlotBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HUPickingSlotTest
{
	/** Service under test */
	private HUPickingSlotBL huPickingSlotBL;

	@BeforeEach
	public final void init()
	{
		AdempiereTestHelper.get().init();
		
		SpringContextHolder.registerJUnitBean(new PickingCandidateRepository());
		SpringContextHolder.registerJUnitBean((PickingSlotConnectedComponent)slotId -> false);

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
		pickingSlot.setC_BPartner_ID(bpartner.getC_BPartner_ID());
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
		assertThat("Queue is not yet empty, so partner shall not yet be released", pickingSlot.getC_BPartner_ID(), is(bpartner.getC_BPartner_ID()));

		//
		// Remove the second HU hu2 (queue shall now be empty)
		huPickingSlotBL.removeFromPickingSlotQueue(pickingSlot, hu2);
		assertThat("Queue is empty, so partner shall be released", pickingSlot.getC_BPartner_ID(), is(-1));
	}
}
