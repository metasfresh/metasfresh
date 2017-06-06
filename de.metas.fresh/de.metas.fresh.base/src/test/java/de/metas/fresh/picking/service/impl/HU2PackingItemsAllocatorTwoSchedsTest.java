package de.metas.fresh.picking.service.impl;

import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.commonCreateHUTestHelper;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.createHU2PackingItemsAllocator;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.createHuDefIFCO;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.createHuDefPalet;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.createLUs;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.createTUs;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.wrapper.POJOLookupMap;
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
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
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
 * Tests the behavior of {@link HU2PackingItemsAllocator} with two {@link IPackingItem}s that contain at least two {@link I_M_ShipmentSchedule}.
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

	private I_M_HU_PI_Item huDefPalet;
	private I_M_HU_PI_Item_Product huDefIFCO;

	private IFreshPackingItem itemToPack;
	private IPackingContext packingContext;

	@Override
	protected void initialize()
	{
		// Services & Helpers
		shipmentScheduleHelper = new ShipmentScheduleHelper(helper);
		packingService = Services.get(IPackingService.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

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

	@Test
	public void testTwoHUsTwoShipmentSchedules_TopLevelTUs()
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		setupContext(10, 10);

		// get a reference to the two scheds now; the allocation() method might remove them from the packing item later on.
		final Set<I_M_ShipmentSchedule> shipmentSchedules = itemToPack.getShipmentSchedules();

		// packing item guards
		final Map<I_M_ShipmentSchedule, BigDecimal> qtys = itemToPack.getQtys();
		assertThat("Unexpected qtys.size(); qtys=" + qtys, qtys.size(), is(2));
		assertThat(shipmentSchedules.size(), is(2));
		itemToPack.getShipmentSchedules();

		final List<I_M_HU> tuHUs = createTUs(helper, huDefIFCO, 20);
		assertThat(tuHUs.size(), is(2));
		tuHUs.forEach(tu -> assertThat(handlingUnitsBL.isTopLevel(tu), is(true)));

		final HU2PackingItemsAllocator hu2PackingItemsAllocator = createHU2PackingItemsAllocator(packingContext, itemToPack);
		hu2PackingItemsAllocator.setFromHUs(tuHUs);
		hu2PackingItemsAllocator.allocate();

		assertThat(POJOLookupMap.get().getRecords(I_M_ShipmentSchedule.class).size(), is(2));

		assertThat("We shall have packed items", packingContext.getPackingItemsMap().hasPackedItems(), is(true));
		assertThat("We shall not have unpacked items", packingContext.getPackingItemsMap().hasUnpackedItems(), is(false));

		final Iterator<I_M_ShipmentSchedule> iterator = shipmentSchedules.iterator();
		final I_M_ShipmentSchedule shipmentSchedule1 = iterator.next();
		final I_M_ShipmentSchedule shipmentSchedule2 = iterator.next();
		assertThat(iterator.hasNext(), is(false));

		new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(shipmentSchedule1)
				.qtyPicked("10")
				.newShipmentScheduleQtyPickedExpectation()
				.noLU()
				.tu(tuHUs.get(1)) // i found that it's the 1st TU by trying out; what matters is that one TU is assigned to this shipment schedule and the other one to
				.qtyPicked("10")
				.endExpectation()
				.assertExpected("");

		new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(shipmentSchedule2)
				.qtyPicked("10")
				.newShipmentScheduleQtyPickedExpectation()
				.noLU()
				.tu(tuHUs.get(0))
				.qtyPicked("10")
				.endExpectation()
				.assertExpected("");
	}

	/**
	 * build one packing item that represents two shipment scheds.
	 * allocate an aggregated HU that represents two TUs with exactly matching quantities.
	 */
	@Test
	public void testTwoHUsTwoShipmentSchedules_AggregateTU()
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		setupContext(10, 10);

		// get a reference to the two scheds now; the allocation() method might remove them from the packing item later on.
		final Set<I_M_ShipmentSchedule> shipmentSchedules = itemToPack.getShipmentSchedules();

		// packing item guards
		final Map<I_M_ShipmentSchedule, BigDecimal> qtys = itemToPack.getQtys();
		assertThat("Unexpected qtys.size(); qtys=" + qtys, qtys.size(), is(2));
		assertThat(shipmentSchedules.size(), is(2));
		itemToPack.getShipmentSchedules();

		final List<I_M_HU> luHUs = createLUs(helper, huDefPalet, huDefIFCO, 20);
		// HU guards
		// there shall be one LU with one aggregate TU that represents 2 IFCOs with 10 each
		assertThat(luHUs.size(), is(1));
		final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(luHUs.get(0));
		assertThat(includedHUs.size(), is(1));
		assertThat(handlingUnitsBL.isVirtual(includedHUs.get(0)), is(true));
		assertThat(includedHUs.get(0).getM_HU_Item_Parent().getQty(), is(new BigDecimal("2")));

		final HU2PackingItemsAllocator hu2PackingItemsAllocator = createHU2PackingItemsAllocator(packingContext, itemToPack);
		hu2PackingItemsAllocator.setFromHUs(luHUs);
		hu2PackingItemsAllocator.allocate();

		assertThat("We shall have packed items", packingContext.getPackingItemsMap().hasPackedItems(), is(true));
		assertThat("We shall not have unpacked items", packingContext.getPackingItemsMap().hasUnpackedItems(), is(false));

		for (final I_M_ShipmentSchedule shipmentSchedule : shipmentSchedules)
		{
			new ShipmentScheduleQtyPickedExpectations()
					.shipmentSchedule(shipmentSchedule)
					.qtyPicked("10")
					.newShipmentScheduleQtyPickedExpectation()
					.lu(luHUs.get(0))
					.tu(includedHUs.get(0))
					.vhu(includedHUs.get(0))
					.qtyPicked("10")
					.endExpectation()
					.assertExpected("shipment schedule");
		}
	}
}
