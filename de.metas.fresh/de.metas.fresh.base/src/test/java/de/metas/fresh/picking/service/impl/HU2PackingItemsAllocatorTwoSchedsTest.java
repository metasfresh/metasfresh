package de.metas.fresh.picking.service.impl;

import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.*;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.junit.Test;

import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.form.PackingItemsMap;
import de.metas.fresh.picking.form.FreshPackingItemHelper;
import de.metas.fresh.picking.form.IFreshPackingItem;
import de.metas.fresh.picking.service.IPackingContext;
import de.metas.fresh.picking.service.IPackingService;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.shipmentschedule.util.ShipmentScheduleHelper;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/*
 * #%L
 * de.metas.fresh.base
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
 * Tests the befavior of {@link HU2PackingItemsAllocator} with two {@link IPackingItem}s that contain at least two {@link I_M_ShipmentSchedule}.
 * Note: if these tests fail, it makes sense to first verify that all tests in {@link HU2PackingItemsAllocatorTest} works.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HU2PackingItemsAllocatorTwoSchedsTest extends AbstractHUTest
{

	private ShipmentScheduleHelper shipmentScheduleHelper;
	private IPackingService packingService;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHUTrxBL huTrxBL;
	private IQueryBL queryBL;
	private I_M_HU_PI huDefIFCO;
	private I_M_HU_PI huDefPalet;
	private IFreshPackingItem itemToPack;
	private IPackingContext packingContext;

	@Override
	protected void initialize()
	{
		// Services & Helpers
		shipmentScheduleHelper = new ShipmentScheduleHelper(helper);
		packingService = Services.get(IPackingService.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		huTrxBL = Services.get(IHUTrxBL.class);
		queryBL = Services.get(IQueryBL.class);

		//
		// Handling Units Definition
		huDefIFCO = createHuDefIFCO(helper);
		huDefPalet = createHuDefPalet(helper, huDefIFCO);
	}

	@Override
	protected HUTestHelper createHUTestHelper()
	{
		return commonCreateHUTestHelper();
	}

	private void setupContext(final int... qtysToDeliver)
	{
		final Map<I_M_ShipmentSchedule, BigDecimal> scheds2Qtys = new HashMap<I_M_ShipmentSchedule, BigDecimal>();
		//
		// Create Items to Pack
		int qtyToDeliverSum = 0;
		for (final int qtyToDeliver : qtysToDeliver)
		{
			BigDecimal qtyToDeliverBD = new BigDecimal(qtyToDeliver);
			final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, qtyToDeliverBD, BigDecimal.ZERO);
			scheds2Qtys.put(schedule, qtyToDeliverBD);
			qtyToDeliverSum += qtyToDeliver;
		}
		this.itemToPack = FreshPackingItemHelper.create(scheds2Qtys);
		// Validate

		assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum(), comparesEqualTo(BigDecimal.valueOf(qtyToDeliverSum)));

		//
		// Create Packing Items
		final PackingItemsMap packingItems = new PackingItemsMap();
		packingItems.addUnpackedItem(itemToPack);

		//
		// Create Packing Context
		this.packingContext = packingService.createPackingContext(helper.ctx);
		packingContext.setPackingItemsMap(packingItems);
		final int packingItemsMapKey = 123; // just a dummy value for now
		packingContext.setPackingItemsMapKey(packingItemsMapKey);

		//
		// Validate initial context state
		assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum(), comparesEqualTo(BigDecimal.valueOf(qtyToDeliverSum)));
		assertTrue("We shall have unpacked items", packingItems.hasUnpackedItems());
		assertFalse("We shall NOT have packed items", packingItems.hasPackedItems());

		for (final I_M_ShipmentSchedule shipmentSchedule : itemToPack.getShipmentSchedules())
		{
			new ShipmentScheduleQtyPickedExpectations()
					.shipmentSchedule(shipmentSchedule)
					.qtyPicked("0")
					.assertExpected_ShipmentSchedule("shipment schedule");
		}
	}

	private List<I_M_HU> createLUs(final int qtyToLoad)
	{
		if (qtyToLoad % COUNT_Tomatoes_Per_IFCO != 0)
		{
			throw new AdempiereException("QtyToLoad shall be multiple of " + COUNT_Tomatoes_Per_IFCO + " else method assertValidShipmentScheduleLUTUAssignments will fail");
		}

		final IHUContext huContext = helper.createMutableHUContextForProcessing(ITrx.TRXNAME_None);
		final BigDecimal qtyToLoadBD = BigDecimal.valueOf(qtyToLoad);
		final List<I_M_HU> hus = helper.createHUs(huContext, huDefPalet, pTomato, qtyToLoadBD, uomEach);

		return hus;
	}
	
	/**
	 * build one packing item that represents two shipment scheds.
	 * allocate two hus with exactly matching quantities.
	 */
	@Test
	public void testTwoHUsTwoShipmentSchedules()
	{
		setupContext(10, 10);
		final List<I_M_HU> luHUs = createLUs(20);
		
		// guards
		assertThat(luHUs.size(), is(1));
		assertThat(handlingUnitsDAO.retrieveIncludedHUs(luHUs.get(1)), is(2));
	}
}
